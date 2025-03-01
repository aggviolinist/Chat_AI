package com.apps.imageAI.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.imageAI.data.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()
    private val _darkMode = MutableStateFlow(true)
    val darkMode get() = _darkMode.asStateFlow()

    private val _shouldDrawerUpdate = MutableStateFlow(false)
    val shouldDrawerUpdate get() = _shouldDrawerUpdate.asStateFlow()
    private val _isImageGeneration = MutableStateFlow (false)
    val isImageGeneration get() = _isImageGeneration.asStateFlow()

    private val _currentLanguageCode = MutableStateFlow("en")
    val currentLanguageCode get() = _currentLanguageCode.asStateFlow()
    init {
        viewModelScope.launch {
            delay(1000)
            _isLoading.value = false
        }
        getDarkMode()
        getIsImageGen()
        Log.e("MainActivityViewModel","init called....")
    }

    private fun getDarkMode() = viewModelScope.launch {
        _darkMode.value = preferenceRepository.getDarkMode()
    }

    fun setDarkMode(isDarkMode: Boolean) = viewModelScope.launch {
        preferenceRepository.setDarkMode(isDarkMode)
        getDarkMode()
    }
    fun isGuestMode() = preferenceRepository.getIsGuest()
    fun resetGuestMode() = viewModelScope.launch {
        preferenceRepository.setIsGuest(false)
    }
    fun resetDrawer(){
        _shouldDrawerUpdate.value = !_shouldDrawerUpdate.value
    }

    private fun getIsImageGen()= viewModelScope.launch {
        _isImageGeneration.value = preferenceRepository.getIsImageGen()
    }

    fun enableImageGenerations(isEnabled:Boolean) = viewModelScope.launch {
        preferenceRepository.setIsImageGen(isEnabled)
        getIsImageGen()
    }
    fun getCurrentLanguageCode() = viewModelScope.launch {
        _currentLanguageCode.value = preferenceRepository.getCurrentLanguageCode()
    }

}