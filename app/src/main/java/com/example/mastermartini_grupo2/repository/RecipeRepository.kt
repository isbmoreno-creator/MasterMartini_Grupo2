
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

class RecipeRepository(
    private val dataStore: DataStore<Preferences>
) {

    constructor(context: Context) : this(context.recipeDataStore)

    companion object {
        private val RECIPES = stringPreferencesKey("recipes")
    }

    // AGREGAR RECETA
    suspend fun addRecipe(recipe: Recipe) {
        dataStore.edit { preferences ->

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

    // OBTENER TODAS LAS RECETAS
    suspend fun getRecipes(): List<Recipe> {

        val preferences = dataStore.data.first()
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

    // EDITAR RECETA LOCAL
    suspend fun updateRecipe(recipe: Recipe) {

        dataStore.edit { preferences ->

            val jsonActual = preferences[RECIPES] ?: "[]"
            val recetasJson = JSONArray(jsonActual)
            val recetasActualizadas = JSONArray()

            for (i in 0 until recetasJson.length()) {

                val receta = recetasJson.getJSONObject(i)

                if (receta.getLong("id") == recipe.id) {

                    val recetaActualizada = JSONObject().apply {
                        put("id", recipe.id)
                        put("name", recipe.name)
                        put("description", recipe.description)
                        put("estimatedTime", recipe.estimatedTime)
                        put("imagePath", recipe.imagePath ?: "")
                    }

                    recetasActualizadas.put(recetaActualizada)

                } else {

                    recetasActualizadas.put(receta)
                }
            }

            preferences[RECIPES] = recetasActualizadas.toString()
        }
    }

    // ELIMINAR RECETA LOCAL
    suspend fun deleteRecipe(id: Long) {

        dataStore.edit { preferences ->

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

