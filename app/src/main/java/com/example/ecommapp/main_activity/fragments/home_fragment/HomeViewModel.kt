package com.example.ecommapp.main_activity.fragments.home_fragment

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.retrofit.ProductService
import com.example.ecommapp.main_activity.retrofit.ProductsApi
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject




@HiltViewModel
class HomeViewModel @Inject constructor(
    private val state: SavedStateHandle,
    val api : ProductsApi
) : ViewModel() {

    // State Variables
    var screen_scroll_position_x : Int
    var screen_scroll_position_y : Int
    var new_collection_rv_scroll_position : Int
    var best_sellings_rv_scroll_position : Int
    var first_time : Boolean

    // List Variables
    var new_collection_list : MutableLiveData<List<Product>>
    var best_sellling_list : MutableLiveData<List<Product>>

    //


    init {
        // State Initializing
        screen_scroll_position_x = 0
        screen_scroll_position_y = 0
        new_collection_rv_scroll_position = 0
        best_sellings_rv_scroll_position = 0
        first_time = true

        // Initialzing The Variables
        new_collection_list = MutableLiveData()
        best_sellling_list = MutableLiveData()
    }


     fun get_new_collection_data() {
         Log.d(TAG, "get_new_collection_data Home: Success Response")

        var call = api.get_products_desc()
         call.enqueue(object : Callback<List<Product>> {
             override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                 if (response.body() != null){
                     new_collection_list.postValue(response.body())
                 }

                 else{
                     new_collection_list.postValue(null)
                     Log.d(TAG, "Home: Null Response")
                }
             }

             override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                 Log.d(TAG, "Home: Failure Response")
             }
         })

    }

    fun get_new_collection_data_without_hilt()  {
        new_collection_list = HomeRepository.get_all_products()
    }


     fun get_best_selling_data()  {

         Log.d(TAG, "get_best_selling_data Home: Success Response")

         var call = api.get_products_asc()
         call.enqueue(object : Callback<List<Product>> {
             override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                 if (response.body() != null){
                     best_sellling_list.postValue(response.body())
                 }
             }

             override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                 Log.d(TAG, "Home: Failure Response")
             }
         })
    }


    fun on_swipe_refresh(){
        //get_new_collection_data()
        get_best_selling_data()
    }

    fun set_scroll_x_pos(x : Int){ screen_scroll_position_x = x }
    fun set_scroll_y_pos(y : Int){ screen_scroll_position_y = y }
    fun set_rv_new_collection_scroll_pos(pos : Int){ new_collection_rv_scroll_position = pos }
    fun set_rv_best_sellings_scroll_pos(pos : Int){ best_sellings_rv_scroll_position = pos }
    fun set_first_time_boolean(b : Boolean){first_time = b}


}


