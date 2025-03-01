package com.apps.imageAI.ui.dialogs

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.imageAI.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository
):ViewModel() {


    private var _isProcessing = mutableStateOf(false)
    val isProcessing:Boolean
        get() = _isProcessing.value
    private val _deleteSuccess = MutableStateFlow(false)
    val deleteSuccess = _deleteSuccess.asStateFlow()

    private var _deleteError = MutableStateFlow(false)
    val deleteError = _deleteError.asStateFlow()



    fun deleteAccount() = viewModelScope.launch {
        _isProcessing.value = true
        val result = firebaseRepository.deleteAccount()
        _isProcessing.value = false
        if (result)
        {
            _deleteSuccess.value = true
        }else{
            _deleteError.value = true
        }

    }


}