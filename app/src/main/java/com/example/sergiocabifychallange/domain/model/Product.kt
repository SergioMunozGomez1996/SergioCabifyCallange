package com.example.sergiocabifychallange.domain.model

import com.example.sergiocabifychallange.data.model.productModel.ProductModel

data class Product(
    val code: String,
    val name: String,
    val price: Double,
    val imageUrl: String
)

fun ProductModel.toDomainLayerType() =  Product(code, name, price, getFakeImageUrl(code))

fun getFakeImageUrl(code: String) : String {
    return when(code) {
        "MUG" -> return "https://images.unsplash.com/photo-1605714262316-da2faa04731a"
        "TSHIRT" -> return "https://images.unsplash.com/photo-1633966887768-64f9a867bdba"
        "VOUCHER" -> return "https://images.unsplash.com/photo-1549465220-1a8b9238cd48"
        else -> "https://brandemia.org/sites/default/files/inline/images/cabify_logo_nuevo_2.png"
    }
}
