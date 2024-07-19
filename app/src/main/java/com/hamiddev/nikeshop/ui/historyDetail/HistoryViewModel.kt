package com.hamiddev.nikeshop.ui.historyDetail

import androidx.lifecycle.MutableLiveData
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.data.model.OrderHistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor():BaseViewModel() {
    val historyDetailLiceData = MutableLiveData<OrderHistoryItem>()

    fun initOrder(data : OrderHistoryItem){
        historyDetailLiceData.value = data
    }
}