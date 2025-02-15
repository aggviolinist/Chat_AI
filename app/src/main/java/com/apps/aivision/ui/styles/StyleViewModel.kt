package com.apps.aivision.ui.styles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.aivision.data.model.StyleModel
import com.apps.aivision.data.repository.LocalResourceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StyleViewModel @Inject constructor(private val localResourceRepository: LocalResourceRepository):ViewModel(){


    private val _styles = MutableStateFlow(listOf<StyleModel>())
    val styles get() = _styles.asStateFlow()

    init {
        viewModelScope.launch {
            _styles.value = localResourceRepository.getStyles()
        }
    }

}