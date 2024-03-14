package com.xenia.testvk.presentation.add_edit_password_screen


sealed class AddEditPasswordEvent{
    data class EnteringWebsite(val value: String): AddEditPasswordEvent()
    data class EnteringLogin(val value: String): AddEditPasswordEvent()
    data class EnteringPassword(val value: String): AddEditPasswordEvent()
    data object SavePassword: AddEditPasswordEvent()
}