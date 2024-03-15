package com.xenia.testvk.domain.usecases.enter_usecases

import com.xenia.testvk.domain.repository.PasswordRepository
import java.io.File

class CheckFileMasterPasswordUseCase(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(filesDir: File): Boolean? {
        return passwordRepository.checkFileIsExist(filesDir)
    }
}