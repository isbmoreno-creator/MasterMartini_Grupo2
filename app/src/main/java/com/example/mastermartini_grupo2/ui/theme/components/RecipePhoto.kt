package com.example.mastermartini_grupo2.ui.theme.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun RecipePhoto(
    imagePath: String?,
    modifier: Modifier = Modifier,
    showPlaceholder: Boolean = true
) {
    val bitmap = remember(imagePath) {
        imagePath?.let { path ->
            BitmapFactory.decodeFile(path)?.asImageBitmap()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = "Fotografía de la receta",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else if (showPlaceholder) {
            Text(
                text = "🍽️",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
