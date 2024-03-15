package com.xenia.testvk.data.data_source

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.xor


private const val ALGORITHM = "PBKDF2WithHmacSHA512"
private const val ITERATIONS = 1000
private const val KEY_LENGTH = 64 * 8

class CryptoClass {
    fun generateHash(password: String, salt: ByteArray): String {
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val key: SecretKey = factory.generateSecret(spec)
        val hash: ByteArray = key.encoded
        return "$ITERATIONS:${toHex(salt)}:${toHex(hash)}"
    }

    fun generateRandomSalt(): ByteArray {
        val random = SecureRandom.getInstance("SHA1PRNG")
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return salt
    }

    fun getHashFromFile(
        filesDir: File
    ): String {
        val file = File(filesDir, "master_password.txt")

        val fin = FileInputStream(file)
        val bytes = fin.readBytes()

        return bytes.toString(Charsets.UTF_8)
    }

    private fun fromHex(hex: String): ByteArray {
        val bytes = ByteArray(hex.length / 2)
        for (i in bytes.indices) {
            bytes[i] = hex.substring(2 * i, 2 * i + 2).toInt(16).toByte()
        }
        return bytes
    }

    private fun toHex(array: ByteArray): String {
        val bi = BigInteger(1, array)
        var hex = bi.toString(16)
        val paddingLength = array.size * 2 - hex.length
        if (paddingLength > 0) {
            hex = String.format("%0" + paddingLength + "d", 0) + hex
        }
        return hex
    }

    fun checkFileIsExist(
        filesDir: File
    ): Boolean? {
        val file = File(filesDir, "master_password.txt")

        if (!file.exists()) {
            return null
        }

        return true
    }

    suspend fun setMasterPassword(filesDir: File, otpValue: String) {
        val salt = generateRandomSalt()
        val newPassword = generateHash(otpValue, salt)

        val file = File(filesDir, "master_password.txt")

        if (!file.exists()) {
            withContext(Dispatchers.IO) {
                file.createNewFile()
            }
        }

        try {
            val fos = withContext(Dispatchers.IO) {
                FileOutputStream(file)
            }
            withContext(Dispatchers.IO) {
                fos.write(newPassword.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun validateMasterPassword(filesDir: File, originalPassword: String): Boolean {
        val currentPasswordHash = getHashFromFile(filesDir)
        val parts = currentPasswordHash.split(":").toTypedArray()
        val iterations = parts[0].toInt()
        val salt = fromHex(parts[1])
        val hash = fromHex(parts[2])
        val spec = PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.size * 8)
        val skf = SecretKeyFactory.getInstance(ALGORITHM)
        val testHash = skf.generateSecret(spec).encoded
        var diff = hash.size xor testHash.size

        for (i in hash.indices) {
            diff = diff or ((hash[i] xor testHash[i]).toInt()) // ???
        }

        return diff == 0
    }

//    fun generatePassword(value: String): String {
//        val salt = generateRandomSalt()
//        val password = generateHash(value, salt)
//
//        return password
//    }

    fun generatePassword(
        plainTextBytes: ByteArray,
        passwordString: String
    ): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()
        try {
            //Random salt for next step
            val random = SecureRandom()
            val salt = ByteArray(256)
            random.nextBytes(salt)
            //PBKDF2 - derive the key from the password, don't use passwords directly
            val passwordChar = passwordString.toCharArray() //Turn password into char[] array
            val pbKeySpec = PBEKeySpec(passwordChar, salt, 1324, 256) //1324 iterations
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")
            //Create initialization vector for AES
            val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
            val iv = ByteArray(16)
            ivRandom.nextBytes(iv)
            val ivSpec = IvParameterSpec(iv)
            //Encrypt
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted = cipher.doFinal(plainTextBytes)
            map["salt"] = salt
            map["iv"] = iv
            map["encrypted"] = encrypted
        } catch (e: java.lang.Exception) {
            Log.e("MYAPP", "encryption exception", e)
        }
        return map
    }

    fun decryptPassword(map: HashMap<String, ByteArray>, passwordString: String): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]
            //regenerate key from password
            val passwordChar = passwordString.toCharArray()
            val pbKeySpec = PBEKeySpec(passwordChar, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")
            //Decrypt
            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            decrypted = cipher.doFinal(encrypted)
        } catch (e: java.lang.Exception) {
            Log.e("MYAPP", "decryption exception", e)
        }
        return decrypted
    }
}