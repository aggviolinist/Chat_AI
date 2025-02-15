package com.apps.aivision.ui.prompts

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivision.data.model.AiPromptModel
import com.apps.aivision.data.model.AiPromptsCategoryModel
import com.apps.aivision.data.repository.LocalResourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiPromptsViewModel @Inject constructor(private val localResourceRepository: LocalResourceRepository) : ViewModel() {


    private val _promptsList: MutableStateFlow<List<AiPromptsCategoryModel>> = MutableStateFlow(mutableListOf())
    val promptsList = _promptsList.asStateFlow()
    private val _categories: MutableStateFlow<List<String>> = MutableStateFlow(mutableListOf())
    val categories = _categories.asStateFlow()
    private val allPrompts = mutableStateListOf<AiPromptsCategoryModel>()
    var selectedIndex = 0

    init {
        viewModelScope.launch {
            _categories.value= localResourceRepository.getFiltersOptions()
            _promptsList.value = localResourceRepository.getDefaultPrompts()
        }
        viewModelScope.launch {
            allPrompts.clear()
            allPrompts.addAll(localResourceRepository.getPrompts())
        }
    }

    fun loadPrompts()= viewModelScope.launch {

       /* if (allPrompts.isEmpty())
        {
            allPrompts.addAll(localResourceRepository.getPrompts())
        }*/
        /*if (selectedIndex==0)
        {
            _promptsList.value = allPrompts
        } else{
            _promptsList.value = listOf(allPrompts[selectedIndex-1])
        }*/
        _promptsList.value = listOf(allPrompts[selectedIndex])
    }

    fun filterByCategory (category:String){
        selectedIndex = categories.value.indexOf(category)
        loadPrompts()
    }

    fun isSelected(category:String) = categories.value.indexOf(category) == selectedIndex
}