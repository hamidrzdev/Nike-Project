package com.hamiddev.nikeshop.ui.favorite

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.common.navigate
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.FavoriteFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteFragmentBinding>(FavoriteFragmentBinding::inflate) {
    val viewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var favoriteProductAdapter: FavoriteProductAdapter


    override fun viewCreated() {
        super.viewCreated()

        configToolbar()

        observeToProductSize()

        observeToProduct()
    }

    private fun configToolbar() {
        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            Navigation.findNavController(it).navigateUp()
        }
    }

    private fun observeToProductSize() {
        // handle empty state
        viewModel.productsSize.observe(viewLifecycleOwner) {
            emptyState(it == 0)
        }
    }

    private fun observeToProduct() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    setProgressIndicator(true)
                }
                Status.ERROR -> setProgressIndicator(false)
                Status.SUCCESS -> {
                    setProgressIndicator(false)
                    viewModel.updateProductSize(it.data?.size!!)
                    binding.productList.layoutManager =
                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                    favoriteProductAdapter.products = it.data.toMutableList()
                    binding.productList.addItemDecoration(DividerItemDecoration(requireContext()))
                    binding.productList.adapter = favoriteProductAdapter


                    favoriteProductAdapter.onClickProduct = {product ->
                        navigate(
                            FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailFragment3(
                                product
                            )
                        )
                    }

                    favoriteProductAdapter.onLongClickProduct = {product ->
                        viewModel.removeFavorite(product)
                    }
                }
            }
        }
    }

    private fun emptyState(isEmpty: Boolean) {
        binding.emptyState.visibility =
            if (isEmpty) View.VISIBLE else View.GONE
    }
}