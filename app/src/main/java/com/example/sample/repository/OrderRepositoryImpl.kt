package com.example.sample.repository

import com.example.sample.model.Order
import com.example.sample.dao.OrderDao
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderDao: OrderDao)  {

     suspend fun getAllOrders(): List<Order> {
        return orderDao.getAllOrders()
    }

     suspend fun placeOrder(order: Order) {
        orderDao.insertOrder(order)
    }
}
