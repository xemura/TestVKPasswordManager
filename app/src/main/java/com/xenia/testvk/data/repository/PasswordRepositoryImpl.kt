package com.xenia.testvk.data.repository

import com.xenia.testvk.data.data_source.PasswordDao
import com.xenia.testvk.data.mapper.Mapper
import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PasswordRepositoryImpl(
    private val passwordDao: PasswordDao
): PasswordRepository {

    private val mapper = Mapper()

    override fun allPasswords(): Flow<List<ItemModel>> =
        passwordDao.allPasswords().map { item ->
            mapper.mapListEntityToListModel(item)
        }

    override suspend fun getPasswordById(id: Int): ItemModel {
        return mapper.mapEntityToModel(passwordDao.getPasswordById(id))
    }

    override suspend fun addPassword(password: ItemModel) {
        passwordDao.addPassword(mapper.mapModelToEntity(password))
    }

    override suspend fun deletePassword(password: ItemModel) {
        passwordDao.deletePassword(mapper.mapModelToEntity(password))
    }
}