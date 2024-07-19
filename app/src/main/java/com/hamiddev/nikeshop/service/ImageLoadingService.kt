package com.hamiddev.nikeshop.service

import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.Placeholder
import com.hamiddev.nikeshop.custom.NikeImageView

interface ImageLoadingService {
    fun load(
        image: NikeImageView,
        url: String,
        @DrawableRes placeholder: Int? = null,
        corner: Float? = null,
        crossFade: Boolean? = null
    )
    fun load(
        image: NikeImageView,
        @DrawableRes drawableRes: Int? = null,
    )
}