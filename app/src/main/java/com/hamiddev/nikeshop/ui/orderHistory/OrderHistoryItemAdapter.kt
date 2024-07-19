package com.hamiddev.nikeshop.ui.orderHistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.common.convertDpToPixel
import com.hamiddev.nikeshop.common.formatPrice
import com.hamiddev.nikeshop.custom.NikeImageView
import com.hamiddev.nikeshop.data.model.OrderHistoryItem
import com.hamiddev.nikeshop.databinding.OrderHistoryItemBinding
import com.hamiddev.nikeshop.service.ImageLoadingService

class OrderHistoryItemAdapter(val context: Context, val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<OrderHistoryItemAdapter.OrderHistoryItemViewHolder>() {


    private val layoutParams: LinearLayout.LayoutParams
    var productList: MutableList<OrderHistoryItem>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onOrderClicked: ((orderHistoryItem: OrderHistoryItem) -> Unit)? = null

    init {
        val size = convertDpToPixel(40f, context).toInt()
        val margin = convertDpToPixel(8f, context).toInt()
        layoutParams = LinearLayout.LayoutParams(size, size)
        layoutParams.setMargins(margin, 0, margin, 0)
    }

    inner class OrderHistoryItemViewHolder(binding: OrderHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val orderIdTv = binding.orderIdTv
        private val dateTv = binding.dateTv
        private val priceTv = binding.priceTv
        private val listLi = binding.orderProductsLl
        private val root = binding.root

        fun bind(orderHistoryItem: OrderHistoryItem) {
            orderIdTv.text = orderHistoryItem.id.toString()
            dateTv.text = orderHistoryItem.date
            priceTv.text = formatPrice(orderHistoryItem.payable)
            listLi.removeAllViews()

            root.setOnClickListener {
                onOrderClicked?.invoke(orderHistoryItem)
            }

            orderHistoryItem.order_items.forEach {
                val imageView = NikeImageView(context)
                imageView.layoutParams = layoutParams
                imageLoadingService.load(imageView, it.product.image)
                listLi.addView(imageView)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryItemViewHolder =
        OrderHistoryItemViewHolder(
            OrderHistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: OrderHistoryItemViewHolder, position: Int) =
        holder.bind(productList!![position])


    override fun getItemCount(): Int =
        productList!!.size

}