package com.example.mastermartini_grupo2.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable

import com.example.mastermartini_grupo2.model.Product
import com.example.mastermartini_grupo2.ui.theme.VerdeMasterMartini
import com.example.mastermartini_grupo2.ui.theme.DoradoMasterMartini

@Composable
fun ProductCard(product: Product) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column {

            // Fotografía del producto
            Image(
                painter = painterResource(
                    id = product.image
                ),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            // Información del producto
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdeMasterMartini
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = product.price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = DoradoMasterMartini
                )
            }
        }
    }
}
