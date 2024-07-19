package com.hamiddev.nikeshop.data.repo.product

import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.service.ApiService
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    ProductDataSource {

    override suspend fun product(sort: Int?): Resource<List<Product>> =
        safeApiCall {
            apiService.getProducts(sort.toString())
        }

    override suspend fun addFavorite(product: Product): Long {
        TODO("Not yet implemented")
    }

    override suspend fun removeFavorite(product: Product): Int {
        TODO("Not yet implemented")
    }

    override suspend fun search(query: String): Resource<List<Product>> =
        safeApiCall {
        apiService.searchInProducts(query)
    }

    override suspend fun favoriteProducts(): Resource<List<Product>> {
        TODO("Not yet implemented")
    }
}