package com.hamiddev.nikeshop.data.repo.order

import com.hamiddev.nikeshop.data.model.Checkout
import com.hamiddev.nikeshop.data.model.OrderHistoryItem
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.SubmitOrderResult

interface OrderDataSource {
    suspend fun list():Resource<List<OrderHistoryItem>>

    suspend fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Resource<SubmitOrderResult>

    suspend fun checkout(orderId: Int): Resource<Checkout>
}