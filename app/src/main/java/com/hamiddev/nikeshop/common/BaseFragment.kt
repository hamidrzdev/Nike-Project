package com.hamiddev.nikeshop.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.hamiddev.nikeshop.ui.cart.CartViewModel
import com.hamiddev.nikeshop.ui.home.HomeViewModel
import org.greenrobot.eventbus.EventBus

open class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    Fragment(), BaseView {

    val vie : CartViewModel by viewModels()

    private var _binding: VB? = null
    val binding: VB
        get() = _binding!!

    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated()
    }

    open fun viewCreated() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}