package com.apps.imageAI.ui.recents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.imageAI.components.CreditHelpers
import com.apps.imageAI.data.model.RecentChat
import com.apps.imageAI.data.repository.PreferenceRepository
import com.apps.imageAI.data.repository.RecentChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecentChatsViewModel @Inject constructor(
    private val repository: RecentChatRepository,
    private val preferenceRepository: PreferenceRepository,
    private val creditHelpers: CreditHelpers
) : ViewModel() {

    private val _recentChats: MutableStateFlow<MutableList<RecentChat>> = MutableStateFlow(
        mutableListOf()
    )
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        true
    )

    val recentChats: StateFlow<MutableList<RecentChat>> = _recentChats.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    val isCreditsPurchased get() = creditHelpers.isCreditsPurchased
    val isFreeCreditsReceived get() = creditHelpers.isFreeCredits

    private val _darkMode = MutableStateFlow(true)
    val darkMode get() = _darkMode.asStateFlow()

    private var searchJob: Job? = null
   // val currentLanguageCode = mutableStateOf("en")

    fun loadThemeMode() = viewModelScope.launch {
        _darkMode.value = preferenceRepository.getDarkMode()
    }

    fun getAllChats() = viewModelScope.launch {
        _isLoading.value = true
        _recentChats.value = repository.getAllChats()
        _isLoading.value = false
    }

    fun deleteChatById(id: Long) = viewModelScope.launch {
        repository.deleteChat(id)
        _recentChats.value = repository.getAllChats()

    }

    fun clearAllChats() = viewModelScope.launch {
        repository.deleteAllChats()
        _recentChats.value = mutableListOf()

    }
    fun searchChats(query:String){
        searchJob?.cancel()
        searchJob = CoroutineScope(Dispatchers.IO).launch {
            _recentChats.value = repository.searchChats(query)
        }
    }
    fun resetSearch(){
        searchJob?.cancel()
        viewModelScope.launch {
            _recentChats.value = repository.getAllChats()
        }
    }

    fun resetCreditsDialog(){
        creditHelpers.resetFreeCredits()
    }

    fun getCurrentLanguageCode() = preferenceRepository.getCurrentLanguageCode()

}