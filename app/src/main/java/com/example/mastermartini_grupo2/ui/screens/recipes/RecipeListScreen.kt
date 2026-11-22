
package com.example.mastermartini_grupo2.ui.screens.recipes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import com.example.mastermartini_grupo2.model.Recipe
import com.example.mastermartini_grupo2.model.ApiRecipe
import com.example.mastermartini_grupo2.ui.theme.VerdeMasterMartini
import com.example.mastermartini_grupo2.ui.theme.DoradoMasterMartini
import com.example.mastermartini_grupo2.ui.theme.components.RecipeCard
import com.example.mastermartini_grupo2.ui.theme.components.AddRecipeDialog
import com.example.mastermartini_grupo2.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    onBackClick: () -> Unit,
    recipeViewModel: RecipeViewModel = viewModel()
) {


    val recipes by recipeViewModel.recipes.collectAsState()


    val apiRecipes by recipeViewModel.apiRecipes.collectAsState()

    // ESTADOS DE CARGA Y ERROR
    val isLoading by recipeViewModel.isLoading.collectAsState()
    val error by recipeViewModel.error.collectAsState()


    var selectedTab by remember { mutableIntStateOf(0) }
    var search by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    var selectedRecipe by remember {
        mutableStateOf<Recipe?>(null)
    }

    var selectedApiRecipe by remember {
        mutableStateOf<ApiRecipe?>(null)
    }


    var editingLocalRecipe by remember {
        mutableStateOf<Recipe?>(null)
    }

    var editingApiRecipe by remember {
        mutableStateOf<ApiRecipe?>(null)
    }

    // ELIMINACIÓN
    var deletingLocalRecipe by remember {
        mutableStateOf<Recipe?>(null)
    }

    var deletingApiRecipe by remember {
        mutableStateOf<ApiRecipe?>(null)
    }

    // VOLVER DESDE EL DETALLE
    BackHandler(
        enabled = selectedRecipe != null || selectedApiRecipe != null
    ) {
        selectedRecipe = null
        selectedApiRecipe = null
    }

    // DETALLE DE RECETA PROPIA
    if (selectedRecipe != null) {

        RecipeDetailScreen(
            recipe = selectedRecipe!!,
            onBack = {
                selectedRecipe = null
            }
        )

        // DETALLE DE RECETA API
    } else if (selectedApiRecipe != null) {

        ApiRecipeDetailScreen(
            recipe = selectedApiRecipe!!,
            onBack = {
                selectedApiRecipe = null
            }
        )

    } else {

        // FILTRAR RECETAS
        val filteredRecipes = recipes.filter {
            it.name.contains(search, ignoreCase = true)
        }

        val filteredApiRecipes = apiRecipes.filter {
            it.name.contains(search, ignoreCase = true)
        }

        Scaffold(

            topBar = {

                TopAppBar(
                    title = {
                        Text("Recetas")
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

            // BOTÓN AGREGAR
            floatingActionButton = {

                if (selectedTab == 1) {

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
            }

        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                // PESTAÑAS
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Color.Transparent,
                    contentColor = VerdeMasterMartini
                ) {

                    Tab(
                        selected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                            search = ""
                        },
                        text = {
                            Text(
                                "Explorar recetas",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )

                    Tab(
                        selected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            search = ""
                        },
                        text = {
                            Text(
                                "Mis recetas",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // BUSCADOR
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

                Spacer(modifier = Modifier.height(18.dp))



                if (selectedTab == 0) {

                    Text(
                        text = "Explorar recetas (${filteredApiRecipes.size})",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeMasterMartini
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    when {

                        isLoading -> {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = VerdeMasterMartini
                                )
                            }
                        }

                        error != null && apiRecipes.isEmpty() -> {

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "No se pudieron cargar las recetas.",
                                    color = MaterialTheme.colorScheme.error
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = {
                                        recipeViewModel.loadRecipes()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = VerdeMasterMartini
                                    )
                                ) {
                                    Text("Reintentar")
                                }
                            }
                        }

                        filteredApiRecipes.isEmpty() -> {

                            Text(
                                text = if (search.isNotBlank()) {
                                    "No se encontraron recetas."
                                } else {
                                    "No hay recetas disponibles."
                                }
                            )
                        }

                        else -> {

                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(bottom = 90.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                items(
                                    items = filteredApiRecipes,
                                    key = { it.id }
                                ) { recipe ->

                                    Column {

                                        ApiRecipeCard(
                                            recipe = recipe,
                                            onClick = {
                                                selectedApiRecipe = recipe
                                            }
                                        )

                                        // BOTONES API
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {

                                            TextButton(
                                                onClick = {
                                                    editingApiRecipe = recipe
                                                }
                                            ) {
                                                Text(
                                                    "Editar",
                                                    color = VerdeMasterMartini
                                                )
                                            }

                                            TextButton(
                                                onClick = {
                                                    deletingApiRecipe = recipe
                                                }
                                            ) {
                                                Text(
                                                    "Eliminar",
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else {

                    Text(
                        text = "Mis recetas (${filteredRecipes.size})",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = VerdeMasterMartini
                    )

                    Spacer(modifier = Modifier.height(12.dp))

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

                                Column {

                                    RecipeCard(
                                        recipe = recipe,
                                        onClick = {
                                            selectedRecipe = recipe
                                        }
                                    )

                                    // BOTONES LOCALES
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {

                                        TextButton(
                                            onClick = {
                                                editingLocalRecipe = recipe
                                            }
                                        ) {
                                            Text(
                                                "Editar",
                                                color = VerdeMasterMartini
                                            )
                                        }

                                        TextButton(
                                            onClick = {
                                                deletingLocalRecipe = recipe
                                            }
                                        ) {
                                            Text(
                                                "Eliminar",
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


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

        editingLocalRecipe?.let { recipe ->

            EditLocalRecipeDialog(
                recipe = recipe,

                onDismiss = {
                    editingLocalRecipe = null
                },

                onSave = { updatedRecipe ->

                    recipeViewModel.updateRecipe(updatedRecipe)
                    editingLocalRecipe = null
                }
            )
        }

        editingApiRecipe?.let { recipe ->

            EditApiRecipeDialog(
                recipe = recipe,

                onDismiss = {
                    editingApiRecipe = null
                },

                onSave = { updatedRecipe ->

                    recipeViewModel.updateApiRecipe(updatedRecipe)
                    editingApiRecipe = null
                }
            )
        }


        deletingLocalRecipe?.let { recipe ->

            AlertDialog(
                onDismissRequest = {
                    deletingLocalRecipe = null
                },

                title = {
                    Text("Eliminar receta")
                },

                text = {
                    Text(
                        "¿Seguro que deseas eliminar ${recipe.name}?"
                    )
                },

                confirmButton = {

                    TextButton(
                        onClick = {

                            recipeViewModel.deleteRecipe(recipe.id)
                            deletingLocalRecipe = null
                        }
                    ) {
                        Text(
                            "Eliminar",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            deletingLocalRecipe = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }


        deletingApiRecipe?.let { recipe ->

            AlertDialog(
                onDismissRequest = {
                    deletingApiRecipe = null
                },

                title = {
                    Text("Eliminar receta de la API")
                },

                text = {
                    Text(
                        "¿Deseas enviar la solicitud para eliminar ${recipe.name}?"
                    )
                },

                confirmButton = {

                    TextButton(
                        onClick = {

                            recipeViewModel.deleteApiRecipe(recipe.id)
                            deletingApiRecipe = null
                        }
                    ) {
                        Text(
                            "Eliminar",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {
                            deletingApiRecipe = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}




@Composable
fun ApiRecipeCard(
    recipe: ApiRecipe,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column {

            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = recipe.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdeMasterMartini
                )

                Text(
                    text = "${recipe.cuisine} · ${recipe.difficulty}",
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = "Preparación: ${recipe.prepTimeMinutes} min",
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = "⭐ ${recipe.rating} (${recipe.reviewCount} opiniones)",
                    color = DoradoMasterMartini
                )
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiRecipeDetailScreen(
    recipe: ApiRecipe,
    onBack: () -> Unit
) {

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Detalle de receta")
                },

                navigationIcon = {

                    IconButton(onClick = onBack) {

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
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            AsyncImage(
                model = recipe.image,
                contentDescription = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Text(
                text = recipe.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeMasterMartini
            )

            Text("Cocina: ${recipe.cuisine}")
            Text("Dificultad: ${recipe.difficulty}")
            Text("Preparación: ${recipe.prepTimeMinutes} minutos")
            Text("Cocción: ${recipe.cookTimeMinutes} minutos")
            Text("Porciones: ${recipe.servings}")

            HorizontalDivider()

            Text(
                text = "Ingredientes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeMasterMartini
            )

            recipe.ingredients.forEach { ingredient ->
                Text("• $ingredient")
            }

            HorizontalDivider()

            Text(
                text = "Preparación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = VerdeMasterMartini
            )

            recipe.instructions.forEachIndexed { index, instruction ->

                Text(
                    text = "${index + 1}. $instruction",
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeMasterMartini,
                    contentColor = DoradoMasterMartini
                )
            ) {
                Text("Volver a las recetas")
            }
        }
    }
}




@Composable
fun EditLocalRecipeDialog(
    recipe: Recipe,
    onDismiss: () -> Unit,
    onSave: (Recipe) -> Unit
) {

    var name by remember(recipe.id) {
        mutableStateOf(recipe.name)
    }

    var description by remember(recipe.id) {
        mutableStateOf(recipe.description)
    }

    var estimatedTime by remember(recipe.id) {
        mutableStateOf(recipe.estimatedTime)
    }

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text("Editar receta")
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text("Nombre")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                    },
                    label = {
                        Text("Descripción")
                    }
                )

                OutlinedTextField(
                    value = estimatedTime,
                    onValueChange = {
                        estimatedTime = it
                    },
                    label = {
                        Text("Tiempo estimado")
                    },
                    singleLine = true
                )
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    onSave(
                        recipe.copy(
                            name = name,
                            description = description,
                            estimatedTime = estimatedTime
                        )
                    )
                },

                enabled = name.isNotBlank() &&
                        description.isNotBlank() &&
                        estimatedTime.isNotBlank()
            ) {
                Text("Guardar")
            }
        },

        dismissButton = {

            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}


@Composable
fun EditApiRecipeDialog(
    recipe: ApiRecipe,
    onDismiss: () -> Unit,
    onSave: (ApiRecipe) -> Unit
) {

    var name by remember(recipe.id) {
        mutableStateOf(recipe.name)
    }

    var cuisine by remember(recipe.id) {
        mutableStateOf(recipe.cuisine)
    }

    var difficulty by remember(recipe.id) {
        mutableStateOf(recipe.difficulty)
    }

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text("Editar receta de la API")
        },

        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text("Nombre")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = cuisine,
                    onValueChange = {
                        cuisine = it
                    },
                    label = {
                        Text("Cocina")
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = difficulty,
                    onValueChange = {
                        difficulty = it
                    },
                    label = {
                        Text("Dificultad")
                    },
                    singleLine = true
                )
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    onSave(
                        recipe.copy(
                            name = name,
                            cuisine = cuisine,
                            difficulty = difficulty
                        )
                    )
                },

                enabled = name.isNotBlank() &&
                        cuisine.isNotBlank() &&
                        difficulty.isNotBlank()
            ) {
                Text("Guardar")
            }
        },

        dismissButton = {

            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
