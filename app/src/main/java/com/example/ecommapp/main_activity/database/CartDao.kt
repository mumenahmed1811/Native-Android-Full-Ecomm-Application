package com.example.ecommapp.main_activity.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.ecommapp.main_activity.data.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM product_table")
    fun get_all_carts(): List<Product>

    @Query("SELECT * FROM product_table")
    fun get_all_carts_as_flow(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert_item_to_cart(product: Product) : Long

    @Delete
    suspend fun delete_item_from_cart(product: Product)


    @Query("Delete FROM product_table")
    fun delete_all_cart()

    @Query("SELECT * FROM product_table WHERE id =:input_id")
    fun get_item(input_id : String) : Product


}