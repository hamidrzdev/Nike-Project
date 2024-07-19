package com.hamiddev.nikeshop.data.model

data class Resource<out T>(val status: Status, val data: T?, val errorResponse: ErrorResponse?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(errorResponse: ErrorResponse?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, errorResponse)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }
}