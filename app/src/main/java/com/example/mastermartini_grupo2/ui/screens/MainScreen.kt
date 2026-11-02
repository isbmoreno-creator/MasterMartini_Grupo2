package com.example.mastermartini_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    onProfileClick: () -> Unit,
    onRecipesClick: () -> Unit,
    onProductsClick: () -> Unit,
    onServicesClick: () -> Unit
) {

    var selectedItem by remember { mutableIntStateOf(0) }

    val verdeMasterMartini = Color(0xFF005B4F)
    val doradoMasterMartini = Color(0xFFC9A500)

    val items = listOf(
        "Services",
        "Productos",
        "Recetas",
        "Perfil"
    )

    val icons = listOf(
        Icons.Default.RoomService,
        Icons.Default.ShoppingBag,
        Icons.Default.MenuBook,
        Icons.Default.AccountCircle
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = verdeMasterMartini
            ) {

                items.forEachIndexed { index, item ->

                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index

                            when (index) {
                                0 -> onServicesClick()
                                1 -> onProductsClick()
                                2 -> onRecipesClick()
                                3 -> onProfileClick()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = icons[index],
                                contentDescription = item
                            )
                        },
                        label = {
                            Text(item)
                        },
                        colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                            selectedIconColor = verdeMasterMartini,
                            selectedTextColor = Color.White,
                            indicatorColor = doradoMasterMartini,
                            unselectedIconColor = Color.White,
                            unselectedTextColor = Color.White
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Master Martini",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = verdeMasterMartini
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Panadería & Pastelería",
                fontSize = 18.sp,
                color = doradoMasterMartini,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¡Bienvenido!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Explora nuestras recetas, productos y servicios.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
