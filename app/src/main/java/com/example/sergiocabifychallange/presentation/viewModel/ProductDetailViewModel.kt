package com.example.sergiocabifychallange.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sergiocabifychallange.domain.model.ProductDetails
import com.example.sergiocabifychallange.domain.useCase.AddProductToCartUseCase
import com.example.sergiocabifychallange.domain.useCase.GetCartListItemUseCase
import com.example.sergiocabifychallange.domain.useCase.RemoveProductToCartUseCase
import com.example.sergiocabifychallange.presentation.view.fragment.ProductDetailFragmentArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCartListItemUseCase: GetCartListItemUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductToCartUseCase: RemoveProductToCartUseCase,
): ViewModel() {

    private val args = ProductDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val productCode: String
        get() = args.productCode

    private val _productDetails = MutableSharedFlow<ProductDetails>()
    val productDetails = _productDetails.asSharedFlow()

    fun getCartListItem(){
        viewModelScope.launch {
            getCartListItemUseCase(productCode).collect{ productDetails ->
                productDetails?.let { _productDetails.emit(it) }
            }
        }
    }

    fun addProductToCart(){
        viewModelScope.launch {
            addProductToCartUseCase(productCode)
        }
    }

    fun removeProductFromCart(){
        viewModelScope.launch {
            removeProductToCartUseCase(productCode)
        }
    }
}