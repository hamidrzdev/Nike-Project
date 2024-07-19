package com.hamiddev.nikeshop.ui.editProfile

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.common.navigateUp
import com.hamiddev.nikeshop.data.model.UserInfo
import com.hamiddev.nikeshop.databinding.EditProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<EditProfileFragmentBinding>(EditProfileFragmentBinding::inflate) {

    val viewModel: EditProfileViewModel by viewModels()

    override fun viewCreated() {
        super.viewCreated()

        configToolbar()

        saveData()

        fillEditTexts()
    }

    private fun configToolbar() {
        binding.backBtn.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun saveData() {
        binding.saveBtn.setOnClickListener {
            saveInformation()
            navigateUp()
        }
    }

    private fun saveInformation() {
        viewModel.saveInformation(
            UserInfo(
                binding.firstNameEt.text.toString(),
                binding.lastNameEt.text.toString(),
                binding.phoneNumberEt.text.toString(),
                binding.postalCodeEt.text.toString(),
                binding.addressEt.text.toString()
            )
        )
    }

    private fun fillEditTexts(){
        // fill edit texts With information received
        viewModel.getDefaultUserInfo().also {
            binding.firstNameEt.setText(it.firstName)
            binding.lastNameEt.setText(it.lastName)
            binding.phoneNumberEt.setText(it.phone)
            binding.postalCodeEt.setText(it.postCode)
            binding.addressEt.setText(it.address)
        }
    }
}