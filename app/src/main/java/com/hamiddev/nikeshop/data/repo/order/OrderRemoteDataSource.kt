package com.hamiddev.nikeshop.data.repo.order

import com.google.gson.JsonObject
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Checkout
import com.hamiddev.nikeshop.data.model.OrderHistoryItem
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.SubmitOrderResult
import com.hamiddev.nikeshop.service.ApiService
import timber.log.Timber
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    OrderDataSource {
    override suspend fun list(): Resource<List<OrderHistoryItem>> =
        safeApiCall {
            apiService.getOrderHistory()
        }

    override suspend fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Resource<SubmitOrderResult> {
        val d = JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("postal_code", postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address", address)
            addProperty("payment_method", paymentMethod)
        }
        return safeApiCall {
            apiService.submitOrder(d)
        }
    }


    override suspend fun checkout(orderId: Int): Resource<Checkout> =
        safeApiCall {
            apiService.checkout(orderId)
        }

}