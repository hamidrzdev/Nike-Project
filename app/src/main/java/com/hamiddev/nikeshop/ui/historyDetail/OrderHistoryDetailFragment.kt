package com.hamiddev.nikeshop.ui.historyDetail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.common.convertEnToFa
import com.hamiddev.nikeshop.common.formatPrice
import com.hamiddev.nikeshop.databinding.OrderHistoryDetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderHistoryDetailFragment :
    BaseFragment<OrderHistoryDetailFragmentBinding>(OrderHistoryDetailFragmentBinding::inflate) {

    lateinit var bundle: OrderHistoryDetailFragmentArgs
    val viewModel: HistoryViewModel by viewModels()

    @Inject
    lateinit var historyDetailImageAdapter: HistoryDetailImageAdapter

    private lateinit var args :OrderHistoryDetailFragmentArgs

    override fun viewCreated() {
        super.viewCreated()

        provideArgs()

        callNecessaryMethodFromViewModel()

        observe()
        toolbarConfig()
    }

    private fun provideArgs(){
        args = OrderHistoryDetailFragmentArgs.fromBundle(requireArguments())
    }

    private fun callNecessaryMethodFromViewModel(){
        viewModel.initOrder(args.order)
    }

    private fun toolbarConfig() {
        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            it.findNavController().navigateUp()
        }
    }


    private fun observe() {
        viewModel.historyDetailLiceData.observe(viewLifecycleOwner) {
            with(binding) {
                tvFirstName.text = it.first_name
                tvLastName.text = it.last_name
                tvPhone.text = convertEnToFa(it.phone)
                tvAddress.text = it.address
                tvPostCode.text = convertEnToFa(it.postal_code)
                tvPaymentWay.text = convertEnToFa(it.payment_method)
                tvPaymentStatus.text = convertEnToFa(it.payment_status)
                tvFullPayment.text = convertEnToFa(formatPrice(it.payable).toString())

                productRv.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

                historyDetailImageAdapter.images = it.order_items.map {
                    it.product.image
                }.toMutableList()

                productRv.adapter = historyDetailImageAdapter

            }
        }
    }
}