package com.example.sample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val id: Int = 0,          // Primary key, auto-generated
    val productName: String,   // Name of the product
    val productPrice: Double,  // Price of the product
    val quantity: Int,      // Quantity ordered
    val image: String,
)
