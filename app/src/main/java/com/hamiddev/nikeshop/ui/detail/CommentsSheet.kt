package com.hamiddev.nikeshop.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hamiddev.nikeshop.databinding.SheetCommentsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class CommentsSheet : BottomSheetDialogFragment() {
    private lateinit var binding: SheetCommentsBinding
    private lateinit var commentAdapter: CommentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SheetCommentsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = CommentsSheetArgs.fromBundle(requireArguments())
        commentAdapter = CommentAdapter(true)

        binding.commentsRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        commentAdapter.comments = bundle.comments.toMutableList()
        binding.commentsRv.adapter = commentAdapter

        Timber.i("timber comments size -> ${bundle.comments.size}")


    }
}