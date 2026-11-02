package com.example.mastermartini_grupo2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermartini_grupo2.model.Recipe
import com.example.mastermartini_grupo2.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = RecipeRepository(application)

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _recipes.value = repository.getRecipes()
        }
    }

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.addRecipe(recipe)
            loadRecipes()
        }
    }

    fun deleteRecipe(id: Long) {
        viewModelScope.launch {
            repository.deleteRecipe(id)
            loadRecipes()
        }
    }

    // Validación del formulario para agregar recetas.
    // La lógica de validación queda centralizada en el ViewModel.
    fun getRecipeValidationErrors(recipe: Recipe): Set<String> {

        val errors = mutableSetOf<String>()

        if (recipe.name.isBlank()) {
            errors.add("nombre")
        }

        if (recipe.description.isBlank()) {
            errors.add("descripcion")
        }

        if (recipe.estimatedTime.isBlank()) {
            errors.add("tiempo")
        }

        return errors
    }
}
