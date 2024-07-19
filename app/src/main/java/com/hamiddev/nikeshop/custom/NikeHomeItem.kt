package com.hamiddev.nikeshop.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.service.ImageLoadingService
import com.hamiddev.nikeshop.ui.home.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NikeHomeItem(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    @Inject
    lateinit var productListAdapter: ProductListAdapter

    var onProductClicked: ((product: Product) -> Unit)? = null
    var onFavoriteClicked: ((product: Product) -> Unit)? = null

    var onShowMoreClicked: (() -> Unit)? = null

    private var productRv: RecyclerView? = null


    init {
        inflate(context, R.layout.home_item, this)

        val showMore = findViewById<MaterialButton>(R.id.show_more_btn)
        val label = findViewById<TextView>(R.id.label_tv)
        productRv = findViewById(R.id.product_rv)

        if (attrs != null) {
            val myAttr = context.obtainStyledAttributes(attrs, R.styleable.NikeHomeItem)
            val showMoreText = myAttr.getString(R.styleable.NikeHomeItem_nike_show_more_text)
            val labelText = myAttr.getString(R.styleable.NikeHomeItem_nike_label_text)

            showMore.text = showMoreText
            label.text = labelText

            showMore.setOnClickListener {
                onShowMoreClicked?.invoke()
            }

            productListAdapter.productEventListener =
                object : ProductListAdapter.ProductEventListener {
                    override fun onProductClick(product: Product) {
                        onProductClicked?.invoke(product)
                    }

                    override fun onFavoriteBtnClick(product: Product) {
                        onFavoriteClicked?.invoke(product)
                    }

                }

            productRv!!.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            productRv!!.adapter = productListAdapter

            myAttr.recycle()
        }
    }

    fun fillList(products: List<Product>) {
        productListAdapter.products = products.toMutableList()
    }

}