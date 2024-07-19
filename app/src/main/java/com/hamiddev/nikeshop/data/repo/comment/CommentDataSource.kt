package com.hamiddev.nikeshop.data.repo.comment

import com.hamiddev.nikeshop.data.model.Comment
import com.hamiddev.nikeshop.data.model.Resource

interface CommentDataSource {
    suspend fun comments(productId:Int): Resource<List<Comment>>
    suspend fun insert(id:Int,title:String,content:String) : Resource<Comment>
}