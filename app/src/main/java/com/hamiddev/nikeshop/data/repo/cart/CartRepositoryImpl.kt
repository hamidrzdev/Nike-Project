package com.hamiddev.nikeshop.data.repo.cart

import com.hamiddev.nikeshop.data.model.*
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartRemoteDataSource: CartDataSource) :
    CartRepository {
    override suspend fun addToCart(productId: Int): Resource<AddToCartResponse> =
        cartRemoteDataSource.addToCart(productId)

    override suspend fun getCart(): Resource<CartResponse> =
        cartRemoteDataSource.getCart()

    override suspend fun remove(cartItemId: Int): Resource<MessageResponse> =
        cartRemoteDataSource.remove(cartItemId)

    override suspend fun changeCount(cartItemId: Int, count: Int): Resource<AddToCartResponse> =
        cartRemoteDataSource.changeCount(cartItemId, count)

    override suspend fun getCartItemsCount(): Resource<CartItemCount> =
        cartRemoteDataSource.getCartItemsCount()

}