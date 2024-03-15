package com.xenia.testvk.presentation.enter_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xenia.testvk.navigation.Screens
import com.xenia.testvk.ui.theme.ButtonColor
import java.io.File


@Composable
fun EnterScreen(
    navController: NavController,
    filesDir: File,
    viewModel: EnterViewModel = hiltViewModel()
) {
    var otpValue by remember {
        mutableStateOf("")
    }
    val masterPassword = viewModel.checkFileIsExist(filesDir)
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (masterPassword == null) {
            Text(
                text = "Создайте пароль и нажмите кнопку",
                Modifier.padding(bottom = 20.dp)
            )
        }

        OtpTextField(
            otpText = otpValue,
            onOtpTextChange = { value, _ ->
                otpValue = value
            }
        )

        Button(
            modifier = Modifier.padding(top = 20.dp),
            onClick = {
                if (masterPassword != null) {
                    val validate = viewModel.validatePassword(filesDir, otpValue)

                    if (validate) {
                        navController.navigate(Screens.MainScreen.route)
                    }
                    else {
                        Toast.makeText(
                            context,
                            "Неверный пароль",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    viewModel.setPassword(filesDir, otpValue)
                    navController.navigate(Screens.MainScreen.route)
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            )
        ) {
            Text(
                text = "Войти",
                textAlign = TextAlign.Center
            )
        }
    }
}