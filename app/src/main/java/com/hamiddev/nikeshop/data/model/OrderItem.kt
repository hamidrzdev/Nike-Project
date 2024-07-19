package com.hamiddev.nikeshop.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderItem(
    val count:Int,
    val discount:Int,
    val id :Int,
    val order_id:Int,
    val price:Int,
    val product:Product,
    val product_id:Int
):Parcelable