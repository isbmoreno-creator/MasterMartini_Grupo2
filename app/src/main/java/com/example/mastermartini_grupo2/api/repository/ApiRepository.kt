package com.example.mastermartini_grupo2.api.repository

import com.example.mastermartini_grupo2.model.ApiRecipe
import com.example.mastermartini_grupo2.api.RetrofitClient


class ApiRecipeRepository {

    suspend fun getRecipes(): List<ApiRecipe> {
        val response = RetrofitClient.recipeApi.getRecipes()

        println("GET Recipes - Código HTTP: ${response.code()}")

        if (response.isSuccessful) {
            return response.body()?.recipes ?: emptyList()
        } else {
            throw Exception("Error al obtener recetas: ${response.code()}")
        }
    }

    suspend fun updateRecipe(recipe: ApiRecipe): ApiRecipe? {
        val response = RetrofitClient.recipeApi.updateRecipe(
            id = recipe.id,
            recipe = recipe
        )

        println("PUT Recipe - Código HTTP: ${response.code()}")

        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun deleteRecipe(id: Int): Boolean {
        val response = RetrofitClient.recipeApi.deleteRecipe(id)

        println("DELETE Recipe - Código HTTP: ${response.code()}")

        return response.isSuccessful
    }
}
