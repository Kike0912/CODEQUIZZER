package com.example.login.data.api

// Importaciones
import com.example.login.data.models.LoginRequest
import com.example.login.data.models.LoginResponse
import com.example.login.data.models.RegisterRequest
// import com.example.login.data.models.SuccessMessage // <-- Esta importación ya no será necesaria aquí para el registro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("register") // Nuevo endpoint para registro.
    suspend fun register(@Body request: RegisterRequest): Response<String> // <-- Cambiado a String

    @POST("login") // Nuevo endpoint para login.
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}