package com.hamiddev.nikeshop.data.repo.user

import com.hamiddev.nikeshop.data.model.MessageResponse
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.TokenResponse
import com.hamiddev.nikeshop.data.model.UserInfo

interface UserRepository {
    suspend fun login(userName: String, password: String): Resource<TokenResponse>
    suspend fun signUp(userName: String, password: String): Resource<MessageResponse>
    fun loadToken()
    fun getUserName(): String
    fun signOut()
    fun getInformation(): UserInfo
    fun saveInformation(userInfo: UserInfo)
    fun onSuccessFulLogin(tokenResponse: TokenResponse?, userName: String?)
}