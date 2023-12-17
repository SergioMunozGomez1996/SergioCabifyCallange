package com.example.sergiocabifychallange.domain.useCase

import com.example.sergiocabifychallange.data.CabifyRepository
import com.example.sergiocabifychallange.domain.model.ProductDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartListItemUseCase @Inject constructor(
    private val repository: CabifyRepository,
    private val getDiscountUseCase: GetDiscountUseCase
) {
    suspend operator fun invoke(productCode: String): Flow<ProductDetails?> {
        val products = repository.getProductsFromApi().data
        return repository
            .getCartProductsFromPreferences()
            .map { cartProductCodeList ->
                products?.firstOrNull { it.code == productCode }?.let { product ->
                    val quantity = cartProductCodeList.count { it == product.code }
                    val discount = getDiscountUseCase(product.code)
                    ProductDetails(
                        product = product,
                        quantity = quantity,
                        discount = discount,
                    )
                }
            }
    }
}