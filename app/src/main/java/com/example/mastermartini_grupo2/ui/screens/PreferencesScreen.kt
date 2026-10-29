package com.example.mastermartini_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.viewmodel.UserViewModel

@Composable
fun PreferencesScreen(
    onBackClick: () -> Unit
) {

    val userViewModel: UserViewModel = viewModel()

    val user = remember { mutableStateOf<User?>(null) }

    val tipoNegocio = remember { mutableStateOf("") }
    val tamanoNegocio = remember { mutableStateOf("") }
    val preferenciasContenido = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        user.value = userViewModel.getUser()

        if (user.value != null) {
            tipoNegocio.value = user.value!!.tipoNegocio
            tamanoNegocio.value = user.value!!.tamanoNegocio
            preferenciasContenido.value = user.value!!.preferenciasContenido
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Preferencias")

        TextField(
            value = tipoNegocio.value,
            onValueChange = { tipoNegocio.value = it },
            label = { Text("Tipo de negocio") }
        )

        TextField(
            value = tamanoNegocio.value,
            onValueChange = { tamanoNegocio.value = it },
            label = { Text("Tamaño de negocio") }
        )

        TextField(
            value = preferenciasContenido.value,
            onValueChange = { preferenciasContenido.value = it },
            label = { Text("Preferencias de contenido") }
        )

        Button(
            onClick = {

                if (user.value != null) {

                    val updatedUser = user.value!!.copy(
                        tipoNegocio = tipoNegocio.value,
                        tamanoNegocio = tamanoNegocio.value,
                        preferenciasContenido = preferenciasContenido.value
                    )

                    userViewModel.updateUser(updatedUser)
                }
            }
        ) {
            Text("Guardar cambios")
        }

        Button(
            onClick = onBackClick
        ) {
            Text("Volver")
        }
    }
}

