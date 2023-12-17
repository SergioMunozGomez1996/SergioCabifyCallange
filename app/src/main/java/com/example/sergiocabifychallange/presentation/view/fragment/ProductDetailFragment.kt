package com.example.sergiocabifychallange.presentation.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.sergiocabifychallange.R
import com.example.sergiocabifychallange.databinding.FragmentProductDetailBinding
import com.example.sergiocabifychallange.presentation.viewModel.ProductDetailViewModel
import com.example.sergiocabifychallange.utils.formatToEuro
import com.example.sergiocabifychallange.utils.getTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val productDetailViewModel by viewModels<ProductDetailViewModel>()

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        productDetailViewModel.getCartListItem()

    }

    private fun initView(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productDetailViewModel.productDetails.collect { productDetail ->
                    binding.apply {
                        Glide.with(binding.productImage.context)
                            .load(productDetail.product.imageUrl)
                            .placeholder(R.drawable.ic_launcher_foreground)  // Add a placeholder here
                            .into(binding.productImage)
                        productTitle.text = productDetail.product.name
                        productPrice.text = formatToEuro(productDetail.product.price)
                        if (productDetail.discount != null){
                            productPromotion.text = productDetail.discount.getTitle(productPromotion.context)
                        } else
                            productPromotion.visibility = View.GONE
                        totalItems.text = productDetail.quantity.toString()
                        addButton.setOnClickListener{
                            productDetailViewModel.addProductToCart()
                        }
                        rmvButton.setOnClickListener{
                            productDetailViewModel.removeProductFromCart()
                        }

                    }
                }
            }
        }
    }

}