package com.hamiddev.nikeshop.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.product.ProductRepository
import com.hamiddev.nikeshop.data.repo.user.TokenContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productRepository: ProductRepository,private val cartRepository: CartRepository) :
    BaseViewModel() {
    val productsLiveData = MutableLiveData<Resource<List<Product>>>()
    var searchQuery: String = ""


    fun search(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            productsLiveData.postValue(Resource.loading(null))
            productsLiveData.postValue(productRepository.search(query))
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
            if (TokenContainer.token != null){
                cartRepository.getCartItemsCount().data?.let {
                    EventBus.getDefault().postSticky(it)
                }

            }
        }
    }

}