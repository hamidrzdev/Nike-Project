package com.hamiddev.nikeshop.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.hamiddev.nikeshop.R
import org.w3c.dom.Text

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onBackButtonClickListener: OnClickListener? = null
        set(value) {
            field = value
            findViewById<ImageView>(R.id.backBtn).setOnClickListener(onBackButtonClickListener)
        }

    private val toolbarTitleTv: TextView
    private val toolbarBackBtn: ImageView
    private val toolbarIcon: NikeIconBadge

    init {
        inflate(context, R.layout.view_toolbar, this)

        toolbarTitleTv = findViewById(R.id.toolbarTitleTv)
        toolbarBackBtn = findViewById(R.id.backBtn)
        toolbarIcon = findViewById(R.id.toolbarIcon)

        if (attrs != null) {
            val myAttr = context.obtainStyledAttributes(attrs, R.styleable.NikeToolbar)
            val title = myAttr.getString(R.styleable.NikeToolbar_nt_title)
            val backBtn = myAttr.getBoolean(R.styleable.NikeToolbar_nt_show_back, true)
            val iconValue = myAttr.getDrawable(R.styleable.NikeToolbar_nt_custom_icon)

            toolbarBackBtn.visibility = if (backBtn) View.VISIBLE else View.INVISIBLE

            if (title != null && title.isNotEmpty())
                toolbarTitleTv.text = title


            if (iconValue != null) {
                toolbarBackBtn.visibility = INVISIBLE
                toolbarIcon.visibility = VISIBLE
                toolbarIcon.imageBadge(iconValue)
            }



            myAttr.recycle()
        }
    }

    fun showIcon(show: Boolean, value: Drawable) {
        toolbarBackBtn.visibility = if (show) INVISIBLE else VISIBLE
        toolbarIcon.visibility = if (show) VISIBLE else INVISIBLE
        toolbarIcon.imageBadge(value)
    }

    fun badgeValue(value: Int) {
        toolbarIcon.setBadgeText(value)
    }
}