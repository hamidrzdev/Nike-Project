package com.hamiddev.nikeshop.data.repo.user

import com.hamiddev.nikeshop.data.model.MessageResponse
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.TokenResponse
import com.hamiddev.nikeshop.data.model.UserInfo
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserDataSource,
    private val userLocalDataSource: UserDataSource
) : UserRepository {
    override suspend fun login(userName: String, password: String): Resource<TokenResponse> {
        val loginJob = userRemoteDataSource.login(userName, password)
        onSuccessFulLogin(loginJob.data, userName)
        return loginJob
    }

    override suspend fun signUp(userName: String, password: String): Resource<MessageResponse> {
        val signupJob = userRemoteDataSource.signUp(userName, password)

        val loginJob = userRemoteDataSource.login(userName, password)
        onSuccessFulLogin(loginJob.data, userName)
        return signupJob
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String =
        userLocalDataSource.getUsername()

    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.clear()
    }

    override fun onSuccessFulLogin(tokenResponse: TokenResponse?, userName: String?) {
        tokenResponse?.let {
            TokenContainer.update(it.access_token, it.refresh_token)
            userLocalDataSource.saveToken(it.access_token, it.refresh_token)
        }
        userName?.let {
            userLocalDataSource.saveUsername(it)
        }
    }

    override fun saveInformation(userInfo: UserInfo) =
        userLocalDataSource.saveInformation(userInfo)

    override fun getInformation(): UserInfo =
        userLocalDataSource.getInformation()

}