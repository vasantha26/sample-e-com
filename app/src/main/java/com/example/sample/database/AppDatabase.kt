package com.example.sample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sample.dao.OrderDao
import com.example.sample.model.Order

@Database(entities = [Order::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}
