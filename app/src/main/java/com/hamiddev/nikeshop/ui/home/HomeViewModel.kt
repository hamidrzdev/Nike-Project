package com.hamiddev.nikeshop.ui.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.MAX_HOME_ITEM
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Banner
import com.hamiddev.nikeshop.data.model.*
import com.hamiddev.nikeshop.data.repo.banner.BannerRepository
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.product.ProductRepository
import com.hamiddev.nikeshop.data.repo.user.TokenContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bannerRepository: BannerRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {
    val latestProductLiveData = MutableLiveData<Resource<List<Product>>>(Resource.loading(null))
    val popularProductLiveData = MutableLiveData<Resource<List<Product>>>(Resource.loading(null))
    val priceDescProductLiveData = MutableLiveData<Resource<List<Product>>>(Resource.loading(null))
    val priceAscProductLiveData = MutableLiveData<Resource<List<Product>>>(Resource.loading(null))
    val bannerLiveData = MutableLiveData<Resource<List<Banner>>>()

    @SuppressLint("NullSafeMutableLiveData")
    fun getData() {
        progressLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {

            fetchLatestProduct()

            fetchPopularProduct()

            fetchPriceDescProduct()

            fetchPriceAscProduct()

            fetchBanner()

            progressLiveData.postValue(false)
        }
    }

    private suspend fun fetchBanner() {
        val bannerJob = bannerRepository.banners()
        bannerLiveData.postValue(bannerJob)
    }

    private suspend fun fetchLatestProduct() {
        fetchProduct(SORT_LATEST, latestProductLiveData)
    }

    private suspend fun fetchPopularProduct() {
        fetchProduct(SORT_POPULAR, popularProductLiveData)
    }

    private suspend fun fetchPriceDescProduct() {
        fetchProduct(SORT_PRICE_DESC, priceDescProductLiveData)
    }

    private suspend fun fetchPriceAscProduct() {
        fetchProduct(SORT_PRICE_ASC, priceAscProductLiveData)
    }

    suspend fun fetchProduct(sort: Int, liveData: MutableLiveData<Resource<List<Product>>>) {
        productRepository.products(sort).also {
            it.data?.subList(0, MAX_HOME_ITEM)
        }.run {
            liveData.postValue(this)
        }
    }

    fun addFavorite(product: Product) {
        viewModelScope.launch {
            if (productRepository.addFavorite(product) != (-1).toLong())
                responseLiveData.value = R.string.add_favorite_product_successful
            else
                responseLiveData.value = R.string.unknownError
        }
    }

    fun removeFavorite(product: Product) {
        viewModelScope.launch {
            if (productRepository.removeFavorite(product) != -1)
                responseLiveData.value = R.string.remove_favorite_product_successful
            else
                responseLiveData.value = R.string.unknownError
        }
    }

    fun getCount() {
        viewModelScope.launch {
            if (TokenContainer.token != null) {

                val count = cartRepository.getCartItemsCount()

                Timber.i("timber main viewModel ${count.data?.count}")
                count.data?.let {
                    EventBus.getDefault().postSticky(it)
                }
            }
        }
    }

}


