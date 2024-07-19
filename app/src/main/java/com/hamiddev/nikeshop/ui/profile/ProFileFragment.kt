package com.hamiddev.nikeshop.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.common.navigate
import com.hamiddev.nikeshop.common.showSnack
import com.hamiddev.nikeshop.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProFileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProFileViewModel by viewModels()

    override fun viewCreated() {
        super.viewCreated()

        callNecessaryMethod()

        handleClicked()

        checkAuth()

        observeToUserName()
    }

    private fun callNecessaryMethod() {
        viewModel.isLogin()
        viewModel.getUserName()
        viewModel.getCount()
    }

    private fun handleClicked() {
        binding.favoriteProductsBtn.setOnClickListener {
            navigate(R.id.action_proFileFragment_to_favoriteFragment)
        }

        binding.editUserBtn.setOnClickListener {
            navigate(R.id.action_proFileFragment_to_editProfileFragment)
        }

        binding.orderHistoryBtn.setOnClickListener {
            if (viewModel.isLogin())
                navigate(R.id.action_proFileFragment_to_orderHistoryFragment)
            else
                showSnack(getString(R.string.should_login))
        }
    }

    private fun observeToUserName() {
        viewModel.userNameLiveData.observe(viewLifecycleOwner) {
            Timber.i("timber name $it")
            binding.usernameTv.text = if (it.isNotEmpty()) it else getString(R.string.guest_user)
        }
    }

    private fun checkAuth() {
        if (viewModel.isLogin()) {
            binding.loginBtn.text = getString(R.string.signOut)
            binding.loginBtn.setOnClickListener {
                viewModel.logOut()
                checkAuth()
                showSnack(getString(R.string.you_are_out))
            }
        } else {
            binding.loginBtn.text = getString(R.string.signIn)
            binding.loginBtn.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_proFileFragment_to_loginFragment)
                viewModel.isLogin()
            }
        }
    }


}