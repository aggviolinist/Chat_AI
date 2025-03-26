package com.apps.imageAI.data.repository

import android.app.Application
import android.content.SharedPreferences
import com.apps.imageAI.components.PreferenceConstant
import com.apps.imageAI.data.model.GPTModel
import java.util.Locale
import javax.inject.Inject

interface PreferenceRepository {
    fun getDefaultPreference():SharedPreferences
    fun setDarkMode(isDarkMode: Boolean)
    fun getDarkMode(): Boolean
    fun getIsGuest(): Boolean
    fun setIsGuest(isGuest:Boolean)
    fun getGPTModel(): String
    fun setGPTModel(modelName: String)
    fun setIsImageGen(enabled: Boolean)
    fun getIsImageGen(): Boolean
    fun setCurrentLanguage(language: String)
    fun getCurrentLanguage(): String
    fun setCurrentLanguageCode(language: String)
    fun getCurrentLanguageCode(): String

    fun setVisionDailyCount(count: Int)
    fun getVisionDailyCount(): Int
    fun setGenerationDailyCount(count: Int)
    fun getGenerationDailyCount(): Int
    fun setGPT4DailyCount(count: Int)
    fun getGPT4DailyCount(): Int
}


class PreferenceRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val app: Application
) : PreferenceRepository {

    override fun getDefaultPreference(): SharedPreferences = sharedPreferences

    override fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(PreferenceConstant.DARK_MODE, isDarkMode).apply()
    }

    override fun getDarkMode(): Boolean {
        return sharedPreferences.getBoolean(
            PreferenceConstant.DARK_MODE,
            true
        )
    }


    override fun getGPTModel(): String {
        return sharedPreferences.getString(PreferenceConstant.GPT_DEFAULT_MODEL,null)?: GPTModel.GPT_3_5_TURBO.name
    }

    override fun setGPTModel(modelName: String) {
        sharedPreferences.edit().putString(PreferenceConstant.GPT_DEFAULT_MODEL, modelName)
            .apply()
    }

    override fun getIsGuest(): Boolean {
        return sharedPreferences.getBoolean(
            PreferenceConstant.IS_GUEST_KEY,
            false
        )
    }

    override fun setIsGuest(isGuest: Boolean) {
        sharedPreferences.edit().putBoolean(PreferenceConstant.IS_GUEST_KEY, isGuest).commit()
    }

    override fun setIsImageGen(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(PreferenceConstant.IMAGE_GENERATION, enabled).apply()
    }

    override fun getIsImageGen(): Boolean {
        return sharedPreferences.getBoolean(
            PreferenceConstant.IMAGE_GENERATION,
            true
        )
    }

    override fun setCurrentLanguage(language: String) {
        sharedPreferences.edit().putString(PreferenceConstant.LANGUAGE_NAME, language).apply()
    }

    override fun getCurrentLanguage(): String =
        sharedPreferences.getString(
            PreferenceConstant.LANGUAGE_NAME,
            Locale.getDefault().displayLanguage
        ) ?: Locale.getDefault().displayLanguage

    override fun setCurrentLanguageCode(language: String) {
        sharedPreferences.edit().putString(PreferenceConstant.LANGUAGE_CODE, language).apply()
    }

    override fun getCurrentLanguageCode(): String =
        sharedPreferences.getString(
            PreferenceConstant.LANGUAGE_CODE,
            Locale.getDefault().language
        ) ?: Locale.getDefault().language

    override fun setVisionDailyCount(count: Int) {
        sharedPreferences.edit().putInt(PreferenceConstant.VISION_LIMIT, count).apply()
    }

    override fun getVisionDailyCount(): Int {
        return sharedPreferences.getInt(PreferenceConstant.VISION_LIMIT, 0)
    }

    override fun setGenerationDailyCount(count: Int) {
        sharedPreferences.edit().putInt(PreferenceConstant.GENERATION_LIMIT, count).apply()
    }

    override fun getGenerationDailyCount(): Int {
        return sharedPreferences.getInt(PreferenceConstant.GENERATION_LIMIT, 0)
    }

    override fun setGPT4DailyCount(count: Int) {
        sharedPreferences.edit().putInt(PreferenceConstant.GPT4_LIMIT, count).apply()
    }

    override fun getGPT4DailyCount(): Int {
        return sharedPreferences.getInt(PreferenceConstant.GPT4_LIMIT, 0)
    }

}