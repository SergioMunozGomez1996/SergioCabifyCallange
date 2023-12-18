package com.example.sergiocabifychallange.useCase

import com.example.sergiocabifychallange.data.CabifyRepository
import com.example.sergiocabifychallange.data.network.NetworkResponse
import com.example.sergiocabifychallange.domain.model.Discount
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.domain.useCase.GetCartListUseCase
import com.example.sergiocabifychallange.domain.useCase.GetDiscountUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetCartListUseCaseTest {

    @RelaxedMockK
    private lateinit var cabifyRepository: CabifyRepository
    @RelaxedMockK
    private lateinit var getDiscountUseCase: GetDiscountUseCase

    private lateinit var getCartListUseCase: GetCartListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getCartListUseCase = GetCartListUseCase(
        cabifyRepository = cabifyRepository,
        getDiscountUseCase = getDiscountUseCase
        )
    }

    @Test
    fun `when discount is null, it returns the price x quantity`() {
        runBlocking {
            coEvery { cabifyRepository.getProductsFromApi() } returns NetworkResponse.Success(
                listOf(
                    mockProduct(
                        code = "code1",
                        price = 50.0,
                    ),
                    mockProduct(
                        code = "code2",
                        price = 30.0,
                    ),
                    mockProduct(
                        code = "code3",
                        price = 40.0,
                    )
                )
            )
            coEvery { getDiscountUseCase(any()) } returns null
            coEvery { cabifyRepository.getCartProductsFromPreferences() } returns flow {
                emit(listOf("code1", "code1", "code1", "code2", "code3"))
            }
            val cartItemList = getCartListUseCase().last()
            Assert.assertEquals(
                150.0f,
                cartItemList.first { it.product.code == "code1" }.finalPrice.toFloat()
            )
            Assert.assertEquals(
                30.0f,
                cartItemList.first { it.product.code == "code2" }.finalPrice.toFloat()
            )
            Assert.assertEquals(
                40.0f,
                cartItemList.first { it.product.code == "code3" }.finalPrice.toFloat()
            )
        }
    }

    @Test
    fun `when apply discount properly`() {
        runBlocking {
            coEvery { cabifyRepository.getProductsFromApi() } returns NetworkResponse.Success(
                listOf(
                    mockProduct(
                        code = "VOUCHER",
                        price = 5.0,
                    ),
                    mockProduct(
                        code = "TSHIRT",
                        price = 20.0,
                    ),
                    mockProduct(
                        code = "MUG",
                        price = 7.5,
                    )
                )
            )
            coEvery { getDiscountUseCase("VOUCHER") } returns Discount.TakeXGetY(
                numberOfPaidProducts = 1,
                numberOfGifts = 1,
            )
            coEvery { getDiscountUseCase("TSHIRT") } returns Discount.DiscountByAmount(
                minNumberOfProducts = 3,
                discountPercentage = 0.05f,
            )
            coEvery { getDiscountUseCase("MUG") } returns null

            coEvery { cabifyRepository.getCartProductsFromPreferences() } returns flow {
                emit(listOf("VOUCHER", "TSHIRT", "MUG"))
            }

            Assert.assertEquals(32.5f, getCartListUseCase().last().sumOf { it.finalPrice }.toFloat())

            coEvery { cabifyRepository.getCartProductsFromPreferences() } returns flow {
                emit(listOf("VOUCHER", "TSHIRT", "VOUCHER"))
            }

            Assert.assertEquals(25.0f, getCartListUseCase().last().sumOf { it.finalPrice }.toFloat())

            coEvery { cabifyRepository.getCartProductsFromPreferences() } returns flow {
                emit(listOf("TSHIRT", "TSHIRT", "TSHIRT", "VOUCHER", "TSHIRT"))
            }

            Assert.assertEquals(81.0f, getCartListUseCase().last().sumOf { it.finalPrice }.toFloat())

            coEvery { cabifyRepository.getCartProductsFromPreferences() } returns flow {
                emit(listOf("VOUCHER", "TSHIRT", "VOUCHER", "VOUCHER", "MUG", "TSHIRT", "TSHIRT"))
            }

            Assert.assertEquals(74.50f, getCartListUseCase().last().sumOf { it.finalPrice }.toFloat())
        }
    }

    private fun mockProduct(
        code: String = "code1",
        name: String = "Product 1",
        price: Double = 50.0,
        imageUrl: String = "imageUrl"
    ) = Product(
        code = code,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
}