package com.xenia.testvk.domain.usecases.passwords_usecases

import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow


class GetPasswordsUseCase(
    private val passwordRepository: PasswordRepository
) {

    operator fun invoke(): Flow<List<ItemModel>>? {
        return passwordRepository.allPasswords()
    }
}