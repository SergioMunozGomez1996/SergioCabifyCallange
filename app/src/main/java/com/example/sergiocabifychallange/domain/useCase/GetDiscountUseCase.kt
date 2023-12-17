package com.example.sergiocabifychallange.domain.useCase

import com.example.sergiocabifychallange.domain.model.Discount
import javax.inject.Inject

class GetDiscountUseCase @Inject constructor(){
    operator fun invoke(productCode: String): Discount? {

        return when (productCode) {
            "VOUCHER" -> Discount.TakeXGetY(1, 1)
            "TSHIRT" -> Discount.DiscountByAmount(3, 0.05f)
            else -> null
        }

    }
}