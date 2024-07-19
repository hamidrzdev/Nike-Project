package com.hamiddev.nikeshop.ui.signup

import android.util.Patterns
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.SignupFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignupFragmentBinding>(SignupFragmentBinding::inflate) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun viewCreated() {
        super.viewCreated()

        configToolbar()

        viewConfig()

        signUp()

        signUpHelper()

        observeToSignUp()

        binding.btnSignUp.isClickable = false
    }

    private fun signUpHelper() {
        binding.btnLogInHelper.setOnClickListener {
            navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun signUp() {

        binding.btnSignUp.setOnClickListener {
            viewModel.signUp(binding.etEmail.text.toString(), binding.etPassword.text.toString())

        }
    }

    private fun observeToSignUp() {
        viewModel.signUnLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.ERROR -> {
                   showErrorSnack(it.errorResponse?.errorMessage!!)
                    hideProgress()
                }
                Status.SUCCESS -> {
                    hideProgress()
                    navigateUp()
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
                btnSignUp.strokeWidth = 0
                btnSignUp.isClickable = true
                btnSignUp.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue))
                btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            } else {
                btnSignUp.strokeWidth = 3
                btnSignUp.isClickable = false
                btnSignUp.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray))
                btnSignUp.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
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

    private fun configToolbar() {
        binding.backBtn.setOnClickListener {
            navigateUp()
        }
    }

    private fun showProgress() {
        binding.btnSignUp.text = null
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.btnSignUp.text = getString(R.string.signUp)
        binding.progress.visibility = View.INVISIBLE
    }

}