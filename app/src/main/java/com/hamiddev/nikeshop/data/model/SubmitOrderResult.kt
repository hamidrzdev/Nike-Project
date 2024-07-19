package com.hamiddev.nikeshop.data.model

import com.google.gson.annotations.SerializedName

data class SubmitOrderResult(
    val bank_gateway_url: String,
    val order_id: Int
)