package com.hamiddev.nikeshop.data.repo.product

import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Resource
import com.hamiddev.nikeshop.db.DatabaseDao
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(private val databaseDao: DatabaseDao) :
    ProductDataSource {

    override suspend fun product(sort: Int?): Resource<List<Product>> = safeApiCall {
        databaseDao.getFavoriteProducts()
    }

    override suspend fun addFavorite(product: Product): Long =
        databaseDao.addToFavorites(product)

    override suspend fun removeFavorite(product: Product): Int =
        databaseDao.deleteFromFavorites(product)

    override suspend fun search(query: String): Resource<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun favoriteProducts(): Resource<List<Product>> = safeApiCall {
        databaseDao.getFavoriteProducts()
    }
}