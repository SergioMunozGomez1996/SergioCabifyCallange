package com.example.sergiocabifychallange.data.network.response.product

import com.example.sergiocabifychallange.data.model.productModel.ProductModel
import com.google.gson.annotations.SerializedName

data class ProductApiResponse(
    @SerializedName("products") val products: List<ProductModel>
)
