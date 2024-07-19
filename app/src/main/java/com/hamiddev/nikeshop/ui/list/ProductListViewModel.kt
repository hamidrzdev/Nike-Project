package com.hamiddev.nikeshop.ui.list

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
class ProductListViewModel @Inject constructor(val productRepository: ProductRepository) : BaseViewModel() {
    val productsLiveData = MutableLiveData<Resource<List<Product>>>(Resource.loading(null))
    val selectedSortTitleLiveData = MutableLiveData<Int>()

    var sort: Int? = null

    private val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )


    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productsLiveData.postValue(productRepository.products(sort!!))
        }
    }

    fun onSelectedSortChangedByUser(sort:Int){
        this.sort=sort
        this.selectedSortTitleLiveData.value=sortTitles[sort]
        getProducts()
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

}