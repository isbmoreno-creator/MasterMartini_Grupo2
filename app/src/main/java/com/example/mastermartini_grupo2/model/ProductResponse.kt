package com.example.mastermartini_grupo2.model

data class ProductResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)