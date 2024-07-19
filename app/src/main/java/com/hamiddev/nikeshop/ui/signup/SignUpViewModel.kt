package com.hamiddev.nikeshop.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.MessageResponse
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    val signUnLiveData = MutableLiveData<Resource<MessageResponse>>()

    fun signUp(userName: String, password: String) {
        viewModelScope.launch {

            signUnLiveData.value = Resource.loading(null)

            signUnLiveData.value =
                userRepository.signUp(userName, password)
        }
    }
}