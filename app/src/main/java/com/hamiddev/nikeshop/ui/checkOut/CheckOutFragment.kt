package com.hamiddev.nikeshop.ui.checkOut

import android.view.View
import androidx.fragment.app.viewModels
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.FragmentCheckOutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(FragmentCheckOutBinding::inflate) {

    private val viewModel: CheckOutViewModel by viewModels()

    private lateinit var args: CheckOutFragmentArgs

    override fun viewCreated() {
        super.viewCreated()

        provideArgs()

        callNecessaryMethodFromViewModel()

        handleButtonClick()

        observeToCheckOut()
    }

    private fun provideArgs() {
        args = CheckOutFragmentArgs.fromBundle(requireArguments())
    }

    private fun callNecessaryMethodFromViewModel(){
        viewModel.checkOut(args.order)
    }

    private fun handleButtonClick() {
        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            navigateUp()
        }

        binding.orderHistoryBtn.setOnClickListener {
            navigate(R.id.action_checkOutFragment_to_orderHistoryFragment2)
        }

        binding.returnHomeBtn.setOnClickListener {
            navigateUp()
        }

    }

    private fun observeToCheckOut() {
        viewModel.checkOutLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> setProgressIndicator(true)
                Status.ERROR -> {
                    setProgressIndicator(false)
                    showErrorSnack(it.errorResponse?.errorMessage!!)
                }
                Status.SUCCESS -> {
                    setProgressIndicator(false)
                    with(binding) {
                        orderPriceTv.text =
                            convertEnToFa(formatPrice(it.data?.payable_price!!).toString())
                        orderStatusTv.text = it.data.payment_status
                        purchaseStatusTv.text =
                            if (it.data.purchase_success) getString(R.string.successful_buy) else getString(R.string.failed_buy)
                    }
                }
            }
        }

    }
}