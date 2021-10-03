package com.example.ecommapp.main_activity.shared_files.fragments.cart_fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.database.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    private val productDao : CartDao
) : ViewModel() {

    var products_list : MutableLiveData<List<Product>>

    init {
        products_list = MutableLiveData()
    }



    fun get_all_products(context: Context) : MutableLiveData<List<Product>>{

        //return CartRepository.get_all_data(context)
        var productData = MutableLiveData<List<Product>>()
        viewModelScope.launch(Dispatchers.IO){
            val data = CartRepository.get_all_data(context)
            withContext(Dispatchers.Main){
                productData.value = data
            }

        }
        return productData

    }

    fun delete_product(context: Context, product: Product){
        viewModelScope.launch(Dispatchers.IO){
            CartRepository.delete_item(context,product)
            withContext(Dispatchers.Main){

            }
        }
    }

    /*fun get_all_data_as_flow(context: Context) : LiveData<List<Product>>{
        return CartRepository.get_all_data_as_flow(context).
    }*/



    /*fun get_first_item(context: Context, input_id : String) : Product{

        return CartRepository.get_first_item(context, input_id)

    }*/
}