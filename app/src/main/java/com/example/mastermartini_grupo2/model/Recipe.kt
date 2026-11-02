package com.example.mastermartini_grupo2.model

data class Recipe(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val description: String,
    val estimatedTime: String,
    val imagePath: String? = null
)