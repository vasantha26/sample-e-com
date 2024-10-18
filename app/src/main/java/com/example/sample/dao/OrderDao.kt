package com.example.sample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sample.model.Order

@Dao
interface OrderDao {

    // Insert a new order
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    // Retrieve all orders
    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<Order>
}
