package com.xenia.testvk.presentation.main_screen

import com.xenia.testvk.domain.ItemModel

data class PasswordState(
    var passwords: List<ItemModel> = emptyList()
)