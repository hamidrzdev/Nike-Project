package com.hamiddev.nikeshop.data.repo.user

import com.hamiddev.nikeshop.data.model.MessageResponse
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.TokenResponse
import com.hamiddev.nikeshop.data.model.UserInfo

interface UserDataSource {
    suspend fun login(userName: String, password: String): Resource<TokenResponse>
    suspend fun signUp(userName: String, password: String): Resource<MessageResponse>
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)
    fun saveUsername(username: String)
    fun getUsername(): String
    fun signOut()
    fun saveInformation(userInfo: UserInfo)
    fun getInformation():UserInfo
}