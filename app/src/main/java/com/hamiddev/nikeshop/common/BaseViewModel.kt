package com.hamiddev.nikeshop.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.data.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL

open class BaseViewModel : ViewModel() {
    val progressLiveData = MutableLiveData<Boolean>()
    val responseLiveData = SingleLiveEvent<Int>()
}