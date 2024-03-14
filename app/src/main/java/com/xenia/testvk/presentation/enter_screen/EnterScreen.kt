package com.xenia.testvk.presentation.enter_screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenia.testvk.navigation.Screens
import com.xenia.testvk.ui.theme.ButtonColor
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.experimental.xor


private const val ALGORITHM = "PBKDF2WithHmacSHA512"
private const val ITERATIONS = 1000
private const val KEY_LENGTH = 64 * 8

@Composable
fun EnterScreen(
    navController: NavController,
    filesDir: File
) {

    var otpValue by remember {
        mutableStateOf("")
    }

    val masterPassword = checkFileIsExist(filesDir)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (masterPassword == null) {
            Text(
                text = "Создайте пароль и нажмите кнопку",
                Modifier.padding(bottom = 20.dp)
            )
        }

        OtpTextField(
            otpText = otpValue,
            onOtpTextChange = { value, _ ->
                otpValue = value
            }
        )

        Button(
            modifier = Modifier.padding(top = 20.dp),
            onClick = {
                if (masterPassword != null) {
                    val currentPasswordHash = getHashFromFile(filesDir)
                    val validate = validatePassword(otpValue, currentPasswordHash)

                    if (validate) {
                        Log.d("TAG", "YES!")
                        navController.navigate(Screens.MainScreen.route)
                    }
                    else {
                        Log.d("TAG", "current $currentPasswordHash")
                    }

                } else {
                    val salt = generateRandomSalt()
                    val newPassword = generateHash(otpValue, salt)

                    setMasterPassword(filesDir, newPassword)

                    navController.navigate(Screens.MainScreen.route)
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            )
        ) {
            Text(
                text = "Войти",
                textAlign = TextAlign.Center
            )
        }
    }
}

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

fun getHashFromFile(
    filesDir: File
): String {
    val file = File(filesDir, "master_password.txt")

    val fin = FileInputStream(file)
    val bytes = fin.readBytes()

    return bytes.toString(Charsets.UTF_8)
}

private fun validatePassword(originalPassword: String, storedPassword: String): Boolean {
    val parts = storedPassword.split(":").toTypedArray()
    val iterations = parts[0].toInt()
    Log.d("TAG", "iterations $iterations")
    val salt = fromHex(parts[1])
    Log.d("TAG", "salt ${toHex(salt)}")
    val hash = fromHex(parts[2])
    Log.d("TAG", "hash.size = ${hash.size}")
    val spec = PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.size * 8)
    val skf = SecretKeyFactory.getInstance(ALGORITHM)
    val testHash = skf.generateSecret(spec).encoded
    Log.d("TAG", "$ITERATIONS:${toHex(salt)}:${toHex(testHash)}")
    var diff = hash.size xor testHash.size
    for (i in hash.indices) {
        diff = diff or ((hash[i] xor testHash[i]).toInt()) // ???
    }
    return diff == 0
}

private fun fromHex(hex: String): ByteArray {
    val bytes = ByteArray(hex.length / 2)
    for (i in bytes.indices) {
        bytes[i] = hex.substring(2 * i, 2 * i + 2).toInt(16).toByte()
    }
    return bytes
}

fun setMasterPassword(
    filesDir: File,
    password: String,
) {
    val file = File(filesDir, "master_password.txt")

    if (!file.exists()) {
        file.createNewFile()
        Log.d("TAG", "setMasterPassword createNewFile")
    }

    try {
        val fos = FileOutputStream(file)
        fos.write(password.toByteArray())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}