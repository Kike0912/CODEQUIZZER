package com.example.login.ui.screen

// Importaciones
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.login.R
import com.example.login.data.api.AuthApiService
import com.example.login.data.models.LoginRequest
import com.example.login.data.models.ErrorMessage
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
    var showError by remember { mutableStateOf(false) }
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
        // Área superior con logo centrado y fondo degradado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(primaryColor, darkBlue)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.size(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.padding(1.dp).fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bienvenido a CodeQuizzer",
            fontSize = 22.sp,
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
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val annotatedText = buildAnnotatedString {
                append("¿No tienes una cuenta aún? ")
                pushStringAnnotation(tag = "REGISTRATE", annotation = "register")
                withStyle(
                    style = SpanStyle(
                        color = primaryColor,
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Regístrate aquí")
                }
                pop()
            }

            ClickableText(
                text = annotatedText,
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "REGISTRATE", start = offset, end = offset)
                        .firstOrNull()?.let {
                            navController.navigate(it.item)
                        }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                errorMessage = null
                showError = false
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val loginRequest = LoginRequest(email = email, password = password)
                        val response = authApiService.login(loginRequest)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                response.body()?.let { loginResponse ->
                                    TokenManager.saveToken(context, loginResponse.token)
                                    loginSuccessful = true
                                }
                            } else {
                                val errorBody = response.errorBody()?.string()
                                val parsedError = try {
                                    val gson = Gson()
                                    gson.fromJson(errorBody, ErrorMessage::class.java)
                                } catch (e: Exception) {
                                    null
                                }
                                errorMessage = parsedError?.message ?: "Usuario o contraseña incorrectos."
                                showError = true
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            errorMessage = "Error de conexión: ${e.localizedMessage}. Verifica tu internet."
                            showError = true
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

        AnimatedVisibility(
            visible = showError && errorMessage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LaunchedEffect(showError) {
                delay(7000)
                showError = false
                errorMessage = null
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .background(Color(0xFFFFCDD2), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "❌ $errorMessage",
                    color = Color(0xFFB71C1C),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
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
