package com.example.mastermartini_grupo2.ui.theme.components


import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mastermartini_grupo2.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

private val VerdeRecipe = Color(0xFF005B4F)
private val DoradoRecipe = Color(0xFFC9A500)

@Composable
fun AddRecipeDialog(
    onAgregar: (Recipe) -> Unit,
    onCerrar: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var imagenPath by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf("") }
    var guardando by remember { mutableStateOf(false) }

    val selectorImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        if (uri != null) {
            guardando = true

            scope.launch {
                val ruta = copiarImagen(context, uri)

                imagenPath = ruta
                guardando = false
            }
        }
    }

    AlertDialog(
        onDismissRequest = onCerrar,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = "Agregar receta",
                color = VerdeRecipe,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de la receta") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = {
                        Text("Ingredientes y preparación")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )

                OutlinedTextField(
                    value = tiempo,
                    onValueChange = { tiempo = it },
                    label = { Text("Tiempo estimado") },
                    placeholder = { Text("Ej: 45 minutos") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        selectorImagen.launch("image/*")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VerdeRecipe,
                        contentColor = DoradoRecipe
                    )
                ) {
                    Text("Seleccionar fotografía")
                }

                if (imagenPath != null) {
                    RecipePhoto(
                        imagePath = imagenPath,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }

                if (guardando) {
                    Text("Cargando fotografía...")
                }

                if (error.isNotBlank()) {
                    Text(
                        text = error,
                        color = Color.Red
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when {
                        nombre.isBlank() -> {
                            error = "Ingresa el nombre de la receta."
                        }

                        descripcion.isBlank() -> {
                            error = "Ingresa la descripción."
                        }

                        tiempo.isBlank() -> {
                            error = "Ingresa el tiempo estimado."
                        }

                        guardando -> {
                            error = "Espera a que termine de cargar la fotografía."
                        }

                        else -> {
                            val nuevaReceta = Recipe(
                                name = nombre.trim(),
                                description = descripcion.trim(),
                                estimatedTime = tiempo.trim(),
                                imagePath = imagenPath
                            )

                            onAgregar(nuevaReceta)
                        }
                    }
                },
                enabled = !guardando,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeRecipe,
                    contentColor = DoradoRecipe
                )
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCerrar
            ) {
                Text(
                    text = "Cancelar",
                    color = VerdeRecipe
                )
            }
        }
    )
}

private suspend fun copiarImagen(
    context: Context,
    uri: Uri
): String? {
    return withContext(Dispatchers.IO) {
        try {
            val archivo = File(
                context.filesDir,
                "receta_${System.currentTimeMillis()}.jpg"
            )

            context.contentResolver.openInputStream(uri)?.use { input ->
                archivo.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            if (archivo.exists() && archivo.length() > 0) {
                archivo.absolutePath
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

