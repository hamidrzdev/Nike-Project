package com.hamiddev.nikeshop.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.formatPrice
import com.hamiddev.nikeshop.common.implementSpringAnimationTrait
import com.hamiddev.nikeshop.custom.NikeImageView
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.service.ImageLoadingService
import java.lang.IllegalStateException

const val VIEW_TYPE_LARGE = 2
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_ROUND = 0

class ProductListAdapter(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var viewType: Int = VIEW_TYPE_ROUND
    var productEventListener: ProductEventListener? = null
    var products = mutableListOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productIv: NikeImageView = itemView.findViewById(R.id.productIv)
        private val titleTv: TextView = itemView.findViewById(R.id.productTitleTv)
        private val currentPriceTv: TextView = itemView.findViewById(R.id.currentPriceTv)
        private val previousPriceTv: TextView = itemView.findViewById(R.id.previousPriceTv)
        private val favoriteBtn: ImageView = itemView.findViewById(R.id.favoriteBtn)
        fun bindProduct(product: Product) {
            imageLoadingService.load(productIv, product.image)
            titleTv.text = product.title
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previous_price)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productEventListener?.onProductClick(product)
            }

            if (product.isFavorite)
                favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
            else
                favoriteBtn.setImageResource(R.drawable.ic_favorites)

            favoriteBtn.setOnClickListener {
                productEventListener?.onFavoriteBtnClick(product)

                product.isFavorite = !product.isFavorite
                notifyItemChanged(bindingAdapterPosition)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutResId = when (viewType) {
            VIEW_TYPE_ROUND -> R.layout.item_product
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            VIEW_TYPE_LARGE -> R.layout.item_product_large
            else -> throw IllegalStateException("view type is not valid")
        }
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        )

    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

    interface ProductEventListener {
        fun onProductClick(product: Product)
        fun onFavoriteBtnClick(product: Product)
    }

}