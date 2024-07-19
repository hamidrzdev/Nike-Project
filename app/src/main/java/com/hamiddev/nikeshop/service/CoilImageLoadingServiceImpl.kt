package com.hamiddev.nikeshop.service

import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.shape.RoundedCornerTreatment
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.custom.NikeImageView

class CoilImageLoadingServiceImpl : ImageLoadingService {
    override fun load(
        image: NikeImageView,
        url: String,
        placeholder: Int?,
        corner: Float?,
        crossFade: Boolean?
    ) {
        image.load(url) {
//            placeholder(placeholder ?: R.drawable.ic_place)
            crossfade(crossFade ?: true)
            transformations(RoundedCornersTransformation(corner ?: 32f))
        }
    }

    override fun load(image: NikeImageView, drawableRes: Int?) {
//        image.setActualImageResource(drawableRes!!)
    }
}