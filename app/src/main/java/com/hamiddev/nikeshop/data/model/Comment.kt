package com.hamiddev.nikeshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val author: Author,
    val content: String,
    val date: String,
    val id: Int,
    val title: String
):Parcelable