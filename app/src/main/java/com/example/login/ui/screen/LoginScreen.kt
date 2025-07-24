package com.example.login.ui.screen

// Importaciones
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Importaciones para corrutinas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Importaciones de tus clases de datos y API (actualizadas)
import com.example.login.data.api.AuthApiService
import com.example.login.data.models.LoginRequest // Nuevo
import com.example.login.data.models.ErrorMessage // Nuevo
import com.example.login.data.remote.RetrofitClient
import com.example.login.utils.TokenManager
import com.google.gson.Gson

@Composable
fun LoginScreen(navController: NavHostController) {
    val primaryColor = Color(0xFF00A0B0)
    val darkBlue = Color(0xFF035D75)
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var loginSuccessful by remember { mutableStateOf(false) }

    val authApiService = remember { RetrofitClient.api }

    LaunchedEffect(loginSuccessful) {
        if (loginSuccessful) {
            navController.navigate("welcome/${email}") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

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
                    .clickable { /* Acción de regreso, si es aplicable en Login, usualmente se cierra la app */ }
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
            horizontalArrangement = Arrangement.Center
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
                errorMessage = null // Limpiar mensajes de error anteriores
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Crear el nuevo LoginRequest
                        val loginRequest = LoginRequest(email = email, password = password)
                        val response = authApiService.login(loginRequest) // Llamada a la API

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                response.body()?.let { loginResponse ->
                                    TokenManager.saveToken(context, loginResponse.token) // Guardar solo el token
                                    loginSuccessful = true // Activar la navegación
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                val parsedError = try {
                                    val gson = Gson()
                                    gson.fromJson(errorBody, ErrorMessage::class.java)
                                } catch (e: Exception) {
                                    null
                                }
                                errorMessage = parsedError?.message ?: "Error de login: ${response.code()}"
                                if (response.code() == 401) {
                                    errorMessage = errorMessage ?: "Credenciales inválidas. Intenta de nuevo."
                                } else {

                                }
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            errorMessage = "Error de conexión: ${e.localizedMessage}. Verifica tu internet."
                        }
                    }
                }
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

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}