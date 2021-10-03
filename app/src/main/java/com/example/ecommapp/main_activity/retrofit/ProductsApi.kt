package com.example.ecommapp.main_activity.retrofit

import androidx.lifecycle.LiveData
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.user_auth.data.LoginRequest
import com.example.ecommapp.user_auth.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface ProductsApi {

    @GET("products")
    fun get_products_asc(
    ): Call<List<Product>>


    @GET("products?sort=desc")
    fun get_products_desc(
    ): Call<List<Product>>


}