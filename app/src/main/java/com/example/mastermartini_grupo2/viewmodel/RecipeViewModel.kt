package com.example.mastermartini_grupo2.viewmodel



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermartini_grupo2.model.Recipe
import com.example.mastermartini_grupo2.repository.RecipeRepository
import com.example.mastermartini_grupo2.model.ApiRecipe
import com.example.mastermartini_grupo2.api.repository.ApiRecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = RecipeRepository(application)
    private val apiRepository = ApiRecipeRepository()

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    private val _apiRecipes = MutableStateFlow<List<ApiRecipe>>(emptyList())
    val apiRecipes: StateFlow<List<ApiRecipe>> = _apiRecipes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _recipes.value = repository.getRecipes()

            _isLoading.value = true
            _error.value = null

            try {
                _apiRecipes.value = apiRepository.getRecipes()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error al cargar las recetas"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.addRecipe(recipe)
            _recipes.value = repository.getRecipes()
        }
    }


    fun deleteRecipe(id: Long) {
        viewModelScope.launch {
            repository.deleteRecipe(id)
            _recipes.value = repository.getRecipes()
        }
    }


    fun updateApiRecipe(recipe: ApiRecipe) {
        viewModelScope.launch {
            try {
                _error.value = null

                val result = apiRepository.updateRecipe(recipe)

                if (result != null) {
                    println("Receta API editada correctamente")
                } else {
                    _error.value = "No se pudo editar la receta de la API"
                }

            } catch (e: Exception) {
                _error.value = e.message ?: "Error al editar la receta"
            }
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.updateRecipe(recipe)
            _recipes.value = repository.getRecipes()
        }
    }

    fun deleteApiRecipe(id: Int) {
        viewModelScope.launch {
            try {
                _error.value = null

                val success = apiRepository.deleteRecipe(id)

                if (success) {
                    println("Receta API eliminada correctamente")
                } else {
                    _error.value = "No se pudo eliminar la receta de la API"
                }

            } catch (e: Exception) {
                _error.value = e.message ?: "Error al eliminar la receta"
            }
        }
    }


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



