package com.hamiddev.nikeshop.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.data.model.CartItemCount
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.user.TokenContainer
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProFileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) :
    BaseViewModel() {

    val userNameLiveData = MutableLiveData<String>()

    fun isLogin() =
        TokenContainer.token != null


    fun logOut() {
        userRepository.signOut()
        isLogin()
        getUserName()
        EventBus.getDefault().postSticky(CartItemCount(0))
    }

    fun getUserName() {
        userNameLiveData.value = userRepository.getUserName()
    }

    fun getCount() {
        viewModelScope.launch {
            if (TokenContainer.token != null) {
                cartRepository.getCartItemsCount().data?.let {
                    EventBus.getDefault().postSticky(it)
                }
            }
        }
    }
}