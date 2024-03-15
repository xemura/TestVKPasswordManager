package com.xenia.testvk.domain.repository

import com.xenia.testvk.domain.ItemModel
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PasswordRepository {

    fun allPasswords(): Flow<List<ItemModel>>?
    fun getPasswordById(id: Int): ItemModel
    suspend fun addPassword(password: ItemModel)
    suspend fun deletePassword(password: ItemModel)

    suspend fun setMasterPassword(filesDir: File, otpValue: String)
    fun checkFileIsExist(filesDir: File): Boolean?
    fun validateMasterPassword(filesDir: File, originalPassword: String): Boolean
}