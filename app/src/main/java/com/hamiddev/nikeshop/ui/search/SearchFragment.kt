package com.hamiddev.nikeshop.ui.search

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.FragmentSearchBinding
import com.hamiddev.nikeshop.ui.home.ProductListAdapter
import com.hamiddev.nikeshop.ui.home.VIEW_TYPE_SMALL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    ProductListAdapter.ProductEventListener {

    private val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var productAdapter: ProductListAdapter

    override fun viewCreated() {
        super.viewCreated()

        callNecessaryMethod()

        search()

//        clearSearchInputText()

        observerToProduct()
    }

    private fun callNecessaryMethod() {
        viewModel.getCount()
    }

    private fun search() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.searchQuery = p0.toString()

            }
        })

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (viewModel.searchQuery.isNotEmpty())
                    viewModel.search(viewModel.searchQuery)
                else
                    showSnack(getString(R.string.field_should_not_empty))
                hideKeyboard()
            }

            true
        }

    }

    private fun clearSearchInputText() {
        binding.clearText.setOnClickListener {
            if (binding.etSearch.length() > 0) {
                hideKeyboard()
                binding.etSearch.clearFocus()
                binding.etSearch.text?.clear()
                productAdapter.products = emptyList<Product>().toMutableList()
            }
        }
    }


    private fun observerToProduct() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> setProgressIndicator(true)
                Status.ERROR -> {
                    showErrorSnack(it.errorResponse?.errorMessage!!)
                    setProgressIndicator(false)
                }
                Status.SUCCESS -> {
                    setProgressIndicator(false)

                    emptyState(it.data!!.isEmpty())

                    binding.productList.layoutManager =
                        GridLayoutManager(requireContext(), 2)
                    productAdapter.viewType = VIEW_TYPE_SMALL
                    productAdapter.productEventListener = this
                    productAdapter.products = it.data.toMutableList()
                    binding.productList.adapter = productAdapter
                }
            }
        }


    }

    private fun emptyState(isEmpty: Boolean) {
        binding.emptyState.visibility =
            if (isEmpty) View.VISIBLE else View.GONE
    }

    override fun onProductClick(product: Product) {
        Navigation.findNavController(requireView())
            .navigate(SearchFragmentDirections.actionSearchFragmentToProductDetailFragment2(product))
    }

    override fun onFavoriteBtnClick(product: Product) {
        if (product.isFavorite)
            viewModel.removeFavorite(product)
        else
            viewModel.addFavorite(product)

        viewModel.responseLiveData.observe(viewLifecycleOwner) {
            showSnack(getString(it))
        }
    }


}