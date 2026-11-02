package com.example.mastermartini_grupo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mastermartini_grupo2.ui.screens.HomeScreen
import com.example.mastermartini_grupo2.ui.screens.ProfileScreen
import com.example.mastermartini_grupo2.ui.screens.RegisterScreen
import com.example.mastermartini_grupo2.ui.theme.MasterMartini_Grupo2Theme
import com.example.mastermartini_grupo2.ui.screens.PreferencesScreen
import com.example.mastermartini_grupo2.ui.screens.LoginScreen
import com.example.mastermartini_grupo2.viewmodel.UserViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mastermartini_grupo2.ui.screens.MainScreen
import com.example.mastermartini_grupo2.ui.screens.recipes.RecipeListScreen
import com.example.mastermartini_grupo2.ui.screens.recipes.ProductListScreen
import com.example.mastermartini_grupo2.ui.screens.recipes.ServiceListScreen
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MasterMartini_Grupo2Theme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        // HOME
        composable("home") {
            HomeScreen(
                onRegisterClick = {
                    navController.navigate("register")
                },
                onProfileClick = {
                    navController.navigate("profile")
                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }



        composable("preferences") {
            PreferencesScreen(
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        // REGISTRO
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("login") {
            val userViewModel: UserViewModel = viewModel()

            LoginScreen(
                onLoginClick = { correo, contrasena, resultado ->
                    userViewModel.validarLogin(correo, contrasena, resultado)
                },
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }


        composable("profile") {
            ProfileScreen(
                onPreferencesClick = {
                    navController.navigate("preferences")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("recipes") {
            RecipeListScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("products") {

            ProductListScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("services") {
            ServiceListScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("main") {
            MainScreen(
                onProfileClick = {
                    navController.navigate("profile")
                },
                onRecipesClick = {
                    navController.navigate("recipes")
                },
                onProductsClick = {
                    navController.navigate("products")
                },
                onServicesClick = {
                    navController.navigate("services")
                }
            )
        }
    }
}
