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
        "MUG" -> return "https://www.regalospublicitarios.com/img/cms/Tazas%20recomendadas/taza-impex02.jpg"
        "TSHIRT" -> return "https://srv.latostadora.com/image/defaultModel--id:5c50e653-862e-4208-92a6-5b9539d4156a;s:H_A1;b:f1f1f1;h:320;f:f.jpg"
        "VOUCHER" -> return "https://cdn.ecommercedns.uk/files/9/255879/0/31890820/10.jpg"
        else -> "https://st3.depositphotos.com/23594922/31822/v/450/depositphotos_318221368-stock-illustration-missing-picture-page-for-website.jpg"
    }
}
