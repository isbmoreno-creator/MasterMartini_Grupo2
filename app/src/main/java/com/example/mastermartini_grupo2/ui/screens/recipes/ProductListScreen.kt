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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mastermartini_grupo2.viewmodel.ProductViewModel
import androidx.compose.runtime.LaunchedEffect
import com.example.mastermartini_grupo2.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onBackClick: () -> Unit,
    productViewModel: ProductViewModel,
    onAddProductClick: (ProductViewModel) -> Unit,
    onEditProductClick: (Product) -> Unit
){
    val viewModel = productViewModel
    val products by viewModel.products.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    var search by remember {
        mutableStateOf("")
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

            // Listado de productos
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
                        key = { it.id }
                    ) { product ->

                        ProductCard(
                            product = product,
                            onDelete = {
                                viewModel.deleteProduct(product.id)
                            },
                            onEdit = {
                                onEditProductClick(product)
                            }
                        )
                    }
                }
            }
        }
    }
}
