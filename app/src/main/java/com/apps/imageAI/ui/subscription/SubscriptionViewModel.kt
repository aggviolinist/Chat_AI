package com.apps.imageAI.ui.subscription

import androidx.lifecycle.ViewModel
import com.apps.imageAI.data.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel@Inject constructor(private val preferenceRepository: PreferenceRepository):ViewModel() {
    fun getCurrentLanguageCode() = preferenceRepository.getCurrentLanguageCode()
}