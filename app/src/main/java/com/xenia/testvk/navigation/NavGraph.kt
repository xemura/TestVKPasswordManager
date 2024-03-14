package com.xenia.testvk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.xenia.testvk.presentation.change_screen.ChangeScreen
import com.xenia.testvk.presentation.enter_screen.EnterScreen
import com.xenia.testvk.presentation.main_screen.MainScreen
import java.io.File

@Composable
fun NavGraph(
    navController: NavHostController,
    filesDir: File
) {
    NavHost(
        navController = navController,
        startDestination = Screens.EnterScreen.route
    )
    {
        composable(route = Screens.EnterScreen.route) {
            EnterScreen(navController, filesDir)
        }
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController)
        }
        composable(route = Screens.ChangeScreen.route) {
            ChangeScreen(navController)
        }
    }
}