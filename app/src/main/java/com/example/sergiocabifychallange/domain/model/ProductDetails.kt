package com.example.sergiocabifychallange.domain.model

data class ProductDetails(
    val product: Product,
    val quantity: Int,
    val discount: Discount?,
)