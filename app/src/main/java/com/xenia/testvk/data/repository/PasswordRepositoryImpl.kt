package com.xenia.testvk.data.repository

import com.xenia.testvk.data.data_source.CryptoClass
import com.xenia.testvk.data.data_source.PasswordDao
import com.xenia.testvk.data.mapper.PasswordMapper
import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File


class PasswordRepositoryImpl(
    private val passwordDao: PasswordDao,
    private val mapper: PasswordMapper,
    private val cryptoClass: CryptoClass
): PasswordRepository {

    override fun allPasswords(): Flow<List<ItemModel>> =
        passwordDao.allPasswords().map { item ->
            mapper.mapListEntityToListModel(item)
        }

    override fun getPasswordById(id: Int): ItemModel {
        return mapper.mapEntityToModel(passwordDao.getPasswordById(id))
    }

    override suspend fun addPassword(password: ItemModel) {
        passwordDao.addPassword(mapper.mapModelToEntity(password))
    }

    override suspend fun deletePassword(password: ItemModel) {
        passwordDao.deletePassword(mapper.mapModelToEntity(password))
    }

    override suspend fun setMasterPassword(filesDir: File, otpValue: String) {
        cryptoClass.setMasterPassword(filesDir, otpValue)
    }

    override fun checkFileIsExist(filesDir: File): Boolean? {
        return cryptoClass.checkFileIsExist(filesDir)
    }

    override fun validateMasterPassword(filesDir: File, originalPassword: String): Boolean {
        return cryptoClass.validateMasterPassword(filesDir, originalPassword)
    }
}