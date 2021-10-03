package com.example.ecommapp.user_auth.retrofit

import com.example.ecommapp.user_auth.data.LoginRequest
import com.example.ecommapp.user_auth.data.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("auth/login/")
    fun login_request(
        @Body request: LoginRequest
    ):Call<LoginResponse>
}