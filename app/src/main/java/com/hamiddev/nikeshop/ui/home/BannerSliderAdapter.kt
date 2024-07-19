package com.hamiddev.nikeshop.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hamiddev.nikeshop.data.model.Banner

class BannerSliderAdapter(fragment: Fragment, private val banners: List<Banner>) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment =
        BannerFragment.newInstance(banners[position])

    override fun getItemCount() = banners.size
}