package com.hamiddev.nikeshop.ui.login

import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun viewCreated() {
        super.viewCreated()


        toolbar()
        viewConfig()
        signIn()
        logInHelper()
        observe()

        binding.btnLogIn.isClickable = false
    }

    private fun logInHelper() {
        binding.btnSignUpHelper.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun signIn() {
        binding.btnLogIn.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun observe() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> showProgress()
                Status.ERROR -> {
                    hideProgress()
                    if (it.errorResponse?.errorCode == Network.CONNECTION_NOT_AUTHORIZED_CODE)
                        showErrorSnack(getString(R.string.password_or_user_not_correct))
                }
                Status.SUCCESS -> {
                    hideProgress()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun viewConfig() {
        binding.etPassword.changeListener {
            with(binding) {
                if (it.isNotEmpty()) {
                    ivPassword.visibility = View.VISIBLE
                    if (isValidPassword(it))
                        showDoneIcon(ivPassword)
                    else
                        showErrorIcon(ivPassword)
                } else
                    ivPassword.visibility = View.INVISIBLE
            }
            checkSubmit()
        }

        binding.etEmail.changeListener {
            with(binding) {
                if (it.isNotEmpty()) {
                    ivEmail.visibility = View.VISIBLE
                    if (isValidEmail(it))
                        showDoneIcon(ivEmail)
                    else
                        showErrorIcon(ivEmail)
                } else
                    ivEmail.visibility = View.INVISIBLE
            }
            checkSubmit()
        }
    }

    private fun checkSubmit() {
        with(binding) {
            if (isValidEmail(etEmail.text.toString()) && isValidPassword(etPassword.text.toString())) {
                btnLogIn.strokeWidth = 0
                btnLogIn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnLogIn.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
                btnLogIn.isClickable = true
            } else {
                btnLogIn.strokeWidth = 3
                btnLogIn.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
                btnLogIn.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnLogIn.isClickable = false
            }
        }
    }

    private fun showDoneIcon(view: ImageView) {
        view.setImageResource(R.drawable.ic_round_done_24)
    }

    private fun showErrorIcon(view: ImageView) {
        view.setImageResource(R.drawable.ic_baseline_error_outline_24)
    }

    private fun isValidEmail(email: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String) =
        password.length >= 8

    private fun toolbar() {
        binding.backBtn.setOnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.btnLogIn.text = ""
    }

    private fun hideProgress() {
        binding.progress.visibility = View.INVISIBLE
        binding.btnLogIn.text = getString(R.string.loginTitle)
    }
}