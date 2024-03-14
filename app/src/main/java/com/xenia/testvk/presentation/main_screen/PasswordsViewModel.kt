package com.xenia.testvk.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.testvk.domain.ItemModel
import com.xenia.testvk.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _passwordsState = mutableStateOf(PasswordState())
    val passwordsState: State<PasswordState> = _passwordsState

    private var recentlyDeletedPassword: ItemModel? = null
    private var allPasswords: Job? = null

    init {
        getPasswords()
    }

    private fun getPasswords(){
        allPasswords?.cancel()
        allPasswords = useCases.allPasswords()?.onEach {
            _passwordsState.value = _passwordsState.value.copy(
                passwords = it
            )
        }?.launchIn(viewModelScope)
    }

    fun onEvent(event: PasswordsEvents){
        when (event){
            is PasswordsEvents.DeletePassword -> {
                viewModelScope.launch {
                    useCases.deletePassword(event.password)
                }
                recentlyDeletedPassword = event.password
            }

            is PasswordsEvents.RestorePassword -> {
                viewModelScope.launch {
                    useCases.addPassword(recentlyDeletedPassword ?: return@launch)
                }
            }
        }
    }
}