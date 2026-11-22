package com.example.mastermartini_grupo2.repository

import com.example.mastermartini_grupo2.api.ProductApi
import com.example.mastermartini_grupo2.api.RetrofitInstance
import com.example.mastermartini_grupo2.model.Product
import com.example.mastermartini_grupo2.model.ProductResponse

class ProductRepository {

    private val api = RetrofitInstance.retrofit.create(ProductApi::class.java)

    suspend fun getProducts(): ProductResponse {
        return api.getProducts(10)
    }

    suspend fun addProduct(product: Product): Product {
        return api.addProduct(product)
    }

    suspend fun updateProduct(id: Int, product: Product): Product {
        return api.updateProduct(id, product)
    }


    suspend fun deleteProduct(id: Int): Product {
        return api.deleteProduct(id)
    }
}