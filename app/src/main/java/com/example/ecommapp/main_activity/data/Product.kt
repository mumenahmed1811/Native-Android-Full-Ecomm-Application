package com.example.ecommapp.main_activity.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "product_table")
@Parcelize
data class Product(

    @PrimaryKey
    @SerializedName("id")
    val id : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("price")
    val price : String,
    @SerializedName("category")
    val category : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("image")
    val image : String,

    val color : String,
    val size : String
): Parcelable
