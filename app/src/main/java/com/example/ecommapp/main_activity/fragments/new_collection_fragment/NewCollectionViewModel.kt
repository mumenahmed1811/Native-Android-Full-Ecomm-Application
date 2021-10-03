package com.example.ecommapp.main_activity.fragments.new_collection_fragment

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.retrofit.ProductsApi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject




@HiltViewModel
class NewCollectionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val api : ProductsApi
) : ViewModel() {

    var new_collection_list_main : MutableLiveData<List<Product>>

    init {
        new_collection_list_main = MutableLiveData()

        get_new_collection_data()
    }

     fun get_new_collection_data(){
         Log.d(TAG, "get_best_selling_data New Collection: Success Response")

        var call = api.get_products_desc()
         call.enqueue(object : Callback<List<Product>> {
             override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                 if (response.body() != null){
                     new_collection_list_main.postValue(response.body())
                     Log.d(TAG, "New Collection: Success Response")
                 }

                 else{
                     new_collection_list_main.postValue(null)
                     Log.d(TAG, "New Collection: Null Response")
                }
             }

             override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                 Log.d(TAG, "New Collection: Failure Response")
             }
         })
    }


    fun on_swipe_refresh(){
        get_new_collection_data()
    }
}