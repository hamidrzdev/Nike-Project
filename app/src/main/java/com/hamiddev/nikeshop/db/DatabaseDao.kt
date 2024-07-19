package com.hamiddev.nikeshop.db

import androidx.room.*
import com.hamiddev.nikeshop.data.model.Product

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM products")
    fun getFavoriteProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: Product):Long

    @Delete
    suspend fun deleteFromFavorites(product: Product):Int
}