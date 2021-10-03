package com.example.ecommapp.user_auth

import com.example.ecommapp.user_auth.retrofit.LoginAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserAuthModule {

    @Provides
    @Singleton
    @Named("User Auth Retrofit")
    fun get_login_request_retrofit_instance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun get_login_request_api( @Named("User Auth Retrofit") retrofit:  Retrofit) : LoginAPI{
        return retrofit.create(LoginAPI::class.java)
    }

}