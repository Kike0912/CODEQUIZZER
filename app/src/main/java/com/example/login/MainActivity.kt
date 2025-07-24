package com.example.login

// Importaciones
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Importa tus pantallas desde el paquete 'ui'
import com.example.login.ui.screen.LoginScreen
import com.example.login.ui.screen.RegistrationScreen
import com.example.login.ui.screen.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(navController = navController)
                }
                composable("welcome/{username}") { backStackEntry ->
                    val username = backStackEntry.arguments?.getString("username") ?: ""
                    WelcomeScreen(username)
                }
                composable("register") {
                    RegistrationScreen(navController = navController)
                }
            }
        }
    }
}