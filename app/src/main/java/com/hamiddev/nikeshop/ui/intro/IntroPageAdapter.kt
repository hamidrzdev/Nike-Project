package com.hamiddev.nikeshop.ui.intro

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hamiddev.nikeshop.data.model.Intro

class IntroPageAdapter(fragment: Fragment, private val introList: List<Intro>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = introList.size

    override fun createFragment(position: Int): Fragment =
        IntroPageFragment.newInstance(introList[position])
}