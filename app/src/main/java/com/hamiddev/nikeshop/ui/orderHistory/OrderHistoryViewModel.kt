package com.hamiddev.nikeshop.ui.orderHistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.OrderHistoryItem
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.repo.order.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(private val orderRepository: OrderRepository) :
    BaseViewModel() {

    val orderLiveData = MutableLiveData<Resource<List<OrderHistoryItem>>>()

    init {
        orderHistory()
    }

    fun orderHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            orderLiveData.postValue(orderRepository.list())
        }
    }
}