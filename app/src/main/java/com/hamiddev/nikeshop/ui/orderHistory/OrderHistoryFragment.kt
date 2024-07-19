package com.hamiddev.nikeshop.ui.orderHistory

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.common.navigate
import com.hamiddev.nikeshop.common.showErrorSnack
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.OrderHistoryFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderHistoryFragment :
    BaseFragment<OrderHistoryFragmentBinding>(OrderHistoryFragmentBinding::inflate) {

    val viewModel: OrderHistoryViewModel by viewModels()

    @Inject
    lateinit var orderHistoryItemAdapter: OrderHistoryItemAdapter

    override fun viewCreated() {
        super.viewCreated()

        toolbarConfig()

        observeToOrder()
    }

    private fun toolbarConfig() {
        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            it.findNavController().navigateUp()
        }
    }

    private fun observeToOrder() {
        viewModel.orderLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> setProgressIndicator(true)
                Status.ERROR -> {
                    showErrorSnack(it.errorResponse?.errorMessage!!)
                    setProgressIndicator(false)
                }
                Status.SUCCESS -> {
                    if (it.data!!.isEmpty())
                        showEmpty()
                    else
                        hideEmpty()

                    setProgressIndicator(false)
                    binding.productList.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    orderHistoryItemAdapter.productList = it.data.toMutableList()
                    binding.productList.adapter = orderHistoryItemAdapter

                    orderHistoryItemAdapter.onOrderClicked = { orderHistoryItem ->
                        navigate(
                            OrderHistoryFragmentDirections.actionOrderHistoryFragmentToOrderHistoryDetailFragment(
                                orderHistoryItem
                            )
                        )
                    }
                }
            }
        }
    }

    private fun showEmpty() {
        binding.emptyState.visibility = View.VISIBLE
    }

    private fun hideEmpty() {
        binding.emptyState.visibility = View.GONE
    }

}