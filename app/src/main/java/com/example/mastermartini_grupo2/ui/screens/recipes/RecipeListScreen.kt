package com.example.mastermartini_grupo2.ui.screens.recipes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.mastermartini_grupo2.model.Recipe
import com.example.mastermartini_grupo2.ui.theme.VerdeMasterMartini
import com.example.mastermartini_grupo2.ui.theme.DoradoMasterMartini
import com.example.mastermartini_grupo2.ui.theme.components.RecipeCard
import com.example.mastermartini_grupo2.ui.theme.components.AddRecipeDialog
import com.example.mastermartini_grupo2.viewmodel.RecipeViewModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    onBackClick: () -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {

    // Obtener las recetas guardadas
    val recipes by recipeViewModel.recipes.collectAsState()

    var search by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    var selectedRecipe by remember {
        mutableStateOf<Recipe?>(null)
    }

    BackHandler(
        enabled = selectedRecipe != null
    ) {
        selectedRecipe = null
    }

    val currentRecipe = selectedRecipe

    if (currentRecipe != null) {

        RecipeDetailScreen(
            recipe = currentRecipe,
            onBack = {
                selectedRecipe = null
            }
        )

    } else {

        val filteredRecipes = recipes.filter {
            it.name.contains(search, ignoreCase = true)
        }

        Scaffold(

            topBar = {
                TopAppBar(
                    title = {
                        Text("Mis recetas")
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = VerdeMasterMartini,
                        titleContentColor = Color.White
                    )
                )
            },

            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showDialog = true
                    },
                    containerColor = VerdeMasterMartini,
                    contentColor = DoradoMasterMartini
                ) {
                    Text(
                        text = "+",
                        fontSize = 28.sp
                    )
                }
            }

        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    label = {
                        Text("Buscar receta")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Mis recetas (${filteredRecipes.size})",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdeMasterMartini
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (filteredRecipes.isEmpty()) {

                    Text(
                        text = if (search.isNotBlank()) {
                            "No se encontraron recetas."
                        } else {
                            "Todavía no tienes recetas. Pulsa + para agregar una."
                        }
                    )

                } else {

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(bottom = 90.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(
                            items = filteredRecipes,
                            key = { it.id }
                        ) { recipe ->

                            RecipeCard(
                                recipe = recipe,
                                onClick = {
                                    selectedRecipe = recipe
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Ventana para agregar una receta
    if (showDialog) {

        AddRecipeDialog(

            onAgregar = { newRecipe ->
                recipeViewModel.addRecipe(newRecipe)

                showDialog = false
            },

            onCerrar = {
                showDialog = false
            },

            onValidate = { recipe ->
                recipeViewModel.getRecipeValidationErrors(recipe)
            }
        )
    }
}
