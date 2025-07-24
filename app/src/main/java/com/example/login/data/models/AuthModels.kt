package com.example.login.data.models

import com.google.gson.annotations.SerializedName

// Modelo para la solicitud de registro (POST /register)
// Ahora requiere más campos.
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val age: Int,
    val password: String
)

// Modelo para la solicitud de login (POST /login)
// Simple: email y password.
data class LoginRequest(
    val email: String,
    val password: String
)

// Modelo para la respuesta de login (POST /login)
// Ahora solo devuelve el "token".
data class LoginResponse(
    val token: String
)

// Modelo para la respuesta de mensaje de éxito (ej. en registro)
// La API devuelve una cadena simple, pero la encapsulamos para consistencia si GSON lo necesita.
data class SuccessMessage(
    val message: String
)

// Modelo para los errores de la API (generalmente devueltos en caso de fallos)
// La API devuelve una cadena simple, pero la encapsulamos para consistencia.
data class ErrorMessage(
    val message: String
)