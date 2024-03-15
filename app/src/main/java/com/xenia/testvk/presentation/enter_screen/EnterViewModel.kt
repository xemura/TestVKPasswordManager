package com.xenia.testvk.presentation.enter_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xenia.testvk.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EnterViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    fun setPassword(filesDir: File, otpValue: String) {
        viewModelScope.launch {
            useCases.setMasterPassword(filesDir, otpValue)
        }
    }

    fun checkFileIsExist(filesDir: File): Boolean? {
        return useCases.checkFileMasterPassword(filesDir)
    }

    fun validatePassword(filesDir: File, originalPassword: String): Boolean {
        return useCases.validateMasterPassword(filesDir, originalPassword)
    }
}