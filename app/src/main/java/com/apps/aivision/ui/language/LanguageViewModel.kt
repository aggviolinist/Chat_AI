package com.apps.aivision.ui.language

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivision.data.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) :
    ViewModel() {

    val selectedValue = mutableStateOf("")

    private val _currentLanguage = MutableStateFlow("")
    val currentLanguage get() = _currentLanguage.asStateFlow()


    fun getCurrentLanguage() = viewModelScope.launch {
        _currentLanguage.value = preferenceRepository.getCurrentLanguageCode()
        selectedValue.value = _currentLanguage.value
    }

    fun setCurrentLanguage(languageCode: String, language: String) = viewModelScope.launch {
        preferenceRepository.setCurrentLanguageCode(languageCode)
        preferenceRepository.setCurrentLanguage(language)
    }
}