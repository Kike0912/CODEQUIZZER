package com.example.login.data.remote

// Importaciones
import com.example.login.data.api.AuthApiService // Usa tu nueva interfaz
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // ¡LA NUEVA URL BASE DE TU API!
    private const val BASE_URL = "https://tqw2zdzzjg.execute-api.us-east-1.amazonaws.com/prod/v1/" // ¡Esta es la URL correcta de tu nueva API!

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Nivel de log: BODY para ver request/response completo
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para conectar
        .readTimeout(30, TimeUnit.SECONDS)    // Tiempo de espera para leer respuesta
        .writeTimeout(30, TimeUnit.SECONDS)   // Tiempo de espera para escribir solicitud
        .build()

    // Instancia perezosa de tu servicio de API de autenticación
    val api: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Para convertir JSON a objetos Kotlin
            .build()
            .create(AuthApiService::class.java)
    }
}
