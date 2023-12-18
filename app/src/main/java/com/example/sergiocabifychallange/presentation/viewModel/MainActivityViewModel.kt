package com.example.sergiocabifychallange.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sergiocabifychallange.domain.useCase.GetCartListUseCase
import com.example.sergiocabifychallange.domain.useCase.GetCartPriceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCartListUseCase: GetCartListUseCase,
    private val getCartPriceUseCase: GetCartPriceUseCase,
): ViewModel() {

    private val _cartPrice = MutableSharedFlow<Double>()
    val cartPrice = _cartPrice.asSharedFlow()

    fun getCartListPrice(){
        viewModelScope.launch {
            getCartListUseCase().collect{
                it.let { _cartPrice.emit(getCartPriceUseCase(it)) }
            }
        }
    }
}