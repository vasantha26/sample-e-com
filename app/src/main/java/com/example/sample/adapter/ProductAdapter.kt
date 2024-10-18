package com.example.sample.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.model.Product
import com.example.sample.databinding.ItemProductBinding

class ProductAdapter : PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(DIFF_CALLBACK) {


     var onItemClickedListener : OnItemClickedListener ?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)

        product?.let { holder.bind(it) }
    }

    fun setonClikedListener(listener: OnItemClickedListener) {
         this.onItemClickedListener = listener
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                textViewProductName.text = product.title
                textViewProductPrice.text = "$${product.price}"
                Glide.with(itemView.context).load(product.image).into(imageViewProduct)
                root.setOnClickListener { onItemClickedListener?.onProductClick(product) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
    interface OnItemClickedListener {
         fun onProductClick(product: Product)
    }
}
