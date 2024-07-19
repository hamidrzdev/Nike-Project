package com.hamiddev.nikeshop.data.repo.user

object TokenContainer {
    var token: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun update(token: String?, refreshToken: String?) {
//        Timber.i("Access Token  -> ${token!!.substring(0,10)} , Refresh Token ${refreshToken!!.substring(0,10)}")
        TokenContainer.token = token
        TokenContainer.refreshToken = refreshToken
    }

    fun clear() {
        token = null
        refreshToken = null
    }
}