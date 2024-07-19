package com.hamiddev.nikeshop.data.repo.cart

import com.hamiddev.nikeshop.data.model.*

interface CartDataSource {
    suspend fun addToCart(productId:Int): Resource<AddToCartResponse>
    suspend fun getCart(): Resource<CartResponse>
    suspend fun remove(cartItemId:Int): Resource<MessageResponse>
    suspend fun changeCount(cartItemId: Int,count:Int):Resource<AddToCartResponse>
    suspend fun getCartItemsCount(): Resource<CartItemCount>
}