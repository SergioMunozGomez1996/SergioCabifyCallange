package com.example.sergiocabifychallange.domain.useCase

import com.example.sergiocabifychallange.data.CabifyRepository
import javax.inject.Inject

class RemoveProductToCartUseCase @Inject constructor(
    val cabifyRepository: CabifyRepository
) {
    suspend operator fun invoke(productCode: String) {
        cabifyRepository.removeProductFromChartFromPreferences(productCode)
    }
}