package com.xenia.testvk.domain.usecases.enter_usecases

import com.xenia.testvk.domain.repository.PasswordRepository
import java.io.File

class ValidateMasterPasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(filesDir: File, originalPassword: String): Boolean {
        return passwordRepository.validateMasterPassword(filesDir, originalPassword)
    }
}