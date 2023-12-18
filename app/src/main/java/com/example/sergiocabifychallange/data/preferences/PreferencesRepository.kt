package com.example.sergiocabifychallange.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
){
    private var typeAdapter: Type = object : TypeToken<List<String>>() {}.type

    fun loadLocalCart(): Flow<List<String>> {
        return dataStore.data.map {
            it[PreferencesKeys.KEY_CART].let { json -> gson.fromJson(json, typeAdapter) } ?: emptyList()
        }
    }

    suspend fun addProductsToCart(productCode: String, quantity: Int) {
        dataStore.edit {
            val currentList: List<String> =
                it[PreferencesKeys.KEY_CART]?.let { json -> gson.fromJson(json, typeAdapter) } ?: emptyList()
            it[PreferencesKeys.KEY_CART] = gson.toJson(currentList.toMutableList().apply {
                addAll((1..quantity).map {
                    productCode
                })
            })
        }
    }

    suspend fun removeProductFromCart(productCode: String) {
        dataStore.edit {
            val currentList: List<String> =
                it[PreferencesKeys.KEY_CART]?.let { json -> gson.fromJson(json, typeAdapter) } ?: emptyList()
            it[PreferencesKeys.KEY_CART] = gson.toJson(currentList.toMutableList().apply { remove(productCode) })
        }
    }
}