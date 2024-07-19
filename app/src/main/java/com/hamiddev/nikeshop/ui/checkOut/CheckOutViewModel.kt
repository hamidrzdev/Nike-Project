package com.hamiddev.nikeshop.ui.checkOut

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Checkout
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.repo.order.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    BaseViewModel() {

    val checkOutLiveData = MutableLiveData<Resource<Checkout>>()

    fun checkOut(orderId: Int) {
        viewModelScope.launch {
            checkOutLiveData.value =
                orderRepository.checkout(orderId)
        }
    }

}