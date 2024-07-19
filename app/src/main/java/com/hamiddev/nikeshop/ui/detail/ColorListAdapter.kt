package com.hamiddev.nikeshop.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.databinding.ItemColorBinding

class ColorListAdapter(val context: Context) :
    RecyclerView.Adapter<ColorListAdapter.ColorListViewHolder>() {
    var colorLists = mutableListOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorListViewHolder =
        ColorListViewHolder(
            ItemColorBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: ColorListViewHolder, position: Int) {
        holder.bind(colorLists[position])
        if (selectedPosition == position)
            holder.binding.selectItem.visibility = View.VISIBLE
        else
            holder.binding.selectItem.visibility = View.INVISIBLE
    }

    override fun getItemCount(): Int =
        colorLists.size

    inner class ColorListViewHolder(val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val root = binding.se
        fun bind(color: Int) {
            root.backgroundTintList = ContextCompat.getColorStateList(context, color)

            root.setOnClickListener {
                notifyItemChanged(selectedPosition)
                selectedPosition = bindingAdapterPosition
                notifyItemChanged(selectedPosition)

                if (selectedPosition == RecyclerView.NO_POSITION) return@setOnClickListener
            }
        }
    }
}