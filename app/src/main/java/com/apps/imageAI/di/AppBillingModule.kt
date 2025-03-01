package com.apps.imageAI.di

import com.apps.imageAI.components.InAppPurchaseHelper
import com.apps.imageAI.components.InAppPurchaseHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
interface AppBillingModule {

    @Binds
    @ActivityScoped
    fun bindInAppHelpers(impl: InAppPurchaseHelperImpl): InAppPurchaseHelper

}