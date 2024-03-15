package com.xenia.testvk.domain.usecases

import com.xenia.testvk.domain.usecases.enter_usecases.CheckFileMasterPasswordUseCase
import com.xenia.testvk.domain.usecases.enter_usecases.SetMasterPasswordUseCase
import com.xenia.testvk.domain.usecases.enter_usecases.ValidateMasterPasswordUseCase
import com.xenia.testvk.domain.usecases.passwords_usecases.AddNewPasswordUseCase
import com.xenia.testvk.domain.usecases.passwords_usecases.DeletePasswordUseCase
import com.xenia.testvk.domain.usecases.passwords_usecases.GetPasswordByIdUseCase
import com.xenia.testvk.domain.usecases.passwords_usecases.GetPasswordsUseCase

data class UseCases(
    val allPasswords: GetPasswordsUseCase,
    val getPasswordById: GetPasswordByIdUseCase,
    val addPassword: AddNewPasswordUseCase,
    val deletePassword: DeletePasswordUseCase,
    val checkFileMasterPassword: CheckFileMasterPasswordUseCase,
    val setMasterPassword: SetMasterPasswordUseCase,
    val validateMasterPassword: ValidateMasterPasswordUseCase
)