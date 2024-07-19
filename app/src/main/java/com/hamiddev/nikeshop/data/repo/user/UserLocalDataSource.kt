package com.hamiddev.nikeshop.data.repo.user

import android.content.SharedPreferences
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.MessageResponse
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.TokenResponse
import com.hamiddev.nikeshop.data.model.UserInfo
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) :
    UserDataSource {
    override suspend fun login(userName: String, password: String): Resource<TokenResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(userName: String, password: String): Resource<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString("access_token", null),
            sharedPreferences.getString("refresh_token", null)
        )

    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("access_token", token)
            putString("refresh_token", refreshToken)
        }.apply()
    }

    override fun saveUsername(username: String) {
        sharedPreferences.edit().apply {
            putString("username", username)
        }.apply()
    }

    override fun getUsername(): String =
        sharedPreferences.getString("username", "") ?: ""

    override fun signOut() {
        sharedPreferences.edit().apply {
            remove("access_token")
            remove("refresh_token")
            remove("username")
        }.apply()
    }

    override fun saveInformation(userInfo: UserInfo) {
        sharedPreferences.edit().apply {
            putString(FIRST_NAME, userInfo.firstName)
            putString(LAST_NAME, userInfo.lastName)
            putString(PHONE, userInfo.phone)
            putString(POST_CODE, userInfo.postCode)
            putString(ADDRESS, userInfo.address)
        }.apply()
    }

    override fun getInformation(): UserInfo {
        val firstName = sharedPreferences.getString(FIRST_NAME, "") ?: ""
        val lastName = sharedPreferences.getString(LAST_NAME, "") ?: ""
        val phone = sharedPreferences.getString(PHONE, "") ?: ""
        val postCode = sharedPreferences.getString(POST_CODE, "") ?: ""
        val address = sharedPreferences.getString(ADDRESS, "") ?: ""

        return UserInfo(firstName, lastName, phone, postCode, address)
    }
}