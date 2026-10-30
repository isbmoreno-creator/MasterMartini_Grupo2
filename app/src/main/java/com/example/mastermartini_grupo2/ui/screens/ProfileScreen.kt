package com.example.mastermartini_grupo2.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mastermartini_grupo2.model.User
import com.example.mastermartini_grupo2.viewmodel.UserViewModel

private val VerdeMasterMartini = Color(0xFF005B4F)
private val DoradoMasterMartini = Color(0xFFC9A500)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onPreferencesClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val userViewModel: UserViewModel = viewModel()

    val user = remember { mutableStateOf<User?>(null) }
    val fotoPerfil = remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        fotoPerfil.value = bitmap
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {
            cameraLauncher.launch(null)
        }
    }

    LaunchedEffect(Unit) {
        user.value = userViewModel.getUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mi perfil",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            Text(
                text = "Información personal",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            if (user.value != null) {

                if (fotoPerfil.value != null) {

                    Image(
                        bitmap = fotoPerfil.value!!.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                    )

                } else {

                    Text(
                        text = "Aún no tienes una foto de perfil",
                        fontSize = 14.sp
                    )
                }

                Button(
                    onClick = {
                        permissionLauncher.launch(
                            android.Manifest.permission.CAMERA
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DoradoMasterMartini
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Tomar foto"
                    )

                    Text(
                        text = "  Tomar foto de perfil"
                    )
                }

                Text(
                    text = "Nombre",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.nombre,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Correo",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.correo,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Teléfono",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.telefono,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Dirección",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.direccion,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "RUT de empresa",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.rutEmpresa,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Tipo de negocio",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.tipoNegocio,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Tamaño de negocio",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.tamanoNegocio,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Preferencias de contenido",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = user.value!!.preferenciasContenido,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = onPreferencesClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeMasterMartini
                    )
                ) {
                    Text("Editar preferencias")
                }

            } else {

                Text(
                    text = "No hay datos registrados.",
                    fontSize = 16.sp
                )
            }
        }
    }
}

