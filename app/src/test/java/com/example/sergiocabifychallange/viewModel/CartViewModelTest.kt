package com.example.sergiocabifychallange.viewModel

import com.example.sergiocabifychallange.domain.model.CartItem
import com.example.sergiocabifychallange.domain.model.Product
import com.example.sergiocabifychallange.domain.useCase.AddProductToCartUseCase
import com.example.sergiocabifychallange.domain.useCase.GetCartListUseCase
import com.example.sergiocabifychallange.domain.useCase.RemoveProductToCartUseCase
import com.example.sergiocabifychallange.presentation.viewModel.CartViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @RelaxedMockK
    private lateinit var getCartListUseCase: GetCartListUseCase
    @RelaxedMockK
    private lateinit var addProductToCartUseCase: AddProductToCartUseCase
    @RelaxedMockK
    private lateinit var removeProductToCartUseCase: RemoveProductToCartUseCase

    private lateinit var cartViewModel : CartViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(StandardTestDispatcher())
        cartViewModel = CartViewModel(
            getCartListUseCase = getCartListUseCase,
            addProductToCartUseCase = addProductToCartUseCase,
            removeProductToCartUseCase = removeProductToCartUseCase
        )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getCartList() is used load cart state`() = runTest {
        coEvery { getCartListUseCase() } returns flow {
            emit(
                listOf(
                    CartItem(
                        product = mockProduct(
                            code = "code1",
                            price = 50.0,
                        ),
                        quantity = 2,
                        discount = null,
                        finalPrice = 90.0,
                        withoutDiscountPrice = 100.0
                    ),
                    CartItem(
                        product = mockProduct(
                            code = "code2",
                            price = 10.0,
                        ),
                        quantity = 3,
                        discount = null,
                        finalPrice = 20.0,
                        withoutDiscountPrice = 30.0
                    )
                )
            )
        }

        var collectedValue: CartViewModel.State? = null
        val job = launch {
            cartViewModel.cartState.collect {
                collectedValue = it
            }
        }
        cartViewModel.getCartList()

        runCurrent()

        Assert.assertEquals(2, collectedValue?.cartItems?.count())

        job.cancel()
    }

    @Test
    fun `when getCartList() is used load, the loading fails and cart list is empty`() = runTest {
        coEvery { getCartListUseCase() } returns flow {
            emit(
                emptyList()
            )
        }

        var collectedValue: CartViewModel.State? = null
        val job = launch {
            cartViewModel.cartState.collect {
                collectedValue = it
            }
        }
        cartViewModel.getCartList()

        runCurrent()

        Assert.assertEquals(0, collectedValue?.cartItems?.count())

        job.cancel()
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