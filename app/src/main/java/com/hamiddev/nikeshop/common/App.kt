package com.hamiddev.nikeshop.common

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        Fresco.initialize(this)
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // load token from SharedPreferences and save in an object
        userRepository.loadToken()


        // create notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            if (notificationManager != null)
                notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}