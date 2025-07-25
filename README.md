# QuizApp - Aplicación Educativa de Quizzes con Autenticación

![QuizApp Logo](app/src/main/res/drawable/logo.png)

---

## 📌 Descripción General

QuizApp es una aplicación Android desarrollada con **Kotlin** y **Jetpack Compose** que permite a los usuarios autenticarse mediante un sistema de login y registro con conexión a una API REST. 

La app ofrece cinco categorías de quizzes educativos para fortalecer conocimientos en tecnologías como Git, APIs REST, SQL, Kotlin y Laravel PHP.

---

## 🛠️ Tecnologías y Herramientas

- **Lenguaje:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Navegación:** Navigation Compose
- **Red:** Retrofit + Gson para consumo API REST
- **Concurrencia:** Coroutines para llamadas asíncronas
- **Persistencia:** Manejo de token con SharedPreferences (TokenManager)
- **Gestión de estado:** StateFlow en ViewModel

---

## 📂 Estructura del Proyecto
com.example.login
├── data
│   ├── api
│   │   └── AuthApiService.kt           # Definición de endpoints para login/registro
│   ├── models
│   │   └── AuthModels.kt                # Clases de datos: LoginRequest, LoginResponse, ErrorMessage
│   └── remote
│       └── RetrofitClient.kt            # Configuración Retrofit
├── ui
│   └── screen
│       ├── LoginScreen.kt               # Pantalla de login con manejo de errores y navegación
│       ├── RegistrationScreen.kt       # Pantalla de registro
│       ├── WelcomeScreen.kt            # Pantalla principal con categorías y logout
│       ├── Categoria1.kt               # Pantallas de quizzes por categoría (1 a 5)
│       └── ...
├── utils
│   └── TokenManager.kt                  # Almacenamiento seguro del token JWT
├── viewmodel
│   └── UserViewModel.kt                 # ViewModel para manejo del estado del usuario
└── MainActivity.kt                      # Entry point y configuración NavHost
