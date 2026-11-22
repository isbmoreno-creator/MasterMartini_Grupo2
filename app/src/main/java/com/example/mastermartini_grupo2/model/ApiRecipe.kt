package com.example.mastermartini_grupo2.model

data class ApiRecipeResponse(
    val recipes: List<ApiRecipe>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class ApiRecipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val prepTimeMinutes: Int,
    val cookTimeMinutes: Int,
    val servings: Int,
    val difficulty: String,
    val cuisine: String,
    val image: String,
    val rating: Double,
    val reviewCount: Int
)