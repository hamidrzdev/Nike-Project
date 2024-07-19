package com.hamiddev.nikeshop.ui.shipping

import android.view.View
import androidx.fragment.app.viewModels
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.FragmentShippingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingFragment : BaseFragment<FragmentShippingBinding>(FragmentShippingBinding::inflate) {

    private val viewModel: ShippingViewModel by viewModels()

    override fun viewCreated() {
        super.viewCreated()

        callNecessaryMethod()

        backBtn()

        observeToPurchase()

        observeToShipping()

        fillEditText()

        submit()
    }

    private fun callNecessaryMethod() {
        viewModel.initPurchase(ShippingFragmentArgs.fromBundle(requireArguments()).purchase)
    }

    private fun backBtn() {
        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            navigateUp()
        }
    }

    private fun fillEditText() {
        viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.firstNameEt.setText(it.firstName)
            binding.lastNameEt.setText(it.lastName)
            binding.phoneNumberEt.setText(it.phone)
            binding.postalCodeEt.setText(it.postCode)
            binding.addressEt.setText(it.address)
        }
    }

    private fun observeToPurchase() {
        viewModel.purchaseLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                totalPriceTv.text = convertEnToFa(formatPrice(it.total_price).toString())
                shippingCostTv.text = convertEnToFa(formatPrice(it.shipping_cost).toString())
                payablePriceTv.text = convertEnToFa(formatPrice(it.payable_price).toString())
            }
        }
    }

    private fun observeToShipping() {
        viewModel.shippingLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    //  nothing to do
                }
                Status.ERROR -> showErrorSnack(it.errorResponse?.errorMessage!!)
                Status.SUCCESS -> {
                    if (it.data!!.bank_gateway_url.isNotEmpty())
                        openUrlInCustomTab(requireContext(), it.data.bank_gateway_url)
                    else
                        navigate(
                            ShippingFragmentDirections.actionShippingFragmentToCheckOutFragment2(
                                it.data.order_id
                            )
                        )
                }
            }
        }
    }

    private fun submit() {
        val onClick = View.OnClickListener {
            val firstName = binding.firstNameEt.text.toString()
            val lastName = binding.lastNameEt.text.toString()
            val post = binding.postalCodeEt.text.toString()
            val phone = binding.phoneNumberEt.text.toString()
            val address = binding.addressEt.text.toString()
            val paymentMethod =
                if (it.id == R.id.codBtn) PAYMENT_METHOD_COD else PAYMENT_METHOD_ONLINE

            viewModel.submit(firstName, lastName, post, phone, address, paymentMethod)
        }
        binding.onlinePaymentBtn.setOnClickListener(onClick)
        binding.codBtn.setOnClickListener(onClick)
    }
}