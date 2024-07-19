package com.hamiddev.nikeshop.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.facebook.drawee.view.SimpleDraweeView


class NikeImageView : SimpleDraweeView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)
}