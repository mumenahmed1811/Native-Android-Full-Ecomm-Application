package com.example.ecommapp.main_activity.shared_files.fragments.product_fragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.database.CartDao
import com.example.ecommapp.main_activity.database.ProductDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productDao : CartDao
): ViewModel() {


    init {

    }


    fun insert_item_to_cart(context: Context, product: Product){
        ProductRepository.insertData(context, product)


    }
}