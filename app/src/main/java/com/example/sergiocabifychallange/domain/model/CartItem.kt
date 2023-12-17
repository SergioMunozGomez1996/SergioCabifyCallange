package com.example.sergiocabifychallange.domain.model

data class CartItem(
    val product: Product,
    val quantity: Int,
    val discount: Discount?,
    val finalPrice: Double,
    val withoutDiscountPrice: Double?
)
