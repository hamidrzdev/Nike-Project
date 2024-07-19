package com.hamiddev.nikeshop.data.repo.cart

import com.google.gson.JsonObject
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.*
import com.hamiddev.nikeshop.service.ApiService
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    CartDataSource {
    override suspend fun addToCart(productId: Int): Resource<AddToCartResponse> =
        safeApiCall {
            apiService.addToCart(
                JsonObject().apply {
                    addProperty("product_id", productId)
                }
            )
        }

    override suspend fun getCart(): Resource<CartResponse> =
        safeApiCall {
            apiService.getCart()
        }

    override suspend fun remove(cartItemId: Int): Resource<MessageResponse> =
        safeApiCall {
            apiService.removeItemFromCart(
                JsonObject().apply {
                    addProperty("cart_item_id", cartItemId)
                }
            )
        }

    override suspend fun changeCount(cartItemId: Int, count: Int): Resource<AddToCartResponse> =
        safeApiCall {
            apiService.changeCount(
                JsonObject().apply {
                    addProperty("cart_item_id", cartItemId)
                    addProperty("count", count)
                }
            )
        }

    override suspend fun getCartItemsCount(): Resource<CartItemCount> =
        safeApiCall {
            apiService.getCartItemCount()
        }
}