package com.example.mastermartini_grupo2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mastermartini_grupo2.model.Product
import com.example.mastermartini_grupo2.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getProducts()
    }
    fun getProducts() {
        if (_products.value.isNotEmpty()) {
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                val response = repository.getProducts()

                _products.value = response.products

            } catch (e: Exception) {

                _error.value = "Error al cargar los productos"

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun addProduct(
        product: Product,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {

                val newProduct = repository.addProduct(product)

                Log.d(
                    "POST_PRODUCTO",
                    "PRODUCTO CREADO: $newProduct"
                )

                onSuccess()

            } catch (e: Exception) {

                Log.e(
                    "POST_PRODUCTO",
                    "ERROR: ${e.message}",
                    e
                )

                _error.value = "Error al agregar el producto"
            }
        }
    }

    fun updateProduct(id: Int, product: Product) {

        viewModelScope.launch {
            try {

                val updatedProduct = repository.updateProduct(id, product)

                Log.d(
                    "PUT_PRODUCTO",
                    "PRODUCTO ACTUALIZADO: $updatedProduct"
                )

            } catch (e: Exception) {

                _error.value = "Error al actualizar el producto"

                Log.e(
                    "PUT_PRODUCTO",
                    "ERROR: ${e.message}",
                    e
                )
            }
        }
    }


    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            try {

                val deletedProduct = repository.deleteProduct(id)

                Log.d(
                    "DELETE_PRODUCTO",
                    "PRODUCTO ELIMINADO: $deletedProduct"
                )

            } catch (e: Exception) {

                Log.e(
                    "DELETE_PRODUCTO",
                    "ERROR: ${e.message}",
                    e
                )

                _error.value = "Error al eliminar el producto"
            }
        }
    }
}