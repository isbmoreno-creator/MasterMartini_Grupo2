package com.example.mastermartini_grupo2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
fun ProfileScreen(
    onPreferencesClick: () -> Unit
) {

    val userViewModel: UserViewModel = viewModel()

    val user = remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        user.value = userViewModel.getUser()
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("Mi perfil")

        if (user.value != null) {

            Text("Nombre: ${user.value!!.nombre}")
            Text("Correo: ${user.value!!.correo}")
            Text("Teléfono: ${user.value!!.telefono}")
            Text("Dirección: ${user.value!!.direccion}")
            Text("RUT de empresa: ${user.value!!.rutEmpresa}")
            Text("Tipo de negocio: ${user.value!!.tipoNegocio}")
            Text("Tamaño de negocio: ${user.value!!.tamanoNegocio}")
            Text("Preferencias: ${user.value!!.preferenciasContenido}")

            Button(
                onClick = onPreferencesClick
            ) {
                Text("Editar preferencias")
            }

        } else {

            Text("No hay datos registrados")
        }
    }
}

