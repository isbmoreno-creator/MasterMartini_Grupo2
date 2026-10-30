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

        composable("home") {
            HomeScreen(
                onRegisterClick = {
                    navController.navigate("register")
                },
                onProfileClick = {
                    navController.navigate("profile")
                }
            )
        }
        composable("preferences") {
            PreferencesScreen(
                onSaveSuccess = {
                    navController.popBackStack()
                    navController.popBackStack()
                }
            )
        }

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

    }
}