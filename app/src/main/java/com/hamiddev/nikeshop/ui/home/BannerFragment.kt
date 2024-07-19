package com.hamiddev.nikeshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.EXTRA_KEY_DATA
import com.hamiddev.nikeshop.custom.NikeImageView
import com.hamiddev.nikeshop.data.model.Banner
import com.hamiddev.nikeshop.service.ImageLoadingService
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject

@AndroidEntryPoint
class BannerFragment : Fragment() {

    @Inject
    lateinit var imageLoadingService: ImageLoadingService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView =
            inflater.inflate(R.layout.fragment_banner, container, false) as NikeImageView

        val banner =
            requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA) ?: throw IllegalStateException(
                "Banner cannot be null"
            )
        imageLoadingService.load(imageView, banner.image)

        return imageView
    }


    companion object {
        fun newInstance(banner: Banner): Fragment =
            BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA, banner)
                }
            }
    }
}