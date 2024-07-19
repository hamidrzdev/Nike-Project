package com.hamiddev.nikeshop.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.TokenResponse
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) :
    BaseViewModel() {

    val loginLiveData = MutableLiveData<Resource<TokenResponse>>()

    fun login(userName: String, password: String) {
        viewModelScope.launch {

            loginLiveData.value = Resource.loading(null)

            loginLiveData.value =
                userRepository.login(userName, password)

            cartRepository.getCartItemsCount().data?.let {
                EventBus.getDefault().postSticky(it)
            }
        }
    }
}