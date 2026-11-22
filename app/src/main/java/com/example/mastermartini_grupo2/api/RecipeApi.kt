package com.example.mastermartini_grupo2.api
import com.example.mastermartini_grupo2.model.ApiRecipeResponse
import com.example.mastermartini_grupo2.model.ApiRecipe
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Body

interface RecipeApi {

    @GET("recipes")
    suspend fun getRecipes(
        @Query("limit") limit: Int = 50,
        @Query("skip") skip: Int = 0
    ): Response<ApiRecipeResponse>

    @PUT("recipes/{id}")
    suspend fun updateRecipe(
        @Path("id") id: Int,
        @Body recipe: ApiRecipe
    ): Response<ApiRecipe>

    @DELETE("recipes/{id}")
    suspend fun deleteRecipe(
        @Path("id") id: Int
    ): Response<ApiRecipe>
}