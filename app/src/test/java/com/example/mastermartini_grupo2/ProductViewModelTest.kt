package com.example.mastermartini_grupo2

import com.example.mastermartini_grupo2.model.Product
import com.example.mastermartini_grupo2.model.ProductResponse
import com.example.mastermartini_grupo2.repository.ProductRepository
import com.example.mastermartini_grupo2.viewmodel.ProductViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ProductViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductViewModel

    private val product = Product(
        id = 1,
        name = "Producto de prueba",
        description = "Descripción de prueba",
        price = 1000.0,
        image = "https://example.com/producto.jpg"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()

        coEvery {
            repository.getProducts()
        } returns ProductResponse(
            products = emptyList(),
            total = 0,
            skip = 0,
            limit = 10
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---------------------------------------------------------
    // GET
    // ---------------------------------------------------------

    @Test
    fun getProducts_withValidResponse_loadsProducts() = runTest {

        val response = ProductResponse(
            products = listOf(product),
            total = 1,
            skip = 0,
            limit = 10
        )

        coEvery {
            repository.getProducts()
        } returns response

        viewModel = ProductViewModel(repository)

        advanceUntilIdle()

        assertEquals(
            listOf(product),
            viewModel.products.value
        )

        assertFalse(viewModel.isLoading.value)
        assertEquals(null, viewModel.error.value)

        coVerify(exactly = 1) {
            repository.getProducts()
        }
    }

    @Test
    fun getProducts_withError_setsError() = runTest {

        coEvery {
            repository.getProducts()
        } throws Exception("Error de conexión")

        viewModel = ProductViewModel(repository)

        advanceUntilIdle()

        assertEquals(
            "Error al cargar los productos",
            viewModel.error.value
        )

        assertFalse(viewModel.isLoading.value)

        coVerify(exactly = 1) {
            repository.getProducts()
        }
    }

    @Test
    fun getProducts_whenProductsAlreadyLoaded_doesNotCallRepositoryAgain() =
        runTest {

            val response = ProductResponse(
                products = listOf(product),
                total = 1,
                skip = 0,
                limit = 10
            )

            coEvery {
                repository.getProducts()
            } returns response

            viewModel = ProductViewModel(repository)

            advanceUntilIdle()

            viewModel.getProducts()

            advanceUntilIdle()

            coVerify(exactly = 1) {
                repository.getProducts()
            }
        }

    // ---------------------------------------------------------
    // POST
    // ---------------------------------------------------------

    @Test
    fun addProduct_withValidProduct_callsRepositoryAndSuccess() =
        runTest {

            coEvery {
                repository.addProduct(product)
            } returns product

            var successCalled = false

            viewModel = ProductViewModel(repository)

            viewModel.addProduct(product) {
                successCalled = true
            }

            advanceUntilIdle()

            assertTrue(successCalled)

            coVerify(exactly = 1) {
                repository.addProduct(product)
            }
        }

    @Test
    fun addProduct_withError_setsError() = runTest {

        coEvery {
            repository.addProduct(product)
        } throws Exception("Error")

        var successCalled = false

        viewModel = ProductViewModel(repository)

        viewModel.addProduct(product) {
            successCalled = true
        }

        advanceUntilIdle()

        assertFalse(successCalled)

        assertEquals(
            "Error al agregar el producto",
            viewModel.error.value
        )

        coVerify(exactly = 1) {
            repository.addProduct(product)
        }
    }

    // ---------------------------------------------------------
    // PUT
    // ---------------------------------------------------------

    @Test
    fun updateProduct_withValidProduct_callsRepository() =
        runTest {

            coEvery {
                repository.updateProduct(1, product)
            } returns product

            viewModel = ProductViewModel(repository)

            viewModel.updateProduct(1, product)

            advanceUntilIdle()

            assertEquals(null, viewModel.error.value)

            coVerify(exactly = 1) {
                repository.updateProduct(1, product)
            }
        }

    @Test
    fun updateProduct_withError_setsError() =
        runTest {

            coEvery {
                repository.updateProduct(1, product)
            } throws Exception("Error")

            viewModel = ProductViewModel(repository)

            viewModel.updateProduct(1, product)

            advanceUntilIdle()

            assertEquals(
                "Error al actualizar el producto",
                viewModel.error.value
            )

            coVerify(exactly = 1) {
                repository.updateProduct(1, product)
            }
        }

    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------

    @Test
    fun deleteProduct_withValidId_callsRepository() =
        runTest {

            coEvery {
                repository.deleteProduct(1)
            } returns product

            viewModel = ProductViewModel(repository)

            viewModel.deleteProduct(1)

            advanceUntilIdle()

            assertEquals(null, viewModel.error.value)

            coVerify(exactly = 1) {
                repository.deleteProduct(1)
            }
        }

    @Test
    fun deleteProduct_withError_setsError() =
        runTest {

            coEvery {
                repository.deleteProduct(1)
            } throws Exception("Error")

            viewModel = ProductViewModel(repository)

            viewModel.deleteProduct(1)

            advanceUntilIdle()

            assertEquals(
                "Error al eliminar el producto",
                viewModel.error.value
            )

            coVerify(exactly = 1) {
                repository.deleteProduct(1)
            }
        }
}