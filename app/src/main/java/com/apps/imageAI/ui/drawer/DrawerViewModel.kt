package com.apps.imageAI.ui.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.imageAI.components.CreditHelpers
import com.apps.imageAI.data.model.GPTModel
import com.apps.imageAI.data.repository.PreferenceRepository
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

    private val _currentGptModel = MutableStateFlow (GPTModel.GPT_3_5_TURBO)
    val currentGptModel get() = _currentGptModel.asStateFlow()
    val isCreditsPurchased get() = creditHelpers.isCreditsPurchased
    private val _currentLanguage = MutableStateFlow("")
    val currentLanguage get() = _currentLanguage.asStateFlow()

    fun getCurrentGptModel() = viewModelScope.launch {
        (if (preferenceRepository.getGPTModel().contentEquals(GPTModel.GPT_4.name)) GPTModel.GPT_4 else GPTModel.GPT_3_5_TURBO).also { _currentGptModel.value = it }
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