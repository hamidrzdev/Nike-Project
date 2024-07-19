package com.hamiddev.nikeshop.data.repo.user

import com.google.gson.JsonObject
import com.hamiddev.nikeshop.common.CLIENT_ID
import com.hamiddev.nikeshop.common.CLIENT_SECRET
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.MessageResponse
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.TokenResponse
import com.hamiddev.nikeshop.data.model.UserInfo
import com.hamiddev.nikeshop.service.ApiService
import timber.log.Timber
import javax.inject.Inject



class UserRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    UserDataSource {
    override suspend fun login(userName: String, password: String): Resource<TokenResponse> =
        safeApiCall {
            apiService.login(JsonObject().apply {
                addProperty("username", userName)
                addProperty("password", password)
                addProperty("grant_type", "password")
                addProperty("client_id", CLIENT_ID)
                addProperty("client_secret", CLIENT_SECRET)
            })
        }


    override suspend fun signUp(userName: String, password: String): Resource<MessageResponse> =
        safeApiCall {
            apiService.signUp(JsonObject().apply {
                addProperty("email", userName)
                addProperty("password", password)
            })
        }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun saveUsername(username: String) {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun saveInformation(userInfo: UserInfo) {
        TODO("Not yet implemented")
    }

    override fun getInformation(): UserInfo {
        TODO("Not yet implemented")
    }
}