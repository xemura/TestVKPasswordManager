package com.xenia.testvk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.xenia.testvk.presentation.add_edit_password_screen.AddEditPasswordScreen
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

        composable(Screens.AddEditPasswordScreen.route + "?passwordId={passwordId}",
            arguments = listOf(
                navArgument(
                    name = "passwordId",
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditPasswordScreen(navController = navController)
        }
    }
}