package com.example.mastermartini_grupo2.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,

    @SerializedName("title")
    val name: String,

    val description: String,

    val price: Double,

    @SerializedName("thumbnail")
    val image: String
)