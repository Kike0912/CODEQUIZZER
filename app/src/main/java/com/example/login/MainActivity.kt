package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.login.ui.screen.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "login") {

                composable("login") {
                    LoginScreen(navController = navController)
                }

                composable("register") {
                    RegistrationScreen(navController = navController)
                }

                // 👇 Bienvenida con parámetro username
                composable(
                    "welcome/{username}",
                    arguments = listOf(navArgument("username") { type = NavType.StringType })
                ) { backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username") ?: "Invitado"
                    WelcomeScreen(username = username, navController = navController)
                }

                // 👇 Categorías correctamente incluidas
                composable("categoria1") {
                    Categoria1(navController = navController)
                }
                composable("categoria2") {
                    Categoria2(navController = navController)
                }
                composable("categoria3") {
                    Categoria3(navController = navController)
                }
                composable("categoria4") {
                    Categoria4(navController = navController)
                }
                composable("categoria5") {
                    Categoria5(navController = navController)
                }
            }
        }
    }
}
