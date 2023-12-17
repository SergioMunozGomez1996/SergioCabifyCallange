package com.example.sergiocabifychallange.domain.model

data class CartItemPrice(
    val finalPrice: Double,
    val withoutDiscountPrice: Double
) {
    val withoutDiscountPriceIfDifferent: Double?
        get() = if (finalPrice != withoutDiscountPrice) withoutDiscountPrice else null
}