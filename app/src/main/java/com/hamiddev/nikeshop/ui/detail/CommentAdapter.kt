package com.hamiddev.nikeshop.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.data.model.Comment
import com.hamiddev.nikeshop.databinding.ItemCommentBinding

class CommentAdapter(private val showAll: Boolean = false) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    var comments = mutableListOf<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = if (comments.size > 4 && !showAll) 4 else comments.size


    class ViewHolder(binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        private val titleTv = binding.commentTitleTv
        private val dateTv = binding.commentDateTv
        private val authorTv = binding.commentAuthorTv
        private val contentTv = binding.commentContent
        fun bind(comment: Comment) {
            titleTv.text = comment.title
            dateTv.text = comment.date
            authorTv.text = comment.author.email
            contentTv.text = comment.content
        }
    }
}