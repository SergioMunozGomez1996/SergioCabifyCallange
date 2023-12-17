package com.example.sergiocabifychallange.domain.useCase

import com.example.sergiocabifychallange.data.CabifyRepository
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    val cabifyRepository: CabifyRepository
) {
    suspend operator fun invoke(productId: String, quantity: Int = 1) {
        cabifyRepository.addProductsToChartToPreferences(productId, quantity)
    }
}