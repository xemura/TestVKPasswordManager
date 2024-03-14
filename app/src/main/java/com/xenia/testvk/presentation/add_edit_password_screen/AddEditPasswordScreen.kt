package com.xenia.testvk.presentation.add_edit_password_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xenia.testvk.presentation.add_edit_password_screen.component.CustomTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditPasswordScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val imageState = viewModel.imageState.value
    val passwordState = viewModel.passwordState.value
    val loginState = viewModel.loginState.value
    val websiteState = viewModel.websiteState.value

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEvents.collectLatest { event ->
            when (event){
                is AddEditViewModel.UIEvents.SavePassword -> {
                    navController.navigateUp()
                }
            }
        }
    })

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditPasswordEvent.SavePassword)
            }) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = "Save Password")
            }
        }
    ) { contentPadding ->

        Box(modifier = Modifier.fillMaxSize().padding(top = contentPadding.calculateTopPadding()),
            contentAlignment = Alignment.Center) {

            Column {

                CustomTextField(
                    text = imageState.text,
                    hint = imageState.hint,
                    onTextChange = {
                        viewModel.onEvent(AddEditPasswordEvent.EnteringImage(it))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Place, contentDescription = "Service")
                    })

                Spacer(modifier = Modifier.padding(20.dp))

                CustomTextField(
                    text = loginState.text,
                    hint = loginState.hint,
                    onTextChange = {
                        viewModel.onEvent(AddEditPasswordEvent.EnteringLogin(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "UserName"
                        )
                    })

                Spacer(modifier = Modifier.padding(20.dp))

                CustomTextField(
                    text = passwordState.text,
                    hint = passwordState.hint,
                    onTextChange = {
                        viewModel.onEvent(AddEditPasswordEvent.EnteringPassword(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Password"
                        )
                    })

                Spacer(modifier = Modifier.padding(20.dp))

                CustomTextField(
                    text = websiteState.text,
                    hint = websiteState.hint,
                    isNotes = true,
                    onTextChange = {
                        viewModel.onEvent(AddEditPasswordEvent.EnteringWebsite(it))
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Place, contentDescription = "Notes")
                    })
            }
        }
    }
}