package com.hamiddev.nikeshop.data.model

data class ErrorResponse(
    var errorMessage:String,
    var errorCode:Int? = null
)