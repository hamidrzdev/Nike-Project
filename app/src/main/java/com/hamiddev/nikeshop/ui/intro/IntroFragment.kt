package com.hamiddev.nikeshop.ui.intro

import androidx.fragment.app.viewModels
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.common.navigate
import com.hamiddev.nikeshop.data.model.Intro
import com.hamiddev.nikeshop.databinding.IntroFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IntroFragment : BaseFragment<IntroFragmentBinding>(IntroFragmentBinding::inflate) {

    private val viewModel: IntroViewModel by viewModels()

    private val pageIntroProduct = Intro(
        R.drawable.intro_product,
        R.string.intro_product_title,
        R.string.intro_product_description,
        R.color.intro_blue
    )

    private val pageIntroProductCost = Intro(
        R.drawable.intro_product_cost,
        R.string.intro_product_cost_title,
        R.string.intro_product_cost_description,
        R.color.intro_red
    )

    private val pageIntroProductPost = Intro(
        R.drawable.intro_post_product,
        R.string.intro_product_post_title,
        R.string.intro_product_post_description,
        R.color.intro_blue2
    )

    private val pageIntroProductCorona = Intro(
        R.drawable.intro_corona_product,
        R.string.intro_product_corona_title,
        R.string.intro_product_corona_description,
        R.color.intro_blue3,
        true
    )

    private val introList = listOf(
        pageIntroProduct,
        pageIntroProductCost,
        pageIntroProductPost,
        pageIntroProductCorona
    )

    override fun viewCreated() {
        super.viewCreated()

        checkIsFirstEnter()

        handleIntro()
    }

    private fun checkIsFirstEnter() {
        if (viewModel.isFirstEnter())
            navigate(R.id.action_introFragment_to_homeFragment)
    }

    private fun handleIntro() {
        val adapter = IntroPageAdapter(this, introList)
        binding.viewPagerContainer.adapter = adapter
        binding.viewPagerDots.setViewPager2(binding.viewPagerContainer)

    }
}