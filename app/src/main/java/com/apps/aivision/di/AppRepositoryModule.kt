package com.apps.aivision.di

import com.apps.aivision.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepositoryModule {
    @Binds
    abstract fun provideChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    abstract fun provideMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

    @Binds
    abstract fun providePreferenceRepository(preferenceRepositoryImpl: PreferenceRepositoryImpl): PreferenceRepository

    @Binds
    abstract fun provideFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    abstract fun provideRecentChatRepository(recentChatRepositoryImpl: RecentChatRepositoryImpl): RecentChatRepository


    @Binds
    abstract fun provideLocalResourceRepository(localResourceRepositoryImpl: LocalResourceRepositoryImpl): LocalResourceRepository

    @Binds
    abstract fun provideImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository



}