package com.example.mastermartini_grupo2.ui.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mastermartini_grupo2.model.Product
import com.example.mastermartini_grupo2.ui.theme.VerdeMasterMartini

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    product: Product,
    onBackClick: () -> Unit,
    onUpdateProduct: (Product) -> Unit
) {

    var name by remember { mutableStateOf(product.name) }
    var description by remember { mutableStateOf(product.description) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var image by remember { mutableStateOf(product.image) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Editar producto")
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Text("Nombre")
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = {
                    Text("Descripción")
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = {
                    Text("Precio")
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = image,
                onValueChange = { image = it },
                label = {
                    Text("URL de imagen")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Button(
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

                        onUpdateProduct(updatedProduct)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar cambios")
            }
        }
    }
}