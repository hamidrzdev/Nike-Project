package com.hamiddev.nikeshop.common

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import org.greenrobot.eventbus.EventBus

open class BaseActivity:AppCompatActivity(),BaseView {
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("RootView must be instance of CoordinatorLayout")
            } else
                return viewGroup
        }
    override val viewContext: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}