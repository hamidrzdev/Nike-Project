package com.hamiddev.nikeshop.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.databinding.ItemShowSizeBinding

class SizeShoeAdapter(val context: Context) :
    RecyclerView.Adapter<SizeShoeAdapter.SizeShowViewHolder>() {

    var sizeList = mutableListOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedPosition: Int = 0

    inner class SizeShowViewHolder(val binding: ItemShowSizeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvSize = binding.tvSize
        val root = binding.root

        fun bind(size: Int) {
            tvSize.text = size.toString()
            root.setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = bindingAdapterPosition
                notifyItemChanged(selectedPosition)

                if (selectedPosition == RecyclerView.NO_POSITION) return@setOnClickListener

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeShowViewHolder =
        SizeShowViewHolder(ItemShowSizeBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: SizeShowViewHolder, position: Int) {
        holder.bind(sizeList[position])
        if (selectedPosition == position) {
            holder.binding.card.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.black
                )
            )
            holder.binding.tvSize.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.binding.card.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            holder.binding.tvSize.setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }

    override fun getItemCount(): Int =
        sizeList.size
}