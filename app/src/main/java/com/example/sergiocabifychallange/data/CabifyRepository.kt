package com.example.sergiocabifychallange.data

import com.example.sergiocabifychallange.data.network.RetrofitClient
import com.example.sergiocabifychallange.data.preferences.PreferencesRepository
import javax.inject.Inject

class CabifyRepository @Inject constructor(
    private val api: RetrofitClient,
    private val preferenceManager: PreferencesRepository,
){

    suspend fun getProductsFromApi() = api.getProducts()

    suspend fun getCartProductsFromPreferences() = preferenceManager.loadLocalCart()
    suspend fun addProductToChartToPreferences(productCode: String) =
        preferenceManager.addProductToCart(productCode)
    suspend fun addProductsToChartToPreferences(productCode: String, quantity: Int) =
        preferenceManager.addProductsToCart(productCode, quantity)
    suspend fun removeProductFromChartFromPreferences(productCode: String) =
        preferenceManager.removeProductFromCart(productCode)

}