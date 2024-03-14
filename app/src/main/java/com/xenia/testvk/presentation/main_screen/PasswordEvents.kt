package com.xenia.testvk.presentation.main_screen

import com.xenia.testvk.domain.ItemModel

sealed class PasswordsEvents {
    data class DeletePassword(val password: ItemModel): PasswordsEvents()
    data object RestorePassword: PasswordsEvents()
}