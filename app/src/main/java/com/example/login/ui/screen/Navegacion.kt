package com.example.login.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navegacion() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome/{username}") {

        // 👇 Ruta con parámetro dinámico
        composable(
            route = "welcome/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Invitado"
            WelcomeScreen(username = username, navController = navController)
        }

        composable("categoria1") {
            Categoria1(navController)
        }
        composable("categoria2") {
            Categoria2(navController)
        }
        composable("categoria3") {
            Categoria3(navController)
        }
        composable("categoria4") {
            Categoria4(navController)
        }
        composable("categoria5") {
            Categoria5(navController)
        }
    }
}
