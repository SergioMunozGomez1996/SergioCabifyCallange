package com.example.sergiocabifychallange.domain.model

sealed class Discount {
    data class DiscountByAmount(val minNumberOfProducts: Int, val discountPercentage: Float) :
        Discount()

    data class TakeXGetY(val numberOfPaidProducts: Int, val numberOfGifts: Int) : Discount()
}
