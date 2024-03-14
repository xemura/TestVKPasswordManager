package com.xenia.testvk.domain.usecases

import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.repository.PasswordRepository


class GetPasswordByIdUseCase(
    private val passwordRepository: PasswordRepository
) {

    suspend operator fun invoke(id: Int): ItemModel {
        return passwordRepository.getPasswordById(id)
    }
}