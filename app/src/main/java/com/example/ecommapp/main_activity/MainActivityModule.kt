package com.example.ecommapp.main_activity

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.ecommapp.main_activity.database.ProductDatabase
import com.example.ecommapp.main_activity.retrofit.ProductsApi
import com.example.ecommapp.user_auth.retrofit.LoginAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainActivityModule {

    @Provides
    @Singleton
    @Named("Main Activity Retrofit")
    fun get_main_activity_retrofit_instance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun get_main_activity_api( @Named("Main Activity Retrofit") retrofit: Retrofit) : ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }


    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ) = Room.databaseBuilder(
        app,
        ProductDatabase::class.java,
        "product_db").build()

    @Provides
    fun provideTaskDao(db: ProductDatabase) = db.productDao()


}