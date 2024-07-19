package com.hamiddev.nikeshop.data.model

data class CartItem(
    val cart_item_id:Int,
    var count:Int,
    val product: Product,
    var changeCountProgressBarIsVisible:Boolean=false
)

