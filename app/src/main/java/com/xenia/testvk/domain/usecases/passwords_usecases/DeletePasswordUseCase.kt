package com.xenia.testvk.domain.usecases.passwords_usecases

import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.repository.PasswordRepository

class DeletePasswordUseCase(
    private val passwordRepository: PasswordRepository
) {

    suspend operator fun invoke(password: ItemModel){
        passwordRepository.deletePassword(password)
    }
}