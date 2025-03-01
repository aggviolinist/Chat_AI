package com.apps.imageAI.di

import com.apps.imageAI.components.ApiKeyHelpers
import com.apps.imageAI.components.Constants
import com.apps.imageAI.data.source.remote.imageAIService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppNetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(apiKeyHelpers: ApiKeyHelpers):OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            /*.addInterceptor(logging)*/
        .readTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideimageAIService(retrofit: Retrofit): imageAIService=
        retrofit.create(imageAIService::class.java)

   }