package com.example.sergiocabifychallange.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sergiocabifychallange.data.network.NetworkResponse
import com.example.sergiocabifychallange.domain.model.Discount
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.domain.useCase.GetProductWithDiscountsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreItemsViewModel @Inject constructor(
    private val getProductWithDiscountsListUseCase: GetProductWithDiscountsListUseCase,
): ViewModel() {

    private val _networkResponse = MutableSharedFlow<NetworkResponse<Map<Product, Discount?>>>(replay = 1)
    val networkResponse = _networkResponse.asSharedFlow()


    fun getStoreItemsWithDiscount(){
        viewModelScope.launch {

            when (val response = getProductWithDiscountsListUseCase()) {
                is NetworkResponse.Success -> {
                    _networkResponse.emit(response)
                }
                is NetworkResponse.Error -> {
                    _networkResponse.emit(response)
                }
                is NetworkResponse.Exception -> {
                    _networkResponse.emit(response)
                }
            }
        }
    }
}