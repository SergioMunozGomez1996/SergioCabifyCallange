package com.example.sergiocabifychallange.domain.useCase

import com.example.sergiocabifychallange.domain.model.CartItem
import javax.inject.Inject

class GetCartPriceUseCase @Inject constructor(){
    operator fun invoke(cartItems: List<CartItem>): Double {
        return cartItems.sumOf { it.finalPrice }
    }
}