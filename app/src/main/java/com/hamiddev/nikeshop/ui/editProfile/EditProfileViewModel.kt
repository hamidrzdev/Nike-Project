package com.hamiddev.nikeshop.ui.editProfile

import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.data.model.UserInfo
import com.hamiddev.nikeshop.data.repo.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    fun saveInformation(userInfo: UserInfo) {
        userRepository.saveInformation(userInfo)
    }

    fun getDefaultUserInfo() =
        userRepository.getInformation()

}