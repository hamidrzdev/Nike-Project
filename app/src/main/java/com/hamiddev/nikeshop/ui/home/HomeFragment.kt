package com.hamiddev.nikeshop.ui.home

import android.service.quicksettings.Tile
import androidx.fragment.app.viewModels
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.*
import com.hamiddev.nikeshop.data.model.Status.*
import com.hamiddev.nikeshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    val viewModel: HomeViewModel by viewModels()


    override fun viewCreated() {
        super.viewCreated()

        callNecessaryMethodFromViewModel()

        observeToData()
    }

    private fun callNecessaryMethodFromViewModel() {
        // get items count which are in the cart
        viewModel.getCount()

        // get all products
        viewModel.getData()
    }


    private fun observeToData() {

        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        // get banners
        viewModel.bannerLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                ERROR -> {
                    Timber.i("timber product banner error -> ${it.errorResponse?.errorMessage}")
                }

                SUCCESS -> {
                    val bannerSliderAdapter = BannerSliderAdapter(this, it.data!!)
                    with(binding.bannerSliders) {
                        adapter = bannerSliderAdapter
                        /* binding.bannerSliders.post {
                             handleHeight()
                         }*/
                        binding.sliderIndicator.setViewPager2(this)
                    }
                }

                LOADING -> {
                    // nothing to do
                }
            }
        }

        // get latest product
        viewModel.latestProductLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                ERROR -> {
                    Timber.i("timber product latest error -> ${it.errorResponse?.errorMessage}")
                }

                SUCCESS -> {
                    binding.item1.onProductClicked = { product ->
                        actionToProductDetail(product)
                    }
                    binding.item1.onFavoriteClicked = { product ->
                        onFavoriteClicked(product)
                    }
                    binding.item1.onShowMoreClicked = {
                        actionToList(SORT_LATEST)
                    }
                    binding.item1.fillList(it.data!!)
                }

                LOADING -> {
                    // nothing to do
                }
            }
        }

        // get popular product
        viewModel.popularProductLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                ERROR -> {
                    Timber.i("timber product popular error -> ${it.errorResponse?.errorMessage}")
                }

                SUCCESS -> {
                    binding.item2.onProductClicked = { product ->
                        actionToProductDetail(product)
                    }
                    binding.item2.onFavoriteClicked = { product ->
                        onFavoriteClicked(product)
                    }
                    binding.item2.onShowMoreClicked = {
                        actionToList(SORT_LATEST)
                    }

                    binding.item2.fillList(it.data!!)
                }

                LOADING -> {
                    // nothing to do
                }
            }
        }

        // get high to low product
        viewModel.priceDescProductLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                ERROR -> {
                    Timber.i("timber product high error -> ${it.errorResponse?.errorMessage}")
                }

                SUCCESS -> {
                    binding.item3.onProductClicked = { product ->
                        actionToProductDetail(product)
                    }
                    binding.item3.onFavoriteClicked = { product ->
                        onFavoriteClicked(product)
                    }
                    binding.item3.onShowMoreClicked = {
                        actionToList(SORT_PRICE_DESC)
                    }

                    binding.item3.fillList(it.data!!)
                }

                LOADING -> {
                    // nothing to do
                }
            }
        }

        // get low to high product
        viewModel.priceAscProductLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                ERROR -> {
                    Timber.i("timber product low error -> ${it.errorResponse?.errorMessage}")
                }

                SUCCESS -> {
                    binding.item4.onProductClicked = { product ->
                        actionToProductDetail(product)
                    }
                    binding.item4.onFavoriteClicked = { product ->
                        onFavoriteClicked(product)
                    }
                    binding.item4.onShowMoreClicked = {
                        actionToList(SORT_PRICE_ASC)
                    }

                    binding.item4.fillList(it.data!!)
                }

                LOADING -> {
                    // nothing to do
                }
            }
        }

        viewModel.responseLiveData.observe(viewLifecycleOwner) {
            showSnack(getString(it))
        }
    }


    /*  private fun ViewPager2.handleHeight() {
          with(binding.bannerSliders) {
              val viewPagerHeight = (((measuredWidth - convertDpToPixel(
                  32f,
                  requireContext()
              )) * 173) / 328).toInt()
              layoutParams = layoutParams.also {
                  it.height = viewPagerHeight
              }
              Timber.i("timber ${convertPixelsToDp(viewPagerHeight.toFloat(), requireContext())}")
          }
      }*/


    private fun actionToProductDetail(product: Product) {
        navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product))
    }

    private fun actionToList(sort: Int) {
        navigate(HomeFragmentDirections.actionHomeFragmentToProductListFragment(sort))
    }

    private fun onFavoriteClicked(product: Product) {
        if (product.isFavorite)
            viewModel.removeFavorite(product)
        else
            viewModel.addFavorite(product)
    }
}
