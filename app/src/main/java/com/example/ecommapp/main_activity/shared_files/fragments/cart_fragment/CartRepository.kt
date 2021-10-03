package com.example.ecommapp.main_activity.shared_files.fragments.cart_fragment

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.database.ProductDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CartRepository {

    companion object {

        var productDatabase: ProductDatabase? = null

        var product: LiveData<Product>? = null

        fun initializeDB(context: Context) : ProductDatabase {
            return ProductDatabase.getDataseClient(context)
        }

        /*fun get_all_data(context: Context) : List<Product> {

            productDatabase = initializeDB(context)
            var temp_list = emptyList<Product>()

            CoroutineScope(Dispatchers.IO).launch {
                temp_list = productDatabase!!.productDao().get_all_carts()
            }
            return temp_list
        }*/

        /*fun get_first_item(context: Context, input_id : String) : Product {

            productDatabase = initializeDB(context)
            var temp_item : Product = Product("","","","","","","","")

                temp_item = productDatabase!!.productDao().get_item(input_id)

            return temp_item
        }*/

        suspend fun get_all_data(context: Context) : List<Product>{

            productDatabase = initializeDB(context)

            return productDatabase!!.productDao().get_all_carts()
        }

        fun get_all_data_as_flow(context: Context) : Flow<List<Product>> {

            productDatabase = initializeDB(context)

            return productDatabase!!.productDao().get_all_carts_as_flow()
        }

        suspend fun delete_item(context: Context, product: Product)  {

            productDatabase = initializeDB(context)

             productDatabase!!.productDao().delete_item_from_cart(product)
        }

    }
}