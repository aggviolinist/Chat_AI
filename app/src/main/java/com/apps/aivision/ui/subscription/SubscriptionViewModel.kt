package com.apps.aivision.ui.subscription

import androidx.lifecycle.ViewModel
import com.apps.aivision.data.repository.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel@Inject constructor(private val preferenceRepository: PreferenceRepository):ViewModel() {
    fun getCurrentLanguageCode() = preferenceRepository.getCurrentLanguageCode()
}