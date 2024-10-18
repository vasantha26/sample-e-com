package com.example.sample.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sample.api.ProductApi
import com.example.sample.model.Product




class ProductPagingSource(private val apiService: ProductApi) : PagingSource<Int, Product>() {

    private var allProducts: List<Product>? = null // Cache to hold all products from API

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage = params.key ?: 1
            val pageSize = 8

            // Fetch the full list only on the first page
            if (allProducts == null) {
                val response = apiService.getProducts() // Full 20 product list in a single call
                allProducts = response
            }

            // Calculate the subset of items for the current page
            val startIndex = (currentPage - 1) * pageSize
            val endIndex = minOf(startIndex + pageSize, allProducts!!.size)


            // Get the products for the current page
            val productsForPage = allProducts!!.subList(startIndex, endIndex)


            LoadResult.Page(
                data = productsForPage,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (endIndex < allProducts!!.size) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
