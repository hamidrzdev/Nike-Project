package com.hamiddev.nikeshop.data.repo.order

import com.hamiddev.nikeshop.data.model.Checkout
import com.hamiddev.nikeshop.data.model.OrderHistoryItem
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.SubmitOrderResult
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderRemoteDataSource: OrderDataSource) :
    OrderRepository {

    override suspend fun list(): Resource<List<OrderHistoryItem>> =
        orderRemoteDataSource.list()

    override suspend fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Resource<SubmitOrderResult> =
        orderRemoteDataSource.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )

    override suspend fun checkout(orderId: Int): Resource<Checkout> =
        orderRemoteDataSource.checkout(orderId)

}