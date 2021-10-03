package com.example.ecommapp.main_activity.fragments.home_fragment

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.retrofit.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    companion object{

        fun get_all_products() : MutableLiveData<List<Product>>{
            var new_collection_list = MutableLiveData<List<Product>>()
            Log.d(ContentValues.TAG, "get_new_collection_data Home without hilt: Success Response")

            val retrofit = ProductService.getProductService()
            var call = retrofit.get_products_desc()
            call.enqueue(object : Callback<List<Product>> {
                override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                    if (response.body() != null){
                        new_collection_list.postValue(response.body())
                        Log.d(ContentValues.TAG, "Home: Success Response")
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Log.d(ContentValues.TAG, "Home: Failure Response")
                }
            })
            return new_collection_list
        }

    }
}