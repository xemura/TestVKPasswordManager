package com.xenia.testvk.presentation.add_edit_password_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _imageState = mutableStateOf(PasswordTextFieldState(hint = "Image"))
    val imageState: State<PasswordTextFieldState> = _imageState

    private val _websiteState = mutableStateOf(PasswordTextFieldState(hint = "Website"))
    val websiteState: State<PasswordTextFieldState> = _websiteState

    private val _loginState = mutableStateOf(PasswordTextFieldState(hint = "Login..."))
    val loginState: State<PasswordTextFieldState> = _loginState

    private val _passwordState = mutableStateOf(PasswordTextFieldState(hint = "Password"))
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private var _uiEvent = MutableSharedFlow<UIEvents>()
    val uiEvents: SharedFlow<UIEvents> = _uiEvent

    private var pressedPasswordId: Int? = null

    init {
        savedStateHandle.get<Int>("passwordId")?.let { passwordId ->
            if (passwordId != -1) {
                pressedPasswordId = passwordId
                viewModelScope.launch(Dispatchers.IO) {
                    delay(100)
                    useCases.getPasswordById(passwordId).let { password ->
                        _imageState.value = _imageState.value.copy(
                            text = password.imageUrl,
                        )
                        _passwordState.value = _passwordState.value.copy(
                            text = password.password,
                        )

                        _websiteState.value = _websiteState.value.copy(
                            text = password.websiteUrl,
                        )

                        _loginState.value = _loginState.value.copy(
                            text = password.login,
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditPasswordEvent) {
        when (event) {
            is AddEditPasswordEvent.SavePassword -> {
                viewModelScope.launch {
                    Log.d("TAG", pressedPasswordId.toString())
                    if (pressedPasswordId == null) {
                        useCases.addPassword(
                            ItemModel(
                                imageUrl = _imageState.value.text,
                                password = _passwordState.value.text,
                                websiteUrl = _websiteState.value.text,
                                login = _loginState.value.text
                            )
                        )
                    } else {
                        useCases.addPassword(
                            ItemModel(
                                id = pressedPasswordId!!,
                                imageUrl = _imageState.value.text,
                                password = _passwordState.value.text,
                                websiteUrl = _websiteState.value.text,
                                login = _loginState.value.text
                            )
                        )
                    }

                    _uiEvent.emit(UIEvents.SavePassword)
                }
            }

            is AddEditPasswordEvent.EnteringImage -> {
                _imageState.value = _imageState.value.copy(
                    text = event.value
                )
            }

            is AddEditPasswordEvent.EnteringPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }

            is AddEditPasswordEvent.EnteringWebsite -> {
                _websiteState.value = _websiteState.value.copy(
                    text = event.value
                )
            }

            is AddEditPasswordEvent.EnteringLogin -> {
                _loginState.value = _loginState.value.copy(
                    text = event.value
                )
            }
        }
    }

    sealed class UIEvents {
        data object SavePassword : UIEvents()
    }
}