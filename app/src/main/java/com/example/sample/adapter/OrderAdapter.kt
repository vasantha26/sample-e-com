package com.example.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sample.model.Order
import com.example.sample.databinding.ItemOrderBinding

class OrderAdapter : ListAdapter<Order, OrderAdapter.OrderViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order, holder)
    }

    inner class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order, holder: OrderViewHolder) {
            binding.apply {
                productNameTextView.text = order.productName
                productQuantityTextView.text = "Quantity: ${order.quantity}"
                productPriceTextView.text = "$${order.productPrice}"
                Glide.with(holder.itemView.context)
                    .load(order.image)
                    .into(productImageView)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Order, newItem: Order) = oldItem == newItem
    }
}
