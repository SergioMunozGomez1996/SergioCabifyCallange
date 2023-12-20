package com.example.sergiocabifychallange.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sergiocabifychallange.R
import com.example.sergiocabifychallange.data.network.NetworkResponse
import com.example.sergiocabifychallange.databinding.FragmentStoreItemsBinding
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.presentation.adapter.AdaptiveSpacingItemDecoration
import com.example.sergiocabifychallange.presentation.adapter.store.StoreAdapter
import com.example.sergiocabifychallange.presentation.viewModel.StoreItemsViewModel
import com.example.sergiocabifychallange.utils.prepareNetworkErrorAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StoreItemsFragment : Fragment() {

    private val storeItemsViewModel by viewModels<StoreItemsViewModel>()

    private var _binding: FragmentStoreItemsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStoreItemsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRecyclerView()
        storeItemsViewModel.getStoreItemsWithDiscount()

    }

    private fun initView(){
        binding.apply {
            cartButton.setOnClickListener {
                findNavController().navigate(StoreItemsFragmentDirections
                    .actionNavigationStoreToCartFragment())
            }
        }
    }

    private fun initRecyclerView(){

        binding.rcyProducts.layoutManager =
            GridLayoutManager(context, 2)

        val spacingInPixels =
            resources.getDimensionPixelSize(R.dimen.spacing16)
        binding.rcyProducts.addItemDecoration( AdaptiveSpacingItemDecoration(spacingInPixels))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                storeItemsViewModel.networkResponse.collect { response ->
                    binding.progress.isVisible = false
                    when (response) {
                        is NetworkResponse.Success -> {

                            if (response.data.isNullOrEmpty()) {
                                binding.emptyView.setMessage(getString(R.string.no_data),
                                    getString(R.string.come_back_soon))
                                binding.emptyView.visibility = View.VISIBLE
                            } else {
                                binding.emptyView.visibility = View.GONE

                                binding.rcyProducts.adapter =
                                    StoreAdapter(response.data) { product ->
                                        onItemSelected(product)
                                    }
                            }
                        }

                        else -> {
                            prepareNetworkErrorAction(response)
                        }

                    }
                }
            }
        }
    }

    private fun onItemSelected(product: Product){
        findNavController().navigate(StoreItemsFragmentDirections
            .actionNavigationStoreToProductDetailFragment(productCode = product.code))
    }

}