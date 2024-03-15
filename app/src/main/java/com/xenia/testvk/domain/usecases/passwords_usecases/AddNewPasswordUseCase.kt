package com.xenia.testvk.domain.usecases.passwords_usecases

import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.repository.PasswordRepository


class AddNewPasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(password: ItemModel){
        if (password.password.isNotBlank() && password.login.isNotBlank())
            passwordRepository.addPassword(password)
    }
}