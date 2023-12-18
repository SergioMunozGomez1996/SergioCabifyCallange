package com.example.sergiocabifychallange.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sergiocabifychallange.databinding.FragmentCartBinding
import com.example.sergiocabifychallange.presentation.view.compose.CartView
import com.example.sergiocabifychallange.presentation.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel by viewModels<CartViewModel>()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        cartViewModel.getCartList()
    }

    private fun initView(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.cartState.collect {
                    binding.apply {
                        cartComposeView.setContent {
                            CartView(
                                cartItems = it.cartItems,
                                removeOneItem = {
                                    cartViewModel.removeProductFromCart(it)
                                },
                                addOneItem = {
                                    cartViewModel.addProductToCart(it)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
