package com.example.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.sample.R
import com.example.sample.databinding.FragmentProductDetailBinding
import com.example.sample.model.Order
import com.example.sample.model.Product
import com.example.sample.viewmodel.ProductDetailViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private var viewModel: ProductDetailViewModel ?= null
    private lateinit var binding: FragmentProductDetailBinding

    var product : Product ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentProductDetailBinding.inflate(layoutInflater ,container ,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]

        val receivedData = arguments?.getString("KARYAKARTA_RESPONSE") ?: ""


        if (receivedData.isNotEmpty()) {

            product = Gson().fromJson(receivedData, Product::class.java)
        }


        product?.let { setupUI(it) }
        product?.let { setupOrderButton(it) }
    }

    private fun setupUI(product: Product) {
        binding.apply {
            productNameTextView.text = product.title
            productDescriptionTextView.text = product.description
            productPriceTextView.text = "$${product.price}"
            // Load product image using Glide or any other image loading library
            Glide.with(this@ProductDetailFragment)
                .load(product.image)
                .into(productImageView)
        }
    }

    private fun setupOrderButton(product: Product) {
        binding.addToCartButton.setOnClickListener {
            showDeleteDialog(product)
        }
    }

    private fun showDeleteDialog(product: Product) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Order Visit ")
            .setMessage("Do you want  to this product order visit ?.")
            .setPositiveButton("Yes") { dialog, _ ->
                val order = Order(product.id , product.title ,product.price ,product.rating.count ,product.image)
                lifecycleScope.launch {
                    viewModel?.addProductToOrder(order)
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}
