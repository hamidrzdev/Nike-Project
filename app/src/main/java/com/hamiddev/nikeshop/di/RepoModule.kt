package com.hamiddev.nikeshop.di

import android.content.SharedPreferences
import com.hamiddev.nikeshop.data.repo.banner.BannerRemoteDataSource
import com.hamiddev.nikeshop.data.repo.banner.BannerRepository
import com.hamiddev.nikeshop.data.repo.banner.BannerRepositoryImpl
import com.hamiddev.nikeshop.data.repo.cart.CartRemoteDataSource
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.cart.CartRepositoryImpl
import com.hamiddev.nikeshop.data.repo.comment.CommentRemoteDataSource
import com.hamiddev.nikeshop.data.repo.comment.CommentRepository
import com.hamiddev.nikeshop.data.repo.comment.CommentRepositoryImpl
import com.hamiddev.nikeshop.data.repo.order.OrderRemoteDataSource
import com.hamiddev.nikeshop.data.repo.order.OrderRepository
import com.hamiddev.nikeshop.data.repo.order.OrderRepositoryImpl
import com.hamiddev.nikeshop.data.repo.product.ProductLocalDataSource
import com.hamiddev.nikeshop.data.repo.product.ProductRemoteDataSource
import com.hamiddev.nikeshop.data.repo.product.ProductRepository
import com.hamiddev.nikeshop.data.repo.product.ProductRepositoryImpl
import com.hamiddev.nikeshop.data.repo.user.UserLocalDataSource
import com.hamiddev.nikeshop.data.repo.user.UserRemoteDataSource
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import com.hamiddev.nikeshop.data.repo.user.UserRepositoryImpl
import com.hamiddev.nikeshop.db.DatabaseDao
import com.hamiddev.nikeshop.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideBannerRepository(apiService: ApiService): BannerRepository =
        BannerRepositoryImpl(BannerRemoteDataSource(apiService))

    @Provides
    @Singleton
    fun provideCartRepository(apiService: ApiService): CartRepository =
        CartRepositoryImpl(CartRemoteDataSource(apiService))

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ApiService,
        databaseDao: DatabaseDao
    ): ProductRepository =
        ProductRepositoryImpl(
            ProductRemoteDataSource(apiService),
            ProductLocalDataSource(databaseDao)
        )

    @Provides
    @Singleton
    fun provideCommentRepository(apiService: ApiService): CommentRepository =
        CommentRepositoryImpl(CommentRemoteDataSource(apiService))

    @Provides
    @Singleton
    fun provideOrderRepository(apiService: ApiService): OrderRepository =
        OrderRepositoryImpl(OrderRemoteDataSource(apiService))

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService,
        sharedPreferences: SharedPreferences
    ): UserRepository =
        UserRepositoryImpl(UserRemoteDataSource(apiService), UserLocalDataSource(sharedPreferences))
}