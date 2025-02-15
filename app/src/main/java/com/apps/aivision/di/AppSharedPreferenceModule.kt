package com.apps.aivision.di

import android.content.Context
import android.content.SharedPreferences
import com.apps.aivision.components.PreferenceConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppSharedPreferenceModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PreferenceConstant.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

}
