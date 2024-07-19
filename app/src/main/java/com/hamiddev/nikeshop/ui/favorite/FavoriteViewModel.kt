package com.hamiddev.nikeshop.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.repo.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val productRepository: ProductRepository) :
    BaseViewModel() {

    val productsLiveData = MutableLiveData<Resource<List<Product>>>(Resource.loading(null))
    val productsSize = MutableLiveData<Int>()

    init {
        products()
    }

    fun products() {
        viewModelScope.launch(Dispatchers.IO) {
            productsLiveData.postValue(productRepository.favoriteProducts())
        }
    }

    fun removeFavorite(product: Product) {
        viewModelScope.launch {
            if (productRepository.removeFavorite(product) != -1) {
                responseLiveData.value = R.string.remove_favorite_product_successful
            } else
                responseLiveData.value = R.string.unknownError

            productsSize.value =
                if (productsLiveData.value?.data?.size!! != 0) (productsSize.value!! - 1) else 0
        }
    }

    fun updateProductSize(size: Int) {
        productsSize.value = size
    }
}