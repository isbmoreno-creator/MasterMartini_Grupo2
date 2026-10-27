package com.example.mastermartini_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.viewmodel.UserViewModel

@Composable
fun RegisterScreen() {

    val userViewModel: UserViewModel = viewModel()

    var mensajeError = remember { mutableStateOf(false) }

    val nombre = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val rutEmpresa = remember { mutableStateOf("") }
    val tipoNegocio = remember { mutableStateOf("") }
    val tamanoNegocio = remember { mutableStateOf("") }
    val preferenciasContenido = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Registro de usuario")

        TextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre") }
        )

        TextField(
            value = correo.value,
            onValueChange = { correo.value = it },
            label = { Text("Correo") }
        )

        TextField(
            value = telefono.value,
            onValueChange = { telefono.value = it },
            label = { Text("Teléfono") }
        )

        TextField(
            value = direccion.value,
            onValueChange = { direccion.value = it },
            label = { Text("Dirección") }
        )

        TextField(
            value = rutEmpresa.value,
            onValueChange = { rutEmpresa.value = it },
            label = { Text("RUT de empresa") }
        )

        TextField(
            value = tipoNegocio.value,
            onValueChange = { tipoNegocio.value = it },
            label = { Text("Tipo de negocio") }
        )

        TextField(
            value = tamanoNegocio.value,
            onValueChange = { tamanoNegocio.value = it },
            label = { Text("Tamaño del negocio") }
        )

        TextField(
            value = preferenciasContenido.value,
            onValueChange = { preferenciasContenido.value = it },
            label = { Text("Preferencias de contenido") }
        )

        Button(
            onClick = {

                val user = User(
                    nombre = nombre.value,
                    correo = correo.value,
                    telefono = telefono.value,
                    direccion = direccion.value,
                    rutEmpresa = rutEmpresa.value,
                    tipoNegocio = tipoNegocio.value,
                    tamanoNegocio = tamanoNegocio.value,
                    preferenciasContenido = preferenciasContenido.value
                )

                if (userViewModel.validateUser(user)) {
                    userViewModel.addUser(user)
                    mensajeError.value = false
                } else {
                    mensajeError.value = true
                }
            }
        ) {
            Text("Registrar")
        }

        if (mensajeError.value) {
            Text("Debes completar todos los campos")
        }
    }
}