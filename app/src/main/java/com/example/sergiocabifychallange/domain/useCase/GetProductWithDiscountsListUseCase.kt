package com.example.sergiocabifychallange.domain.useCase

import com.example.sergiocabifychallange.data.CabifyRepository
import com.example.sergiocabifychallange.data.network.NetworkResponse
import com.example.sergiocabifychallange.domain.model.Discount
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.utils.UiText
import javax.inject.Inject

class GetProductWithDiscountsListUseCase @Inject constructor(
    private val repository: CabifyRepository,
    private val getDiscountUseCase: GetDiscountUseCase
){
    suspend operator fun invoke(): NetworkResponse<Map<Product, Discount?>> {

        return repository.getProductsFromApi().let { response ->
            when (response) {
                is NetworkResponse.Success -> {
                    NetworkResponse.Success(response.data?.associateWith {
                        getDiscountUseCase(it.code)
                    })
                }
                is NetworkResponse.Error -> {
                    NetworkResponse.Error(
                        response.statusCode ?: 0,
                        response.message as UiText.DynamicString
                    )
                }
                is NetworkResponse.Exception -> {
                    NetworkResponse.Exception(
                        message = response.message as UiText.StringResource
                    )
                }
            }
        }

    }
}