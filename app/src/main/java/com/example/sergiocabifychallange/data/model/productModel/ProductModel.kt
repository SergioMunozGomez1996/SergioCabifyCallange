package com.example.sergiocabifychallange.data.model.productModel

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double
)
