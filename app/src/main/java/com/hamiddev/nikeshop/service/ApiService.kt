package com.hamiddev.nikeshop.service

import com.google.gson.JsonObject
import com.hamiddev.nikeshop.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("banner/slider")
    suspend fun banners(): List<Banner>

    @GET("product/list")
    suspend fun getProducts(@Query("sort") sort: String): List<Product>

    @GET("comment/list")
    suspend fun getComments(@Query("product_id") productId: Int): List<Comment>

    @POST("comment/add")
    suspend fun addComment(@Body jsonObject: JsonObject): Comment

    @GET("product/search")
    suspend fun searchInProducts(@Query("q") productTitle: String): List<Product>

    @POST("auth/token")
    suspend fun login(@Body jsonObject: JsonObject): TokenResponse

    @POST("user/register")
    suspend fun signUp(@Body jsonObject: JsonObject): MessageResponse

    @GET("order/list")
    suspend fun getOrderHistory(): List<OrderHistoryItem>

    @POST("cart/add")
    suspend fun addToCart(@Body jsonObject: JsonObject): AddToCartResponse

    @GET("cart/list")
    suspend fun getCart(): CartResponse

    @POST("cart/remove")
    suspend fun removeItemFromCart(@Body jsonObject: JsonObject): MessageResponse

    @POST("cart/changeCount")
    suspend fun changeCount(@Body jsonObject: JsonObject): AddToCartResponse

    @GET("cart/count")
    suspend fun getCartItemCount(): CartItemCount

    @POST("order/submit")
    suspend fun submitOrder(@Body jsonObject: JsonObject): SubmitOrderResult

    @GET("order/checkout")
    suspend fun checkout(@Query("order_id") orderId: Int): Checkout

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>
}