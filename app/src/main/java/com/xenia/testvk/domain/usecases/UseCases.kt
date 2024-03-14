package com.xenia.testvk.domain.usecases

data class UseCases(
    val allPasswords: GetPasswordsUseCase,
    val getPasswordById: GetPasswordByIdUseCase,
    val addPassword: AddNewPasswordUseCase,
    val deletePassword: DeletePasswordUseCase
)