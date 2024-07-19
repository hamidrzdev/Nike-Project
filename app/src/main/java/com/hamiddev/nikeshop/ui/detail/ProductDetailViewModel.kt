package com.hamiddev.nikeshop.ui.detail

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.AddToCartResponse
import com.hamiddev.nikeshop.data.model.Comment
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.comment.CommentRepository
import com.hamiddev.nikeshop.data.repo.product.ProductRepository
import com.hamiddev.nikeshop.data.repo.user.TokenContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val commentRepository: CommentRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : BaseViewModel() {
    val productLiveData = MutableLiveData<Product>()
    val commentLiveData = MutableLiveData<Resource<List<Comment>>>(Resource.loading(null))
    val cartResponseLiveData = MutableLiveData<Resource<AddToCartResponse>>()

    fun initProduct(product: Product) {
        productLiveData.value = product
    }


    fun getComments() {
        viewModelScope.launch {
            commentLiveData.value =
                commentRepository.comments(productLiveData.value!!.id)
        }
    }

    fun addComment(title: String, content: String) {
        viewModelScope.launch {
            commentRepository.add(productLiveData.value!!.id, title, content)
        }
    }

    fun addToCart() {
        viewModelScope.launch {
            cartResponseLiveData.value =
                cartRepository.addToCart(productLiveData.value!!.id)
        }
    }

    fun addFavorite(product: Product) {
        viewModelScope.launch {
            if (productRepository.addFavorite(product) != (-1).toLong()) {
                product.isFavorite = true
                initProduct(product)
                responseLiveData.value = R.string.add_favorite_product_successful
            } else
                responseLiveData.value = R.string.unknownError
        }
    }

    fun removeFavorite(product: Product) {
        viewModelScope.launch {
            if (productRepository.removeFavorite(product) != -1) {
                product.isFavorite = false
                initProduct(product)
                responseLiveData.value = R.string.remove_favorite_product_successful
            } else
                responseLiveData.value = R.string.unknownError
        }
    }

    fun isLogin() =
        TokenContainer.token != null


}