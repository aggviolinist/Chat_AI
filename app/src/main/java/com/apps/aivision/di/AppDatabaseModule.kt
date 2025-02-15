package com.apps.aivision.di

import android.content.Context
import androidx.room.Room
import com.apps.aivision.data.source.local.AIVisionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {


    @Provides
    @Singleton
    fun provideRoomDb(@ApplicationContext appContext: Context): AIVisionDatabase =
        Room.databaseBuilder(
            appContext,
            AIVisionDatabase::class.java,
            "AIVisiondb.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideAIVisionDao(aiVisionDatabase: AIVisionDatabase) = aiVisionDatabase.aiVisionDao()
}