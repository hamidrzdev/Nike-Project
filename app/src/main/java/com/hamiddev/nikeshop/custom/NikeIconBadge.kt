package com.hamiddev.nikeshop.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hamiddev.nikeshop.R

class NikeIconBadge(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var badge: TextView
    private val icon: ImageView

    init {
        inflate(context, R.layout.custom_icon_badge, this)

        icon = findViewById(R.id.icon)
        badge = findViewById(R.id.badge)

        if (attrs != null) {
            val myAttr = context.obtainStyledAttributes(attrs, R.styleable.NikeIconBadge)
            val badgeValue = myAttr.getString(R.styleable.NikeIconBadge_nike_badge_number)
            val showBadge = myAttr.getBoolean(R.styleable.NikeIconBadge_nike_show_badge, true)
            val iconValue = myAttr.getDrawable(R.styleable.NikeIconBadge_nike_icon)

            badge.visibility = if (showBadge) VISIBLE else INVISIBLE
            badge.text = badgeValue

            icon.setImageDrawable(iconValue)


            myAttr.recycle()
        }
    }

    fun showBadge(show: Boolean) {
        badge.visibility = if (show) VISIBLE else INVISIBLE
    }

    fun imageBadge(value: Drawable) {
        icon.setImageDrawable(value)
    }

    fun setBadgeText(value: Int) {
        badge.text = value.toString()
    }
}