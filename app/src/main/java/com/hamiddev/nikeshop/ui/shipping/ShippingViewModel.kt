package com.hamiddev.nikeshop.ui.shipping

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.PurchaseDetail
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.model.SubmitOrderResult
import com.hamiddev.nikeshop.data.repo.order.OrderRepository
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShippingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository
) : BaseViewModel() {

    val purchaseLiveData = MutableLiveData<PurchaseDetail>()

    val shippingLiveData = SingleLiveEvent<Resource<SubmitOrderResult>>()

    fun initPurchase(purchaseDetail: PurchaseDetail) {
        purchaseLiveData.value = purchaseDetail
    }

    val userInfo = liveData {
        emit(userRepository.getInformation())
    }

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ) {
        viewModelScope.launch {
            shippingLiveData.value =
                orderRepository.submit(
                    firstName,
                    lastName,
                    postalCode,
                    phoneNumber,
                    address,
                    paymentMethod
                )
        }
    }

}