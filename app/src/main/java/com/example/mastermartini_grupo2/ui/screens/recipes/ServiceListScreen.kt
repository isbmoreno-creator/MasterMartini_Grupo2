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
import com.example.mastermartini_grupo2.model.Service
import com.example.mastermartini_grupo2.ui.theme.VerdeMasterMartini
import com.example.mastermartini_grupo2.ui.theme.components.ServiceCard

private val servicios = listOf(

    Service(
        id = 1,
        name = "Excelencia y calidad italiana",
        description = "Cuidadosa selección de materias prima de primera calidad.",
        price = "$25.000",
        image = R.drawable.excelencia
    ),

    Service(
        id = 2,
        name = "A la altura de los chefs",
        description = "Productos en línea con las últimas tendencias de alimentación.",
        price = "$80.000",
        image = R.drawable.chefs
    ),

    Service(
        id = 3,
        name = "Resultados garantizados",
        description = "Sabor, aroma, rendimiento y calidad en preparaciones.",
        price = "$35.000",
        image = R.drawable.resultados
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceListScreen(onBackClick: () -> Unit) {

    var search by remember { mutableStateOf("") }

    val filteredServices = servicios.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Catálogo de servicios")
                },

                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = {
                    Text("Buscar servicio")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Nuestros servicios (${filteredServices.size})",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeMasterMartini
            )

            Spacer(modifier = Modifier.height(14.dp))

            if (filteredServices.isEmpty()) {

                Text(
                    text = "No se encontraron servicios.",
                    color = Color.Gray
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(
                        items = filteredServices,
                        key = { it.id }
                    ) { service ->

                        ServiceCard(service = service)
                    }
                }
            }
        }
    }
}