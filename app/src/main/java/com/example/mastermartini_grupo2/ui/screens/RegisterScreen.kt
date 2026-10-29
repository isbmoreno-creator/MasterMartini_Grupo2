package com.example.mastermartini_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.Icon

@Composable
fun RegisterScreen() {

    val userViewModel: UserViewModel = viewModel()

    val errores = remember { mutableStateOf(setOf<String>()) }

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
            onValueChange = {
                nombre.value = it
                errores.value = errores.value - "nombre"
            },
            label = { Text("Nombre") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Nombre"
                )
            },
            isError = errores.value.contains("nombre"),
            supportingText = {
                if (errores.value.contains("nombre")) {
                    Text("El nombre es obligatorio")
                }
            }
        )

        TextField(
            value = correo.value,
            onValueChange = {
                correo.value = it
                errores.value = errores.value - "correo"
            },
            label = { Text("Correo") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Correo"
                )
            },
            isError = errores.value.contains("correo"),
            supportingText = {
                if (errores.value.contains("correo")) {
                    Text("El correo es obligatorio")
                }
            }
        )

        TextField(
            value = telefono.value,
            onValueChange = {
                telefono.value = it
                errores.value = errores.value - "telefono"
            },
            label = { Text("Teléfono") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Teléfono"
                )
            },
            isError = errores.value.contains("telefono"),
            supportingText = {
                if (errores.value.contains("telefono")) {
                    Text("El teléfono es obligatorio")
                }
            }
        )

        TextField(
            value = direccion.value,
            onValueChange = {
                direccion.value = it
                errores.value = errores.value - "direccion"
            },
            label = { Text("Dirección") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Dirección"
                )
            },
            isError = errores.value.contains("direccion"),
            supportingText = {
                if (errores.value.contains("direccion")) {
                    Text("La dirección es obligatoria")
                }
            }
        )

        TextField(
            value = rutEmpresa.value,
            onValueChange = {
                rutEmpresa.value = it
                errores.value = errores.value - "rutEmpresa"
            },
            label = { Text("RUT de empresa") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "RUT de empresa"
                )
            },
            isError = errores.value.contains("rutEmpresa"),
            supportingText = {
                if (errores.value.contains("rutEmpresa")) {
                    Text("El RUT de empresa es obligatorio")
                }
            }
        )

        TextField(
            value = tipoNegocio.value,
            onValueChange = {
                tipoNegocio.value = it
                errores.value = errores.value - "tipoNegocio"
            },
            label = { Text("Tipo de negocio") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "Tipo de negocio"
                )
            },
            isError = errores.value.contains("tipoNegocio"),
            supportingText = {
                if (errores.value.contains("tipoNegocio")) {
                    Text("El tipo de negocio es obligatorio")
                }
            }
        )

        TextField(
            value = tamanoNegocio.value,
            onValueChange = {
                tamanoNegocio.value = it
                errores.value = errores.value - "tamanoNegocio"
            },
            label = { Text("Tamaño de negocio") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = "Tamaño de negocio"
                )
            },
            isError = errores.value.contains("tamanoNegocio"),
            supportingText = {
                if (errores.value.contains("tamanoNegocio")) {
                    Text("El tamaño del negocio es obligatorio")
                }
            }
        )

        TextField(
            value = preferenciasContenido.value,
            onValueChange = {
                preferenciasContenido.value = it
                errores.value = errores.value - "preferenciasContenido"
            },
            label = { Text("Preferencias de contenido") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Preferencias de contenido"
                )
            },
            isError = errores.value.contains("preferenciasContenido"),
            supportingText = {
                if (errores.value.contains("preferenciasContenido")) {
                    Text("Las preferencias de contenido son obligatorias")
                }
            }
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

                val validationErrors =
                    userViewModel.getValidationErrors(user)

                if (validationErrors.isEmpty()) {
                    userViewModel.addUser(user)
                    errores.value = emptySet()
                } else {
                    errores.value = validationErrors
                }
            }
        ) {
            Text("Registrar")
        }
    }
}
