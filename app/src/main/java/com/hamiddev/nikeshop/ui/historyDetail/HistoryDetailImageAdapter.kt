package com.hamiddev.nikeshop.ui.historyDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.databinding.ItemHistoryImageBinding
import com.hamiddev.nikeshop.service.ImageLoadingService

class HistoryDetailImageAdapter(var imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<HistoryDetailImageAdapter.HistoryDetailViewHolder>() {

    var images = mutableListOf<String>()

    inner class HistoryDetailViewHolder(binding: ItemHistoryImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val image = binding.image
        fun bind(imageId: String) {
            imageLoadingService.load(image, imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDetailViewHolder =
        HistoryDetailViewHolder(
            ItemHistoryImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: HistoryDetailViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int =
        images.size
}