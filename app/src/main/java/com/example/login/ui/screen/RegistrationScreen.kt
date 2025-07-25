package com.example.login.ui.screen

// Importaciones
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.login.data.models.ErrorMessage // Esta sigue siendo necesaria para errores
import com.example.login.data.models.RegisterRequest
import com.example.login.data.remote.RetrofitClient
import com.google.gson.Gson

@Composable
fun RegistrationScreen(navController: NavHostController) {
    val primaryColor = Color(0xFF00A0B0)
    val darkBlue = Color(0xFF035D75)

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var registrationMessage by remember { mutableStateOf("") }
    var registrationSuccessful by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val authApiService = remember { RetrofitClient.api }

    LaunchedEffect(registrationSuccessful) {
        if (registrationSuccessful) {
            showSuccessDialog = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(primaryColor, darkBlue)
                    )
                ),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
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
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
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
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                registrationMessage = ""
                val ageInt = age.toIntOrNull()
                if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || ageInt == null || password.isBlank()) {
                    registrationMessage = "Todos los campos son requeridos y la edad debe ser un número."
                    return@Button
                }
                if (ageInt < 13) {
                    registrationMessage = "Debes tener al menos 13 años para registrarte."
                    return@Button
                }
                if (password.length < 8) {
                    registrationMessage = "La contraseña debe tener al menos 8 caracteres."
                    return@Button
                }

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val registerRequest = RegisterRequest(
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                            age = ageInt,
                            password = password
                        )
                        val response = authApiService.register(registerRequest)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                registrationSuccessful = true
                            } else {
                                val errorBody = response.errorBody()?.string()
                                val parsedError = try {
                                    val gson = Gson()
                                    gson.fromJson(errorBody, ErrorMessage::class.java)
                                } catch (e: Exception) {
                                    null
                                }
                                registrationMessage = parsedError?.message ?: "Error al registrar: ${response.code()}"
                                if (response.code() == 409) {
                                    registrationMessage = registrationMessage ?: "El correo electrónico ya está registrado."
                                } else if (response.code() == 400) {
                                    registrationMessage = registrationMessage ?: "Datos inválidos en el registro."
                                }
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            registrationMessage = "Error de conexión: ${e.localizedMessage}. Verifica tu internet."
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
                text = "Registrarse",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (registrationMessage.isNotEmpty() && !registrationSuccessful) {
            Text(
                text = registrationMessage,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )

        // Aquí el botón "¿Ya tienes cuenta? Iniciar Sesión" con ícono y estilo
        Row(
            Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.textButtonColors(contentColor = primaryColor)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "¿Ya tienes cuenta? Iniciar Sesión",
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // Diálogo éxito registro
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { /* No cerrar tocando fuera */ },
            title = { Text("Registro exitoso") },
            text = { Text("Tu cuenta ha sido creada correctamente. Pulsa el botón para iniciar sesión.") },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    navController.popBackStack()
                }) {
                    Text("Ir a Login")
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(navController = rememberNavController())
}
