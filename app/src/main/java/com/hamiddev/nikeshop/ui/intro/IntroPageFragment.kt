package com.hamiddev.nikeshop.ui.intro

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.common.EXTRA_KEY_DATA
import com.hamiddev.nikeshop.data.model.Intro
import com.hamiddev.nikeshop.databinding.IntroPageFragmentBinding
import com.hamiddev.nikeshop.service.ImageLoadingService
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject

@AndroidEntryPoint
class IntroPageFragment :
    BaseFragment<IntroPageFragmentBinding>(IntroPageFragmentBinding::inflate) {

    @Inject
    lateinit var imageLoadingService: ImageLoadingService
    val viewModel: IntroViewModel by viewModels()

    override fun viewCreated() {
        super.viewCreated()

        val intro =
            requireArguments().getParcelable<Intro>(EXTRA_KEY_DATA) ?: throw IllegalStateException(
                "Intro cannot be null"
            )

        binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), intro.color))

        imageLoadingService.load(binding.image, intro.imageId)
        binding.title.text = getString(intro.title)
        binding.description.text = getString(intro.description)

        val animTopToBottom = TranslateAnimation(-0f, 0f, -200f, 0f)
        val animFade = AlphaAnimation(0.1f, 1.0f)

        val animBottomToTop = TranslateAnimation(0f, 0f, 200f, 0f)

        val animationSetImage = AnimationSet(true).apply {
            addAnimation(animTopToBottom)
            addAnimation(animFade)
            duration = 2000
        }

        val animationSetText = AnimationSet(true).apply {
            addAnimation(animBottomToTop)
            addAnimation(animFade)
            duration = 2000
        }

        binding.image.startAnimation(animationSetImage)
        binding.linearLayout.startAnimation(animationSetText)

        binding.enter.visibility = if (intro.showEnter) View.VISIBLE else View.INVISIBLE
        binding.enter.setOnClickListener {
            viewModel.firstEnter()
            it.findNavController().navigate(R.id.action_introFragment_to_homeFragment)
        }

    }

    companion object {
        fun newInstance(intro: Intro): Fragment =
            IntroPageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA, intro)
                }
            }
    }
}