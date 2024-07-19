package com.hamiddev.nikeshop.common

import com.google.gson.JsonObject
import com.hamiddev.nikeshop.data.model.TokenResponse
import com.hamiddev.nikeshop.data.repo.user.TokenContainer
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import com.hamiddev.nikeshop.service.ApiService
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class NikeAuthenticator : Authenticator {

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var userRepository:UserRepository

    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.i("timber nike authenticator")
        Timber.i("timber call ${response.body}")
        if (!TokenContainer.token.isNullOrEmpty() &&
            !TokenContainer.refreshToken.isNullOrEmpty() &&
            !response.request.url.pathSegments.last().equals("token", false)
        ) {
            try {

                val token = getNewToken()
                if (token.isEmpty())
                    return null

                return response.request.newBuilder().header("Authorization", token).build()

            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        return null
    }

    private fun getNewToken(): String {
        val request: retrofit2.Response<TokenResponse> =
            apiService.refreshToken(JsonObject().apply {
                addProperty("grant_type", "refresh_token")
                addProperty("refresh_token", TokenContainer.refreshToken)
                addProperty("client_id", CLIENT_ID)
                addProperty("client_secret", CLIENT_SECRET)
            }).execute()

        request.body()?.let {
            userRepository.onSuccessFulLogin(it, null)
            return "Bearer ${it.access_token}"
        }

        return ""
    }

}