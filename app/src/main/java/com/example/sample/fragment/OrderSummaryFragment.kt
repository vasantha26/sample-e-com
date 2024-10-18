package com.example.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample.R
import com.example.sample.adapter.OrderAdapter
import com.example.sample.databinding.FragmentOrderSummaryBinding
import com.example.sample.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSummaryFragment : Fragment(R.layout.fragment_order_summary) {

    private var viewModel: OrderViewModel ?= null
    private  var binding: FragmentOrderSummaryBinding ?= null
    private val orderAdapter = OrderAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentOrderSummaryBinding.inflate(layoutInflater ,container ,false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
       viewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        setupRecyclerView()
        observeOrders()

    }

    private fun setupRecyclerView() {
        binding?.orderRecyclerView?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }

    private fun observeOrders() {
        viewModel?.orders?.observe(viewLifecycleOwner) { orders ->
            if(orders.isEmpty()){
                binding?.orderItemEmpty?.visibility = View.VISIBLE
                binding?.orderRecyclerView?.visibility = View.GONE
            }else{
                binding?.orderItemEmpty?.visibility = View.GONE
                binding?.orderRecyclerView?.visibility = View.VISIBLE
                orderAdapter.submitList(orders)

            }
        }
    }
}
