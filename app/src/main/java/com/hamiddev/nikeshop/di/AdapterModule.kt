package com.hamiddev.nikeshop.di

import android.content.Context
import com.hamiddev.nikeshop.service.ImageLoadingService
import com.hamiddev.nikeshop.ui.cart.CartItemAdapter
import com.hamiddev.nikeshop.ui.favorite.FavoriteProductAdapter
import com.hamiddev.nikeshop.ui.historyDetail.HistoryDetailImageAdapter
import com.hamiddev.nikeshop.ui.home.ProductListAdapter
import com.hamiddev.nikeshop.ui.orderHistory.OrderHistoryItemAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {

    @Provides
    fun provideProductAdapter(imageLoadingService: ImageLoadingService) =
        ProductListAdapter(imageLoadingService)

    @Provides
    fun provideFavoriteListAdapter(imageLoadingService: ImageLoadingService) =
        FavoriteProductAdapter(imageLoadingService)

    @Provides
    fun provideCartListAdapter(imageLoadingService: ImageLoadingService) =
        CartItemAdapter(imageLoadingService)

    @Provides
    fun provideHistoryImageAdapter(imageLoadingService: ImageLoadingService) =
        HistoryDetailImageAdapter(imageLoadingService)

    @Provides
    fun provideOrderHistoryAdapter(
        @ApplicationContext context: Context,
        imageLoadingService: ImageLoadingService
    ) =
        OrderHistoryItemAdapter(context,imageLoadingService)
}