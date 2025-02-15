package com.apps.aivision.ui.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivision.components.CreditHelpers
import com.apps.aivision.data.model.GPTModel
import com.apps.aivision.data.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    private val creditHelpers: CreditHelpers
) :
    ViewModel() {

    private val _currentGptModel = MutableStateFlow (GPTModel.gpt35Turbo)
    val currentGptModel get() = _currentGptModel.asStateFlow()
    val isCreditsPurchased get() = creditHelpers.isCreditsPurchased
    private val _currentLanguage = MutableStateFlow<String>("")
    val currentLanguage get() = _currentLanguage.asStateFlow()

    fun getCurrentGptModel() = viewModelScope.launch {
        _currentGptModel.value =  if (preferenceRepository.getGPTModel().contentEquals(GPTModel.gpt4.name)) GPTModel.gpt4 else GPTModel.gpt35Turbo
        //Log.e("Settings","value:${_currentGptModel.value.name}")
    }

    fun setGptModel(model:GPTModel) = viewModelScope.launch {
        preferenceRepository.setGPTModel(model.name)
        getCurrentGptModel()
        //Log.e("Settings","model:${getGPTModelUseCase().name}")
    }
    fun getCurrentLanguage() = viewModelScope.launch {
        _currentLanguage.value = preferenceRepository.getCurrentLanguage()
    }

}