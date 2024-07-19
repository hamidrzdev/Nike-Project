package com.hamiddev.nikeshop.service

import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation
import com.facebook.drawee.view.SimpleDraweeView
import com.google.android.material.shape.RoundedCornerTreatment
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.custom.NikeImageView

class FrescoImageLoadingServiceImpl : ImageLoadingService {
    override fun load(
        image: NikeImageView,
        url: String,
        placeholder: Int?,
        corner: Float?,
        crossFade: Boolean?
    ) {
        image.setImageURI(url)
    }

    override fun load(image: NikeImageView, drawableRes: Int?) {
        image.setActualImageResource(drawableRes!!)
    }
}