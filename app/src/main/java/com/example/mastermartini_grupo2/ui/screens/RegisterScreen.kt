package com.example.mastermartini_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
fun RegisterScreen(
    onRegisterSuccess: () -> Unit
) {
    val userViewModel: UserViewModel = viewModel()

    val nombre = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val telefono = remember { mutableStateOf("") }
    val direccion = remember { mutableStateOf("") }
    val rutEmpresa = remember { mutableStateOf("") }
    val tipoNegocio = remember { mutableStateOf("") }
    val tamanoNegocio = remember { mutableStateOf("") }
    val preferenciasContenido = remember { mutableStateOf("") }

    val errores = remember { mutableStateOf(setOf<String>()) }

    var registroExitoso = remember { mutableStateOf(false) }

    if (registroExitoso.value) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("¡Registro exitoso!")

            Text("Tus datos fueron guardados correctamente.")

            Button(
                onClick = onRegisterSuccess
            ) {
                Text("Volver al inicio")
            }
        }

    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                label = {
                    Text("Nombre")
                },
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
                label = {
                    Text("Correo")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Correo"
                    )
                },
                isError = errores.value.contains("correo"),
                supportingText = {
                    if (errores.value.contains("correo")) {
                        Text("Ingresa un correo válido")
                    }
                }
            )

            TextField(
                value = telefono.value,
                onValueChange = {
                    telefono.value = it
                    errores.value = errores.value - "telefono"
                },
                label = {
                    Text("Teléfono")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Teléfono"
                    )
                },
                isError = errores.value.contains("telefono"),
                supportingText = {
                    if (errores.value.contains("telefono")) {
                        Text("Ingresa un teléfono válido")
                    }
                }
            )

            TextField(
                value = direccion.value,
                onValueChange = {
                    direccion.value = it
                    errores.value = errores.value - "direccion"
                },
                label = {
                    Text("Dirección")
                },
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
                label = {
                    Text("RUT de empresa")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = "RUT de empresa"
                    )
                },
                isError = errores.value.contains("rutEmpresa"),
                supportingText = {
                    if (errores.value.contains("rutEmpresa")) {
                        Text("Ingresa un RUT válido")
                    }
                }
            )

            TextField(
                value = tipoNegocio.value,
                onValueChange = {
                    tipoNegocio.value = it
                    errores.value = errores.value - "tipoNegocio"
                },
                label = {
                    Text("Tipo de negocio")
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
                label = {
                    Text("Tamaño de negocio")
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
                label = {
                    Text("Preferencias de contenido")
                },
                isError = errores.value.contains("preferenciasContenido"),
                supportingText = {
                    if (errores.value.contains("preferenciasContenido")) {
                        Text("Las preferencias son obligatorias")
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

                        registroExitoso.value = true

                    } else {

                        errores.value = validationErrors
                    }
                }
            ) {
                Text("Registrarse")
            }
        }
    }
}

