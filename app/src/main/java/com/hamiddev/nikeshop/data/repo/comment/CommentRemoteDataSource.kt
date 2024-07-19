package com.hamiddev.nikeshop.data.repo.comment

import com.google.gson.JsonObject
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Comment
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.service.ApiService
import javax.inject.Inject

class CommentRemoteDataSource @Inject constructor(private val apiService: ApiService) : CommentDataSource {

    override suspend fun comments(productId: Int): Resource<List<Comment>> =
        safeApiCall {
            apiService.getComments(productId)
        }

    override suspend fun insert(id:Int,title:String,content:String): Resource<Comment> =
        safeApiCall {
            apiService.addComment(JsonObject().apply {
                addProperty("title", title)
                addProperty("content", content)
                addProperty("product_id", id)
            })
        }

}