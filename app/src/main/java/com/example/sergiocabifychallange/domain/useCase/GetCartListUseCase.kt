package com.example.sergiocabifychallange.domain.useCase

import com.example.sergiocabifychallange.data.CabifyRepository
import com.example.sergiocabifychallange.domain.model.CartItem
import com.example.sergiocabifychallange.domain.model.CartItemPrice
import com.example.sergiocabifychallange.domain.model.Discount
import com.example.sergiocabifychallange.domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartListUseCase @Inject constructor(
    private val cabifyRepository: CabifyRepository,
    private val getDiscountUseCase: GetDiscountUseCase
) {
     suspend operator fun invoke(): Flow<List<CartItem>> {
        val products = cabifyRepository.getProductsFromApi().data ?: emptyList()
        return cabifyRepository.getCartProductsFromPreferences().map { productCodeList ->
            products.filter { productCodeList.contains(it.code) }
                .map { product ->
                    val quantity = productCodeList.count { it == product.code }
                    val discount = getDiscountUseCase(product.code)
                    val prices =
                        getPrices(product = product, quantity = quantity, discount = discount)
                    CartItem(
                        product = product,
                        quantity = quantity,
                        discount = discount,
                        finalPrice = prices.finalPrice,
                        withoutDiscountPrice = prices.withoutDiscountPriceIfDifferent
                    )
                }
        }
    }

    private fun getPrices(
        product: Product,
        quantity: Int,
        discount: Discount?
    ): CartItemPrice {
        val withoutDiscountPrice = product.price * quantity
        val totalPrice = discount?.let {
            getPriceWithDiscount(product, quantity, it)
        } ?: withoutDiscountPrice
        return CartItemPrice(totalPrice, withoutDiscountPrice)

    }

    private fun getPriceWithDiscount(
        product: Product,
        quantity: Int,
        discount: Discount
    ): Double {
        return when (discount) {
            is Discount.DiscountByAmount -> {
                if (quantity >= discount.minNumberOfProducts) {
                    product.price * quantity * (1 - discount.discountPercentage)
                } else {
                    product.price * quantity
                }
            }
            is Discount.TakeXGetY -> {
                val blockSize: Int = (discount.numberOfPaidProducts + discount.numberOfGifts)
                val numberOfBlocks: Int = quantity / blockSize
                val rest = quantity % blockSize
                return numberOfBlocks * product.price * discount.numberOfPaidProducts + rest * product.price
            }
        }
    }
}