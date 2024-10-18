package com.example.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.databinding.ItemLoadStateBinding

class ProductLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, retry)
    }

    class LoadStateViewHolder(
        private val binding: ItemLoadStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState is LoadState.Error
                textViewError.isVisible = loadState is LoadState.Error
            }
        }
    }
}
