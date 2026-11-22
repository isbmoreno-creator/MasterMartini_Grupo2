package com.example.mastermartini_grupo2
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.mastermartini_grupo2.repository.RecipeRepository
import com.example.mastermartini_grupo2.model.Recipe
import kotlinx.coroutines.test.runTest

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.rules.TemporaryFolder
import org.robolectric.RobolectricTestRunner
import java.io.File

@RunWith(RobolectricTestRunner::class)
class RecipeRepositoryTest {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    private fun createRepository(
        scope: kotlinx.coroutines.CoroutineScope
    ): RecipeRepository {

        val dataStore = PreferenceDataStoreFactory.create(
            scope = scope,
            produceFile = {
                File(
                    temporaryFolder.root,
                    "recipes.preferences_pb"
                )
            }
        )

        return RecipeRepository(dataStore)
    }

    @Test
    fun obtenerRecetasListaVacia() = runTest {

        val repository = createRepository(backgroundScope)

        val recetas = repository.getRecipes()

        assertTrue(recetas.isEmpty())
    }

    @Test
    fun agregarRecetaCorrectamente() = runTest {

        val repository = createRepository(backgroundScope)

        val receta = Recipe(
            id = 1L,
            name = "Pizza",
            description = "Pizza de queso",
            estimatedTime = "30 minutos"
        )

        repository.addRecipe(receta)

        val recetas = repository.getRecipes()

        assertEquals(1, recetas.size)
        assertEquals("Pizza", recetas[0].name)
        assertEquals("Pizza de queso", recetas[0].description)
    }

    @Test
    fun editarRecetaCorrectamente() = runTest {

        val repository = createRepository(backgroundScope)

        val receta = Recipe(
            id = 1L,
            name = "Pizza",
            description = "Pizza original",
            estimatedTime = "30 minutos"
        )

        repository.addRecipe(receta)

        val recetaActualizada = receta.copy(
            name = "Pizza especial",
            description = "Pizza con queso y tomate",
            estimatedTime = "45 minutos"
        )

        repository.updateRecipe(recetaActualizada)

        val recetas = repository.getRecipes()

        assertEquals(1, recetas.size)
        assertEquals("Pizza especial", recetas[0].name)
        assertEquals(
            "Pizza con queso y tomate",
            recetas[0].description
        )
        assertEquals("45 minutos", recetas[0].estimatedTime)
    }



    @Test
    fun eliminarRecetaCorrectamente() = runTest {

        val repository = createRepository(backgroundScope)

        val receta1 = Recipe(
            id = 1L,
            name = "Pizza",
            description = "Pizza de queso",
            estimatedTime = "30 minutos"
        )

        val receta2 = Recipe(
            id = 2L,
            name = "Hamburguesa",
            description = "Hamburguesa casera",
            estimatedTime = "20 minutos"
        )

        repository.addRecipe(receta1)
        repository.addRecipe(receta2)

        repository.deleteRecipe(1L)

        val recetas = repository.getRecipes()

        assertEquals(1, recetas.size)
        assertEquals("Hamburguesa", recetas[0].name)
    }


    @Test
    fun guardarRecetaConImagen() = runTest {

        val repository = createRepository(backgroundScope)

        val receta = Recipe(
            id = 3L,
            name = "Ensalada",
            description = "Ensalada fresca",
            estimatedTime = "15 minutos",
            imagePath = "/imagenes/ensalada.jpg"
        )

        repository.addRecipe(receta)

        val recetas = repository.getRecipes()

        assertEquals(1, recetas.size)
        assertEquals(
            "/imagenes/ensalada.jpg",
            recetas[0].imagePath
        )
    }
}
