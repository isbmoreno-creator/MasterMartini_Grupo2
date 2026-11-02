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

import com.example.mastermartini_grupo2.R
import com.example.mastermartini_grupo2.model.Product
import com.example.mastermartini_grupo2.ui.theme.VerdeMasterMartini
import com.example.mastermartini_grupo2.ui.theme.components.ProductCard

// Lista de productos definidos desde el código
private val productos = listOf(

    Product(
        id = 1,
        name = "Margarinas",
        description = "Margarina de Hojaldre de alto rendimiento de origen italiana",
        price = "$10.000",
        image = R.drawable.margarinas
    ),

    Product(
        id = 2,
        name = "Chocolates Sucedáneos",
        description = "Caravella es una línea de cobertura ampliamente conocida en el mundo repostero y chocolatero por ofrecer una gama de productos para bañar, cubrir y rellenar.",
        price = "$15.000",
        image = R.drawable.chocolate
    ),

    Product(
        id = 3,
        name = "Cremas Líquidas UHT",
        description = "Una gama completa de cremas vegetales tipo chantilly y neutras para cocinar.",
        price = "$20.000",
        image = R.drawable.crema
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    onBackClick: () -> Unit
) {

    var search by remember {
        mutableStateOf("")
    }

    // Filtrar productos por nombre
    val filteredProducts = productos.filter {
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
                            product = product
                        )
                    }
                }
            }
        }
    }
}
