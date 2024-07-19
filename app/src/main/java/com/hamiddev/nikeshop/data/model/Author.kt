package com.hamiddev.nikeshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val email: String
):Parcelable