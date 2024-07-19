package com.hamiddev.nikeshop.di

import com.hamiddev.nikeshop.service.FrescoImageLoadingServiceImpl
import com.hamiddev.nikeshop.service.ImageLoadingService
import com.hamiddev.nikeshop.ui.home.ProductListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewComponent::class)
object ViewModule {

    @Provides
    fun provideProductAdapter(imageLoadingService: ImageLoadingService) =
        ProductListAdapter(imageLoadingService)

}