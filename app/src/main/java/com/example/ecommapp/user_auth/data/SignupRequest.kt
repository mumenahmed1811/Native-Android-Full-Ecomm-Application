package com.example.ecommapp.user_auth.data

import com.google.gson.annotations.SerializedName

data class SignupRequest(

    @SerializedName("email")
    val email : String,
    @SerializedName("username")
    val username : String,
    @SerializedName("password")
    val password : String,
    @SerializedName("name")
    val name : Name,
    @SerializedName("address")
    val address : List<String>,
    @SerializedName("phone")
    val phone : String

)

data class Name(
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String
)