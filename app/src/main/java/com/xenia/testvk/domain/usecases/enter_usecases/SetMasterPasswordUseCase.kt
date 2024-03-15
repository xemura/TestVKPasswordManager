package com.xenia.testvk.domain.usecases.enter_usecases

import com.xenia.testvk.domain.repository.PasswordRepository
import java.io.File

class SetMasterPasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(filesDir: File, otpValue: String) {
        passwordRepository.setMasterPassword(filesDir, otpValue)
    }
}