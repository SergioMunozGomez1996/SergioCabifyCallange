package com.example.sergiocabifychallange.data.network

import com.example.sergiocabifychallange.data.network.response.product.ProductApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface CabifyApiService {

    @GET("palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json")
    suspend fun getProducts(): Response<ProductApiResponse>
}