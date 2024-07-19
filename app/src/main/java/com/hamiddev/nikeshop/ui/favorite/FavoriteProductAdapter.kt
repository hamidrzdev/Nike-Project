package com.hamiddev.nikeshop.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.databinding.ItemFavoriteProductBinding
import com.hamiddev.nikeshop.service.ImageLoadingService

class FavoriteProductAdapter(var imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<FavoriteProductAdapter.FavoriteViewHolder>() {

    var products = mutableListOf<Product>()

    var onClickProduct: ((product: Product) -> Unit)? = null
    var onLongClickProduct: ((product: Product) -> Unit)? = null

    inner class FavoriteViewHolder(val binding: ItemFavoriteProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val titleTv = binding.productTitleTv
        private val productIv = binding.nikeImageView
        fun bindProduct(product: Product) {
            titleTv.text = product.title
            imageLoadingService.load(productIv, product.image)
            itemView.setOnClickListener {
                onClickProduct?.invoke(product)
            }
            itemView.setOnLongClickListener {
                products.remove(product)
                notifyItemRemoved(bindingAdapterPosition)
                onLongClickProduct?.invoke(product)
                return@setOnLongClickListener false
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        FavoriteViewHolder(ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holderFavorite: FavoriteViewHolder, position: Int) {
        holderFavorite.bindProduct(products[position])
    }

    override fun getItemCount(): Int = products.size

}