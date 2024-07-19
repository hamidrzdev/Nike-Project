package com.hamiddev.nikeshop.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.common.convertEnToFa
import com.hamiddev.nikeshop.common.formatPrice
import com.hamiddev.nikeshop.data.model.CartItem
import com.hamiddev.nikeshop.data.model.PurchaseDetail
import com.hamiddev.nikeshop.databinding.ItemCartBinding
import com.hamiddev.nikeshop.databinding.ItemPurchaseDetailsBinding
import com.hamiddev.nikeshop.service.ImageLoadingService

class CartItemAdapter(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var purchaseDetail: PurchaseDetail? = null
    var cartItems = mutableListOf<CartItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
     lateinit var cartItemViewCallBack: CartItemViewCallBack

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindCartItem(cartItem: CartItem) {
            with(binding) {
                productTitleTv.text = cartItem.product.title
                cartItemCountTv.text = convertEnToFa(cartItem.count.toString())
                previousPriceTv.text =
                    convertEnToFa(formatPrice(cartItem.product.price + cartItem.product.discount).toString())
                priceTv.text = convertEnToFa(formatPrice(cartItem.product.price).toString())
                imageLoadingService.load(productIv, cartItem.product.image)
                removeFromCartBtn.setOnClickListener {
                    cartItemViewCallBack.onRemoveCartItem(cartItem)
                }

                changeCountProgressBar.visibility =
                    if (cartItem.changeCountProgressBarIsVisible) View.VISIBLE else View.GONE

                cartItemCountTv.visibility =
                    if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE

                increaseBtn.setOnClickListener {
                    if (cartItem.count <= 4) {
                        cartItem.changeCountProgressBarIsVisible = true
                        changeCountProgressBar.visibility = View.VISIBLE
                        cartItemCountTv.visibility = View.INVISIBLE
                        cartItemViewCallBack.onIncreaseCartItemButtonClick(cartItem)
                    }
                }
                decreaseBtn.setOnClickListener {
                    if (cartItem.count >= 2) {
                        cartItem.changeCountProgressBarIsVisible = true
                        changeCountProgressBar.visibility = View.VISIBLE
                        cartItemCountTv.visibility = View.INVISIBLE
                        cartItemViewCallBack.onDecreaseCartItemButtonClick(cartItem)
                    }
                }
                productIv.setOnClickListener {
                    cartItemViewCallBack.onProductImageClick(cartItem)
                }
            }
        }
    }

    class PurchaseDetailViewHolder(val binding: ItemPurchaseDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindPurchaseDetail(totalPrice: Int, shippingCost: Int, payablePrice: Int) {
            with(binding) {
                totalPriceTv.text = convertEnToFa(formatPrice(totalPrice).toString())
                shippingCostTv.text = convertEnToFa(formatPrice(shippingCost).toString())
                payablePriceTv.text = convertEnToFa(formatPrice(payablePrice).toString())
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CART_ITEM)
            CartViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            PurchaseDetailViewHolder(
                ItemPurchaseDetailsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartViewHolder)
            holder.bindCartItem(cartItems[position])
        else if (holder is PurchaseDetailViewHolder)
            purchaseDetail?.let {
                holder.bindPurchaseDetail(it.total_price, it.shipping_cost, it.payable_price)
            }

    }

    override fun getItemCount(): Int = cartItems.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == cartItems.size)
            VIEW_TYPE_PURCHASE_DETAIL
        else
            VIEW_TYPE_CART_ITEM
    }

    fun increaseCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    fun decreaseCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }


    interface CartItemViewCallBack {
        fun onRemoveCartItem(itemCart: CartItem)
        fun onIncreaseCartItemButtonClick(itemCart: CartItem)
        fun onDecreaseCartItemButtonClick(itemCart: CartItem)
        fun onProductImageClick(itemCart: CartItem)
    }


    companion object {
        const val VIEW_TYPE_CART_ITEM = 0
        const val VIEW_TYPE_PURCHASE_DETAIL = 1
    }

}