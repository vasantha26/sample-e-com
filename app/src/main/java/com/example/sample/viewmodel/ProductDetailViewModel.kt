package com.example.sample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.model.Order
import com.example.sample.repository.OrderRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val orderRepository: OrderRepositoryImpl) : ViewModel() {

    fun addProductToOrder(order : Order) {
        viewModelScope.launch {
            orderRepository.placeOrder(order)
        }
    }
}
