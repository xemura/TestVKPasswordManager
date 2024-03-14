package com.xenia.testvk.presentation.main_screen

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.squareup.picasso.Picasso
import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.navigation.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: PasswordsViewModel = hiltViewModel()
) {

//    val data = mutableListOf(
//        ItemModel(0, "https://www.google.com/s2/favicons?domain=https://google.com&sz=128", "google.com", "testlogin@mail.ru", "testpassword123"),
//        ItemModel(1, "https://www.google.com/s2/favicons?domain=https://yandex.com&sz=128", "yandex.com", "test2login@mail.ru", "test2password123"),
//        ItemModel(2, "https://www.google.com/s2/favicons?domain=https://vk.com&sz=128", "vk.com", "test3login@mail.ru", "test3password123")
//    )

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()
    val passwords = viewModel.passwordsState.value


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            ),
                title = {
                    Text(
                        text = "Менеджер паролей",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddEditPasswordScreen.route) {
                        popUpTo(Screens.AddEditPasswordScreen.route) {
                            inclusive = true
                        }
                    }
                },
            ) {
                Icon(Icons.Filled.Add, "Add data button.")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->

            LazyColumn(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
            ) {
                itemsIndexed(
                    passwords.passwords,
                    key = { _, password -> password.hashCode() }
                ) { _, password ->
                    PasswordItem(
                        modifier = Modifier.animateItemPlacement(),
                        password = password,
                        onDeleteClick = {
                            viewModel.onEvent(PasswordsEvents.DeletePassword(password))
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Password Deleted",
                                    actionLabel = "Undo",
                                    duration = SnackbarDuration.Long
                                )

                                if (result == SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(PasswordsEvents.RestorePassword)
                                }
                            }
                        },
                        onItemClick = {
                            navController.navigate(Screens.AddEditPasswordScreen.route + "?passwordId=${password.id}")
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun LoadImageFromUrl(url: String) {
    var image by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        val bitmap = withContext(Dispatchers.IO) {
            Picasso.get().load(url).get()
        }
        image = bitmap
    }

    image?.let {
        Image(bitmap = it.asImageBitmap(), contentDescription = null, alignment = Alignment.Center)
    }
}