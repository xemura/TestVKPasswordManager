package com.xenia.testvk.navigation

sealed class Screens(val route: String) {
    data object EnterScreen: Screens("enter_screen")
    data object MainScreen: Screens("main_screen")
    data object ChangeScreen: Screens("Change_screen")
}