package com.apps.imageAI.ui.welcome

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.imageAI.components.ApiKeyHelpers
import com.apps.imageAI.components.AppLogger
import com.apps.imageAI.components.Constants
import com.apps.imageAI.components.CreditHelpers
import com.apps.imageAI.components.JavaUtils
import com.apps.imageAI.data.repository.FirebaseRepository
import com.apps.imageAI.data.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val app: Application,
                                           private val firebaseRepository: FirebaseRepository, private val apiKeyHelpers: ApiKeyHelpers, private val creditHelpers: CreditHelpers, private val preferenceRepository: PreferenceRepository
):ViewModel() {
    private var _isProcessing = mutableStateOf(false)
    val isProcessing:Boolean
        get() = _isProcessing.value
    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    private var _authError = mutableStateOf(false)
    val authError:Boolean get() = _authError.value

    fun updateProcessingState(isProcessing:Boolean)
    {
        _isProcessing.value = isProcessing
    }

    fun authenticateWithToken(token:String) = viewModelScope.launch {

        val authResult = firebaseRepository.loginToFirebase(token)
        if (authResult)
        {
            firebaseRepository.setUpAccount()
            apiKeyHelpers.connect()
            _loginSuccess.value = true
            delay(400) // let the config load first
            creditHelpers.connect()
        }else{
            _authError.value = true
            _isProcessing.value =false
        }

    }

    fun loginWithEmailAndPass() = viewModelScope.launch {

        val id = JavaUtils.generateDeviceId().replace("-","_")
        val pass = JavaUtils.computeMD5Hash(id)
        val email = "$id${Constants.EMAIL_DOMAIN}"
        AppLogger.logE("WelcomeViewModel","email:${email.trim()} pass:${pass}")
        val authResult = firebaseRepository.loginToFirebase(email.trim(),pass)

        if (authResult)
        {
            firebaseRepository.setUpAccount()
            apiKeyHelpers.connect()
            _loginSuccess.value = true
            preferenceRepository.setIsGuest(true)
            delay(400) // let the config load first
            creditHelpers.connect()
        }else{
            _authError.value = true
            _isProcessing.value =false
        }

    }
}