package com.hamiddev.nikeshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderHistoryItem(
    val address: String,
    val date: String,
    val first_name: String,
    val id: Int,
    val last_name: String,
    val order_items: List<OrderItem>,
    val payable: Int,
    val payment_method: String,
    val payment_status: String,
    val phone: String,
    val postal_code: String,
    val shipping_cost: Int,
    val total: Int,
    val user_id: Int
):Parcelable