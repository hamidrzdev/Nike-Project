package com.hamiddev.nikeshop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamiddev.nikeshop.data.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): DatabaseDao
}