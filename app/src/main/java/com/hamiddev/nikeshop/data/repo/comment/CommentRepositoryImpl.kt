package com.hamiddev.nikeshop.data.repo.comment

import com.hamiddev.nikeshop.data.model.Comment
import com.hamiddev.nikeshop.data.model.Resource
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(private val commentDataSource: CommentDataSource) : CommentRepository {

    override suspend fun comments(productId: Int): Resource<List<Comment>> =
        commentDataSource.comments(productId)

    override suspend fun add(id: Int, title: String, content: String): Resource<Comment> =
        commentDataSource.insert(id, title, content)

}