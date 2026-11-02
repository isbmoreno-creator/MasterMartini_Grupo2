package com.example.mastermartini_grupo2.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mastermartini_grupo2.model.Recipe
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject

private val Context.recipeDataStore by preferencesDataStore(
    name = "recipe_preferences"
)

class RecipeRepository(private val context: Context) {

    companion object {
        private val RECIPES = stringPreferencesKey("recipes")
    }

    suspend fun addRecipe(recipe: Recipe) {
        context.recipeDataStore.edit { preferences ->

            val jsonActual = preferences[RECIPES] ?: "[]"
            val recetasJson = JSONArray(jsonActual)

            val nuevaReceta = JSONObject().apply {
                put("id", recipe.id)
                put("name", recipe.name)
                put("description", recipe.description)
                put("estimatedTime", recipe.estimatedTime)
                put("imagePath", recipe.imagePath ?: "")
            }

            recetasJson.put(nuevaReceta)

            preferences[RECIPES] = recetasJson.toString()
        }
    }

    suspend fun getRecipes(): List<Recipe> {

        val preferences = context.recipeDataStore.data.first()
        val jsonActual = preferences[RECIPES] ?: "[]"

        val recetasJson = JSONArray(jsonActual)
        val recetas = mutableListOf<Recipe>()

        for (i in 0 until recetasJson.length()) {

            val objeto = recetasJson.getJSONObject(i)

            recetas.add(
                Recipe(
                    id = objeto.getLong("id"),
                    name = objeto.getString("name"),
                    description = objeto.getString("description"),
                    estimatedTime = objeto.getString("estimatedTime"),
                    imagePath = objeto.optString("imagePath").ifBlank {
                        null
                    }
                )
            )
        }

        return recetas
    }

    suspend fun deleteRecipe(id: Long) {
        context.recipeDataStore.edit { preferences ->

            val jsonActual = preferences[RECIPES] ?: "[]"
            val recetasJson = JSONArray(jsonActual)
            val recetasActualizadas = JSONArray()

            for (i in 0 until recetasJson.length()) {

                val receta = recetasJson.getJSONObject(i)

                if (receta.getLong("id") != id) {
                    recetasActualizadas.put(receta)
                }
            }

            preferences[RECIPES] = recetasActualizadas.toString()
        }
    }
}
