package com.example.sample.api

import com.example.sample.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(): List<Product>

}
