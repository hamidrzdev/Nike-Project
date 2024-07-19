package com.hamiddev.nikeshop.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseFragment
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.SORT_LATEST
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.FragmentListBinding
import com.hamiddev.nikeshop.ui.home.ProductListAdapter
import com.hamiddev.nikeshop.ui.home.VIEW_TYPE_LARGE
import com.hamiddev.nikeshop.ui.home.VIEW_TYPE_SMALL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentListBinding>(FragmentListBinding::inflate),
    ProductListAdapter.ProductEventListener {

    private val viewModel: ProductListViewModel by viewModels()

    @Inject
    lateinit var productListenerAdapter: ProductListAdapter

    private lateinit var gridLayoutManager: GridLayoutManager

    var sort = SORT_LATEST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sort = ProductListFragmentArgs.fromBundle(requireArguments()).sort
        viewModel.sort = sort
    }

    override fun viewCreated() {
        super.viewCreated()

        callNecessaryMethod()

        initList()

        observer()

        toolbarConfig()

        viewTypeChangeConfig()

        sortList()
    }

    private fun callNecessaryMethod() {
        viewModel.getProducts()
    }

    private fun sortList() {
        binding.sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.sort))
                .setSingleChoiceItems(
                    R.array.sortTitlesArray, viewModel.sort!!
                ) { dialog, selectedSortIndex ->
                    viewModel.sort = selectedSortIndex
                    viewModel.onSelectedSortChangedByUser(selectedSortIndex)
                    dialog.dismiss()
                }

            dialog.show()
        }
    }

    private fun initList() {
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        with(binding) {
            productRv.layoutManager = gridLayoutManager
            productRv.adapter = productListenerAdapter
            productListenerAdapter.viewType = VIEW_TYPE_SMALL

            productListenerAdapter.productEventListener = this@ProductListFragment
        }
    }


    private fun viewTypeChangeConfig() {
        binding.viewTypeChangerBtn.setOnClickListener {
            if (productListenerAdapter.viewType == VIEW_TYPE_SMALL) {
                binding.viewTypeChangerBtn.setImageResource(R.drawable.view_type_large)
                productListenerAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListenerAdapter.notifyDataSetChanged()
            } else {
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
                productListenerAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListenerAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun toolbarConfig() {
        binding.toolbarView.onBackButtonClickListener = View.OnClickListener {
            Navigation.findNavController(requireView())
                .navigateUp()
        }
    }

    private fun observer() {
        viewModel.selectedSortTitleLiveData.observe(viewLifecycleOwner) {
            binding.selectedSortTitleTv.text = getString(it)
        }

        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> setProgressIndicator(true)
                Status.ERROR -> {
                    setProgressIndicator(false)
//                    Timber.i("timber error -> ${it.throwable?.message}")
                }
                Status.SUCCESS -> {
                    setProgressIndicator(false)
                    productListenerAdapter.products = it.data!!.toMutableList()
                }
            }
        }

    }

    override fun onProductClick(product: Product) {
        Navigation.findNavController(requireView()).navigate(
            ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment2(product)
        )
    }

    override fun onFavoriteBtnClick(product: Product) {
        if (product.isFavorite)
            viewModel.removeFavorite(product)
        else
            viewModel.addFavorite(product)
    }

}