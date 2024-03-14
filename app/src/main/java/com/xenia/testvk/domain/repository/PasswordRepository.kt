package com.xenia.testvk.domain.repository

import com.xenia.testvk.domain.ItemModel
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    fun allPasswords(): Flow<List<ItemModel>>?

    suspend fun getPasswordById(id: Int): ItemModel

    suspend fun addPassword(password: ItemModel)

    suspend fun deletePassword(password: ItemModel)
}