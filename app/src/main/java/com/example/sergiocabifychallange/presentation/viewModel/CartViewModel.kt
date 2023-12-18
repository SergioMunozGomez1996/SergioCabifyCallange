package com.example.sergiocabifychallange.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sergiocabifychallange.domain.model.CartItem
import com.example.sergiocabifychallange.domain.useCase.AddProductToCartUseCase
import com.example.sergiocabifychallange.domain.useCase.GetCartListUseCase
import com.example.sergiocabifychallange.domain.useCase.RemoveProductToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartListUseCase: GetCartListUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val removeProductToCartUseCase: RemoveProductToCartUseCase
    ): ViewModel() {

    private val _cartState = MutableStateFlow(value = State(emptyList()))
    val cartState = _cartState.asSharedFlow()

    fun getCartList() {
        viewModelScope.launch {
            getCartListUseCase().collect {
                it.let {
                    _cartState.emit(_cartState.value.copy(cartItems = it))
                }
            }
        }
    }

    fun addProductToCart(productCode: String){
        viewModelScope.launch {
            addProductToCartUseCase(productCode)
        }
    }

    fun removeProductFromCart(productCode: String){
        viewModelScope.launch {
            removeProductToCartUseCase(productCode)
        }
    }

    data class State(
        val cartItems: List<CartItem> = emptyList(),
    )
}