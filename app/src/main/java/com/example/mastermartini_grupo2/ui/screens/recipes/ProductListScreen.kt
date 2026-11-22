package com.example.mastermartini_grupo2.ui.screens.recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mastermartini_grupo2.ui.theme.VerdeMasterMartini
import com.example.mastermartini_grupo2.ui.theme.components.ProductCard
import com.example.mastermartini_grupo2.viewmodel.ProductViewModel
import com.example.mastermartini_grupo2.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onBackClick: () -> Unit,
    productViewModel: ProductViewModel,
    onAddProductClick: (ProductViewModel) -> Unit
) {

    val viewModel = productViewModel
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    var search by remember {
        mutableStateOf("")
    }

    // Producto seleccionado para editar
    var selectedProduct by remember {
        mutableStateOf<Product?>(null)
    }

    // Producto seleccionado para eliminar
    var productToDelete by remember {
        mutableStateOf<Product?>(null)
    }

    val filteredProducts = products.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Productos")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VerdeMasterMartini,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // Buscador
            OutlinedTextField(
                value = search,
                onValueChange = {
                    search = it
                },
                label = {
                    Text("Buscar producto")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = {
                    onAddProductClick(viewModel)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar producto")
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Título y cantidad
            Text(
                text = "Nuestros productos (${filteredProducts.size})",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeMasterMartini
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // Listado
            if (filteredProducts.isEmpty()) {

                Text(
                    text = "No se encontraron productos.",
                    color = Color.Gray
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        bottom = 20.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(
                        items = filteredProducts,
                        key = {
                            it.id
                        }
                    ) { product ->

                        ProductCard(
                            product = product,

                            onDelete = {
                                productToDelete = product
                            },

                            onEdit = {
                                selectedProduct = product
                            }
                        )
                    }
                }
            }
        }
    }

    // =========================================================
    // DIÁLOGO PARA EDITAR PRODUCTO
    // =========================================================

    selectedProduct?.let { product ->

        var name by remember(product.id) {
            mutableStateOf(product.name)
        }

        var description by remember(product.id) {
            mutableStateOf(product.description)
        }

        var price by remember(product.id) {
            mutableStateOf(product.price.toString())
        }

        var image by remember(product.id) {
            mutableStateOf(product.image)
        }

        AlertDialog(
            onDismissRequest = {
                selectedProduct = null
            },

            title = {
                Text("Editar producto")
            },

            text = {

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text("Nombre")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        label = {
                            Text("Descripción")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = price,
                        onValueChange = {
                            price = it
                        },
                        label = {
                            Text("Precio")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = image,
                        onValueChange = {
                            image = it
                        },
                        label = {
                            Text("URL de imagen")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        val priceValue = price.toDoubleOrNull()

                        if (
                            name.isNotBlank() &&
                            description.isNotBlank() &&
                            priceValue != null &&
                            image.isNotBlank()
                        ) {

                            val updatedProduct = Product(
                                id = product.id,
                                name = name,
                                description = description,
                                price = priceValue,
                                image = image
                            )

                            viewModel.updateProduct(
                                updatedProduct.id,
                                updatedProduct
                            )

                            selectedProduct = null
                        }
                    }
                ) {
                    Text("Guardar")
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        selectedProduct = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // =========================================================
    // DIÁLOGO PARA CONFIRMAR ELIMINACIÓN
    // =========================================================

    productToDelete?.let { product ->

        AlertDialog(
            onDismissRequest = {
                productToDelete = null
            },

            title = {
                Text("Eliminar producto")
            },

            text = {
                Text(
                    "¿Deseas enviar la solicitud para eliminar " +
                            "${product.name}?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        viewModel.deleteProduct(
                            product.id
                        )

                        productToDelete = null
                    }
                ) {
                    Text(
                        text = "Eliminar",
                        color = Color.Red
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        productToDelete = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
