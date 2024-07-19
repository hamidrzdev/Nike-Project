package com.hamiddev.nikeshop.ui.main

import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.user.TokenContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val cartRepository: CartRepository) :
    BaseViewModel() {

    fun getCount() {
        viewModelScope.launch {
            if (TokenContainer.token != null){
                val count = cartRepository.getCartItemsCount().data
                Timber.i("timber main viewModel ${count?.count}")
                count?.let {
                    EventBus.getDefault().postSticky(it)
                }
            }
        }
    }
}