package com.example.mastermartini_grupo2.api

import com.example.mastermartini_grupo2.model.Product
import com.example.mastermartini_grupo2.model.ProductResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 10
    ): ProductResponse

    @POST("products/add")
    suspend fun addProduct(
        @Body product: Product
    ): Product

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: Product
    ): Product

    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): Product
}