package com.apps.imageAI.di

import android.content.Context
import androidx.room.Room
import com.apps.imageAI.data.source.local.imageAIDatabase
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
    fun provideRoomDb(@ApplicationContext appContext: Context): imageAIDatabase =
        Room.databaseBuilder(
            appContext,
            imageAIDatabase::class.java,
            "ImageAIdb.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideimageAIDao(ImageAIDatabase: imageAIDatabase) = ImageAIDatabase.ImageAIDao()
}