package com.xenia.testvk.presentation.main_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xenia.testvk.navigation.Screens
import com.xenia.testvk.ui.theme.ButtonColor

@Composable
fun MainScreen(
    navController: NavController
) {
    Button(
        modifier = Modifier.padding(top = 20.dp),
        onClick = {
            navController.navigate(Screens.EnterScreen.route) {
                popUpTo(Screens.EnterScreen.route) {
                    inclusive = true
                }
            }
        },
        shape = ButtonDefaults.outlinedShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor
        )
    )
    {
        Text(
            text = "Enter Screen",
            textAlign = TextAlign.Center
        )
    }
}