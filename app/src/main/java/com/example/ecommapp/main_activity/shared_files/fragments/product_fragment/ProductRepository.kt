package com.example.ecommapp.main_activity.shared_files.fragments.product_fragment

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.ecommapp.main_activity.data.Product
import com.example.ecommapp.main_activity.database.ProductDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ProductRepository {

    companion object {

        var productDatabase: ProductDatabase? = null

        var product: LiveData<Product>? = null

        fun initializeDB(context: Context) : ProductDatabase {
            return ProductDatabase.getDataseClient(context)
        }

        fun insertData(context: Context,product: Product) {

            productDatabase = initializeDB(context)

            CoroutineScope(IO).launch {
                productDatabase!!.productDao().insert_item_to_cart(product)
            }

        }



    }
}