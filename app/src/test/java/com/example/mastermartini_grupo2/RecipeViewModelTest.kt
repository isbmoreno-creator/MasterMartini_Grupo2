package com.example.mastermartini_grupo2

import com.example.mastermartini_grupo2.model.Recipe
import com.example.mastermartini_grupo2.repository.RecipeRepository
import com.example.mastermartini_grupo2.viewmodel.RecipeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.unmockkAll


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class RecipeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RecipeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockkConstructor(com.example.mastermartini_grupo2.repository.RecipeRepository::class)
        mockkConstructor(com.example.mastermartini_grupo2.api.repository.ApiRecipeRepository::class)

        coEvery {
            anyConstructed<com.example.mastermartini_grupo2.repository.RecipeRepository>()
                .getRecipes()
        } returns emptyList()

        coEvery {
            anyConstructed<com.example.mastermartini_grupo2.api.repository.ApiRecipeRepository>()
                .getRecipes()
        } returns emptyList()

        val application = RuntimeEnvironment.getApplication()

        viewModel = RecipeViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        io.mockk.unmockkAll()
    }

    @Test
    fun getRecipeValidationErrors_withValidRecipe_returnsEmptySet() {
        val recipe = Recipe(
            id = 1L,
            name = "Pizza",
            description = "Pizza con queso",
            estimatedTime = "30 minutos",
            imagePath = null
        )

        val errors = viewModel.getRecipeValidationErrors(recipe)

        assertEquals(emptySet<String>(), errors)
    }

    @Test
    fun getRecipeValidationErrors_withEmptyName_returnsNameError() {
        val recipe = Recipe(
            id = 1L,
            name = "",
            description = "Pizza con queso",
            estimatedTime = "30 minutos",
            imagePath = null
        )

        val errors = viewModel.getRecipeValidationErrors(recipe)

        assertEquals(setOf("nombre"), errors)
    }

    @Test
    fun getRecipeValidationErrors_withEmptyDescription_returnsDescriptionError() {
        val recipe = Recipe(
            id = 1L,
            name = "Pizza",
            description = "",
            estimatedTime = "30 minutos",
            imagePath = null
        )

        val errors = viewModel.getRecipeValidationErrors(recipe)

        assertEquals(setOf("descripcion"), errors)
    }

    @Test
    fun getRecipeValidationErrors_withEmptyEstimatedTime_returnsTimeError() {
        val recipe = Recipe(
            id = 1L,
            name = "Pizza",
            description = "Pizza con queso",
            estimatedTime = "",
            imagePath = null
        )

        val errors = viewModel.getRecipeValidationErrors(recipe)

        assertEquals(setOf("tiempo"), errors)
    }

    @Test
    fun getRecipeValidationErrors_withAllFieldsEmpty_returnsAllErrors() {
        val recipe = Recipe(
            id = 1L,
            name = "",
            description = "",
            estimatedTime = "",
            imagePath = null
        )

        val errors = viewModel.getRecipeValidationErrors(recipe)

        assertEquals(
            setOf("nombre", "descripcion", "tiempo"),
            errors
        )
    }

    @Test
    fun addRecipe_callsRepositoryAndUpdatesRecipes() = runTest {
        val recipe = mockk<Recipe>()

        coEvery {
            anyConstructed<RecipeRepository>().addRecipe(recipe)
        } returns Unit

        coEvery {
            anyConstructed<RecipeRepository>().getRecipes()
        } returns listOf(recipe)

        viewModel.addRecipe(recipe)

        advanceUntilIdle()

        assertEquals(listOf(recipe), viewModel.recipes.value)

        coVerify(exactly = 1) {
            anyConstructed<RecipeRepository>().addRecipe(recipe)
        }

        coVerify(atLeast = 1) {
            anyConstructed<RecipeRepository>().getRecipes()
        }
    }

    @Test
    fun deleteRecipe_callsRepositoryAndUpdatesRecipes() = runTest {
        val recipe = mockk<Recipe>()

        coEvery {
            anyConstructed<RecipeRepository>().deleteRecipe(1L)
        } returns Unit

        coEvery {
            anyConstructed<RecipeRepository>().getRecipes()
        } returns listOf(recipe)

        viewModel.deleteRecipe(1L)

        advanceUntilIdle()

        assertEquals(listOf(recipe), viewModel.recipes.value)

        coVerify(exactly = 1) {
            anyConstructed<RecipeRepository>().deleteRecipe(1L)
        }

        coVerify(atLeast = 1) {
            anyConstructed<RecipeRepository>().getRecipes()
        }
    }

    @Test
    fun updateRecipe_callsRepositoryAndUpdatesRecipes() = runTest {
        val recipe = mockk<Recipe>()

        coEvery {
            anyConstructed<RecipeRepository>().updateRecipe(recipe)
        } returns Unit

        coEvery {
            anyConstructed<RecipeRepository>().getRecipes()
        } returns listOf(recipe)

        viewModel.updateRecipe(recipe)

        advanceUntilIdle()

        assertEquals(listOf(recipe), viewModel.recipes.value)

        coVerify(exactly = 1) {
            anyConstructed<RecipeRepository>().updateRecipe(recipe)
        }

        coVerify(atLeast = 1) {
            anyConstructed<RecipeRepository>().getRecipes()
        }
    }
}