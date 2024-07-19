package com.hamiddev.nikeshop.data.repo.product

import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Resource

interface ProductRepository {
    suspend fun products(sort: Int): Resource<List<Product>>
    suspend fun addFavorite(product: Product):Long
    suspend fun removeFavorite(product: Product):Int
    suspend fun search(query:String):Resource<List<Product>>
    suspend fun favoriteProducts():Resource<List<Product>>
}