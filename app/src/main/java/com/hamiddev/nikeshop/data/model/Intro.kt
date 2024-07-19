package com.hamiddev.nikeshop.data.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Intro(
    @DrawableRes val imageId: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
    @ColorRes val color: Int,
    val showEnter: Boolean = false
) : Parcelable