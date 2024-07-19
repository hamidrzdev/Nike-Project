package com.hamiddev.nikeshop.ui.intro

import android.content.SharedPreferences
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.FIRST_ENTER
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    BaseViewModel() {

    fun isFirstEnter(): Boolean =
        sharedPreferences.getBoolean(FIRST_ENTER, false)


    fun firstEnter() {
        sharedPreferences.edit().apply {
            putBoolean(FIRST_ENTER, true)
        }.apply()
    }
}