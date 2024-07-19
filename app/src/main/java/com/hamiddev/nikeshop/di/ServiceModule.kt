package com.hamiddev.nikeshop.di

import android.app.NotificationManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamiddev.nikeshop.common.BASE_URL
import com.hamiddev.nikeshop.common.NikeAuthenticator
import com.hamiddev.nikeshop.data.repo.user.*
import com.hamiddev.nikeshop.db.AppDatabase
import com.hamiddev.nikeshop.db.DatabaseDao
import com.hamiddev.nikeshop.service.ApiService
import com.hamiddev.nikeshop.service.CoilImageLoadingServiceImpl
import com.hamiddev.nikeshop.service.FrescoImageLoadingServiceImpl
import com.hamiddev.nikeshop.service.ImageLoadingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val oldRequest = it.request()
                val newRequestBuilder = oldRequest.newBuilder()
                if (TokenContainer.token != null)
                    newRequestBuilder.addHeader("Authorization", "Bearer ${TokenContainer.token}")
                newRequestBuilder.addHeader("accept", "application/json")
                newRequestBuilder.method(oldRequest.method, oldRequest.body)
                return@addInterceptor it.proceed(newRequestBuilder.build())
            }.authenticator(NikeAuthenticator())
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideImageLoadingService(): ImageLoadingService =
        FrescoImageLoadingServiceImpl()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    @Singleton
    fun provideDatabaseDao(database: AppDatabase): DatabaseDao =
        database.productDao()

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("app_settings", MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager =
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

}