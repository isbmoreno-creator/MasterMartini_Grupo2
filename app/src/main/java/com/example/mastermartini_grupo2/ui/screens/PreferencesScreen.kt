package com.example.mastermartini_grupo2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.size
import androidx.compose.ui.text.style.TextAlign

private val VerdeMasterMartini = Color(0xFF005B4F)
private val DoradoMasterMartini = Color(0xFFC9A500)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen(
    onSaveSuccess: () -> Unit
) {
    val userViewModel: UserViewModel = viewModel()

    val user = remember { mutableStateOf<User?>(null) }

    val tipoNegocio = remember { mutableStateOf("") }
    val tamanoNegocio = remember { mutableStateOf("") }
    val preferenciasContenido = remember { mutableStateOf("") }

    val datosGuardados = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        user.value = userViewModel.getUser()

        if (user.value != null) {
            tipoNegocio.value = user.value!!.tipoNegocio
            tamanoNegocio.value = user.value!!.tamanoNegocio
            preferenciasContenido.value =
                user.value!!.preferenciasContenido
        }
    }

    LaunchedEffect(datosGuardados.value) {
        if (datosGuardados.value) {
            delay(1500)
            onSaveSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Preferencias",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    if (!datosGuardados.value) {
                        IconButton(
                            onClick = onSaveSuccess
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VerdeMasterMartini,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        if (datosGuardados.value) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                AnimatedVisibility(
                    visible = datosGuardados.value,
                    enter = fadeIn() + slideInVertically(),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Datos guardados",
                            tint = VerdeMasterMartini,
                            modifier = Modifier.size(56.dp)
                        )

                        Text(
                            text = "¡Datos guardados correctamente!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Tus preferencias fueron actualizadas.",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Personaliza tu experiencia",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Actualiza la información de tu negocio y tus preferencias de contenido.",
                    fontSize = 16.sp
                )

                TextField(
                    value = tipoNegocio.value,
                    onValueChange = {
                        tipoNegocio.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Tipo de negocio")
                    }
                )

                TextField(
                    value = tamanoNegocio.value,
                    onValueChange = {
                        tamanoNegocio.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Tamaño de negocio")
                    }
                )

                TextField(
                    value = preferenciasContenido.value,
                    onValueChange = {
                        preferenciasContenido.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Preferencias de contenido")
                    }
                )

                Button(
                    onClick = {

                        if (user.value != null) {

                            val updatedUser = user.value!!.copy(
                                tipoNegocio = tipoNegocio.value,
                                tamanoNegocio = tamanoNegocio.value,
                                preferenciasContenido =
                                    preferenciasContenido.value
                            )

                            userViewModel.updateUser(updatedUser)

                            user.value = updatedUser

                            datosGuardados.value = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DoradoMasterMartini
                    )
                ) {
                    Text("Guardar cambios")
                }
            }
        }
    }
}

