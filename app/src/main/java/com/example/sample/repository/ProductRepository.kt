package com.example.sample.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.sample.model.Product
import com.example.sample.api.ProductApi
import com.example.sample.paging.ProductPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productApi: ProductApi) {

    fun getProducts(): Pager<Int, Product> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
                enablePlaceholders = false,

            ),
            pagingSourceFactory = { ProductPagingSource(productApi) }
        )
    }
}
