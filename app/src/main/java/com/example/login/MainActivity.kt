package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

@Composable
fun LoginScreen(navController: NavHostController) {
    val primaryColor = Color(0xFF00A0B0)
    val darkBlue = Color(0xFF035D75)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(primaryColor, darkBlue)
                    )
                )
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { /* Acción de regreso */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bienvenido",
            fontSize = 26.sp,
            color = darkBlue,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center // ¡CAMBIO AQUI!
        ) {
            Text(
                text = "Registrate aqui",
                color = primaryColor,
                fontSize = 14.sp,
                modifier = Modifier.clickable { navController.navigate("register") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("welcome/${email}")
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = "Iniciar sesión",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}

@Composable
fun WelcomeScreen(username: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome, $username!",
            fontSize = 28.sp,
            color = Color(0xFF00796B)
        )
    }
}

@Composable
fun RegistrationScreen(navController: NavHostController) {
    val primaryColor = Color(0xFF00A0B0) // Colores consistentes con LoginScreen
    val darkBlue = Color(0xFF035D75)

    // Estados para cada campo del formulario de registro
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") } // Cambiado de email a username
    var age by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Puedes añadir un estado para el mensaje de éxito/error del registro
    var registrationMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            // Agregamos scroll para asegurar que todos los campos sean visibles en pantallas pequeñas
            .verticalScroll(rememberScrollState()) // NECESARIO: Importa rememberScrollState y verticalScroll
    ) {
        // --- Header con gradiente y botón de retroceso ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp) // Un poco más pequeño que en login para dar espacio a más campos
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(primaryColor, darkBlue)
                    )
                ),
            contentAlignment = Alignment.TopStart // Alinear el botón de regreso arriba a la izquierda
        ) {
            IconButton(
                onClick = { navController.popBackStack() }, // Vuelve a la pantalla anterior
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Text(
                text = "Crear Cuenta",
                fontSize = 26.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center) // Centra el texto dentro del Box
                    .padding(top = 16.dp) // Pequeño padding para ajustar
            )
        }
        // --- FIN Header ---

        Spacer(modifier = Modifier.height(24.dp))

        // --- Campos del formulario ---
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Nombre") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = { Text("Apellido") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = username, // Ahora es nombre de usuario
            onValueChange = { username = it },
            placeholder = { Text("Nombre de usuario") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            placeholder = { Text("Edad") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Contraseña") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            }
        )
        // --- FIN Campos del formulario ---

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Registrarse
        Button(
            onClick = {
                // TODO: Aquí va la lógica de registro (llamada a la API)
                // Usar CoroutineScope(Dispatchers.IO).launch {...} y RetrofitClient.api.register
                // Puedes construir tu objeto RegisterRequest así:
                // val registerData = RegisterRequest(
                //     name = name,
                //     lastName = lastName,
                //     username = username,
                //     age = age.toIntOrNull() ?: 0, // Convertir edad a Int, manejar posible error
                //     password = password
                // )
                // val response = RetrofitClient.api.register(registerData)
                // if (response.isSuccessful) {
                //     registrationMessage = "Registro exitoso. Inicia sesión."
                //     navController.popBackStack() // Volver al login
                // } else {
                //     registrationMessage = "Error en el registro: ${response.code()}"
                // }

                // Por ahora, solo simula el regreso al login
                registrationMessage = "Simulando registro exitoso..."
                navController.popBackStack() // Vuelve al login después de un registro "simulado"
            },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = "Registrarse",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de registro (éxito/error)
        if (registrationMessage.isNotEmpty()) {
            Text(
                text = registrationMessage,
                color = if (registrationMessage.contains("exitoso")) primaryColor else Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Divisor horizontal
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        // Enlace para volver al login (redundante con el botón de atrás, pero puede ser útil)
        Row(
            Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "¿Ya tienes cuenta? Iniciar Sesión",
                color = primaryColor,
                fontSize = 14.sp,
                modifier = Modifier.clickable { navController.popBackStack() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp)) // Espacio al final
    }
}

// Para el Preview en Android Studio
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(navController = rememberNavController())
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}