package com.example.sample.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample.R
import com.example.sample.adapter.ProductAdapter
import com.example.sample.adapter.ProductLoadStateAdapter
import com.example.sample.databinding.FragmentProductListBinding
import com.example.sample.model.Product
import com.example.sample.view.MainActivity
import com.example.sample.viewmodel.ProductViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductListFragment : Fragment() , ProductAdapter.OnItemClickedListener{

    private var viewModel: ProductViewModel ?= null
    private  var productAdapter: ProductAdapter ?= null
    private  var binding: FragmentProductListBinding ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentProductListBinding.inflate(layoutInflater, container, false)

        return  binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =  ViewModelProvider(this)[ProductViewModel::class.java]


        setupRecyclerView()


    }



    private fun setupRecyclerView() {
        productAdapter = ProductAdapter()
        productAdapter?.setonClikedListener(this)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        binding?.recyclerView?.adapter = productAdapter?.withLoadStateHeaderAndFooter(
            header = ProductLoadStateAdapter { productAdapter?.retry() }, // Optional loading state header
            footer = ProductLoadStateAdapter { productAdapter?.retry() }  // Optional loading state footer
        )

        lifecycleScope.launch {
            viewModel?.productList?.collectLatest { pagingData ->
                try {
                    productAdapter?.submitData(pagingData)
                } catch (e: Exception) {
                    Log.e("TAG", "Error submitting data: ${e.message}")
                }
            }
        }

    }

    override fun onProductClick(product: Product) {
        val bundle = Bundle().apply { putString("KARYAKARTA_RESPONSE", Gson().toJson(product)) }
        NavHostFragment.findNavController(this@ProductListFragment).navigate(R.id.action_productListFragment_to_productDetailFragment,bundle )
    }
}

