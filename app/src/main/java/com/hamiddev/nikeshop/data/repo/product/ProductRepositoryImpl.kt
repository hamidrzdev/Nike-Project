package com.hamiddev.nikeshop.data.repo.product

import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.Product
import com.hamiddev.nikeshop.data.model.Resource
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductDataSource,
    private val productLocalDataSource: ProductDataSource
) :
    ProductRepository {

    override suspend fun products(sort: Int): Resource<List<Product>> {
        val local = productLocalDataSource.product()
        val favoriteProductsId = local.data?.map { it.id }
        val remote = productRemoteDataSource.product(sort)

        remote.data?.forEach { product ->
            favoriteProductsId?.let {
                if (it.contains(product.id))
                    product.isFavorite = true
            }
        }

        return remote
    }

    override suspend fun addFavorite(product: Product): Long =
        productLocalDataSource.addFavorite(product)


    override suspend fun removeFavorite(product: Product): Int =
        productLocalDataSource.removeFavorite(product)

    override suspend fun search(query: String): Resource<List<Product>> {
        val local = productLocalDataSource.product()
        val favoriteProductsId = local.data?.map { it.id }
        val remote = productRemoteDataSource.search(query)

        remote.data?.forEach {product ->
            favoriteProductsId?.let {
                if (it.contains(product.id))
                    product.isFavorite = true
            }
        }

        return remote
    }

    override suspend fun favoriteProducts(): Resource<List<Product>> =
        productLocalDataSource.favoriteProducts()

}