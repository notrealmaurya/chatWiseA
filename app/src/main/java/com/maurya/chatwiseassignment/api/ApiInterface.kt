package com.maurya.chatwiseassignment.api

import com.maurya.chatwiseassignment.repository.ProductResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}
