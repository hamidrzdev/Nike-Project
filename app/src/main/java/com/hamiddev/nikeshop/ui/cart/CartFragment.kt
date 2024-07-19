package com.hamiddev.nikeshop.ui.cart

import android.content.SharedPreferences
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.*
import com.hamiddev.nikeshop.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate),
    CartItemAdapter.CartItemViewCallBack {

    private val viewModel: CartViewModel by viewModels()


    @Inject
    lateinit var cartItemAdapter: CartItemAdapter

    @Inject
    lateinit var sha:SharedPreferences

    override fun viewCreated() {
        super.viewCreated()
        viewModel.getData()
        viewModel.getCount()
        observer()
        emptyStateData()
        payBtn()

        val d = sha.getString("access_token","")
        Timber.i("timber global $d")

    }


    private fun payBtn() {
        binding.payBtn.setOnClickListener {
            navigate(CartFragmentDirections.actionCartFragmentToShippingFragment(viewModel.cartPurchaseLiveData.value!!))
        }
    }


    private fun observer() {
        viewModel.cartItemsLiveData.observe(viewLifecycleOwner) {
            Timber.i("timber cart fvfvfvfv")
            when (it!!.status) {
                Status.ERROR -> {
                    showErrorSnack(it.errorResponse!!.errorMessage)
                    binding.payBtn.visibility = View.VISIBLE
                    Timber.i("timber cart error -> ${it.errorResponse.errorMessage}")
                }
                Status.SUCCESS -> {
                    binding.payBtn.visibility =
                        if (it.data?.cart_items!!.isNotEmpty()) View.VISIBLE else View.INVISIBLE
                    Timber.i("timber cart success")
                    binding.cartItemsRv.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    cartItemAdapter.cartItemViewCallBack = this
                    cartItemAdapter.cartItems = it.data.cart_items.toMutableList()
                    binding.cartItemsRv.adapter = cartItemAdapter
                }
                Status.LOADING -> {
                    binding.payBtn.visibility = View.GONE
                    Timber.i("timber cart loading")
                }
            }
        }

        viewModel.cartPurchaseLiveData.observe(viewLifecycleOwner) {
            cartItemAdapter.purchaseDetail = it
            cartItemAdapter.notifyItemChanged(cartItemAdapter.cartItems.size)
        }

        viewModel.progressLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

    }


    private fun emptyStateData() {
        viewModel.emptyStateLiveData.observe(viewLifecycleOwner) {

            with(binding) {
                if (it.mustShow) {
                    emptyStateVisible()
                    emptyStateMessageTv.text = getString(it.messageResId)
                    authBtn.visibility =
                        if (it.mustShowCallToActionButton) View.VISIBLE else View.GONE
                    authBtn.setOnClickListener {
                        navigate(R.id.action_cartFragment_to_loginFragment)
                    }
                } else
                    emptyStateInVisible()
            }
        }
    }

    private fun emptyStateVisible() {
        binding.emptyState.visibility = View.VISIBLE
        binding.cartItemsRv.visibility = View.INVISIBLE
        binding.payBtn.visibility = View.GONE
    }

    private fun emptyStateInVisible() {
        binding.emptyState.visibility = View.INVISIBLE
        binding.cartItemsRv.visibility = View.VISIBLE
    }

    override fun onRemoveCartItem(itemCart: CartItem) {
        viewModel.removeItemFromCart(itemCart)
        viewModel.cartResponseLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.ERROR -> showErrorSnack(it.errorResponse!!.errorMessage)
                Status.SUCCESS -> viewModel.getData()
                Status.LOADING -> {
                    // nothing to do
                }
            }
        }
    }

    override fun onIncreaseCartItemButtonClick(itemCart: CartItem) {
        viewModel.increaseCartItemCount(itemCart)
        viewModel.cartResponseLiveData2.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.ERROR -> showErrorSnack(it.errorResponse!!.errorMessage)
                Status.SUCCESS -> {
                    viewModel.calculateAndPublishPurchaseDetail()
                    cartItemAdapter.increaseCount(itemCart)
                }
                Status.LOADING -> {
                    // nothing to do
                }
            }
        }
    }

    override fun onDecreaseCartItemButtonClick(itemCart: CartItem) {
        viewModel.decreaseCartItemCount(itemCart)
        viewModel.cartResponseLiveData2.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.ERROR -> showErrorSnack(it.errorResponse!!.errorMessage)
                Status.SUCCESS -> {
                    viewModel.calculateAndPublishPurchaseDetail()
                    cartItemAdapter.decreaseCount(itemCart)
                }
                Status.LOADING -> {
                    // nothing to do
                }
            }
        }
    }

    override fun onProductImageClick(itemCart: CartItem) {
        navigate(
            CartFragmentDirections.actionCartFragmentToProductDetailFragment3(
                itemCart.product
            )
        )
    }
}