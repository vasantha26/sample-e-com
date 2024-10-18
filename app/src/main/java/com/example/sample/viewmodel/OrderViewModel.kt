package com.example.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.model.Order
import com.example.sample.repository.OrderRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepositoryImpl
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            // Fetch orders from repository and update the LiveData
            val orderList = orderRepository.getAllOrders()
            _orders.postValue(orderList)
        }
    }
}

