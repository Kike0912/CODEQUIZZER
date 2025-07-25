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

---

## 📖 Flujo de Usuario

1.  **Pantalla Login:** El usuario ingresa su email y contraseña. Se valida vía API REST. En caso de éxito, se guarda el token y se navega a la pantalla principal.
2.  **Pantalla Registro:** Permite crear una cuenta nueva con campos validados y registro en backend.
3.  **Pantalla Welcome:** Muestra saludo personalizado con el email de usuario, y las 5 categorías de quizzes disponibles.
4.  **Pantallas de Categorías:** Cada categoría contiene preguntas tipo quiz para poner a prueba conocimientos.
5.  **Cerrar sesión:** Desde la pantalla principal, el usuario puede cerrar sesión limpiando el token y regresando a login.

---

## 🧩 Componentes Clave

### ViewModel: `UserViewModel`

- Contiene un `MutableStateFlow` para el nombre de usuario (email).
- Provee métodos para actualizar el estado y compartirlo entre pantallas.

```kotlin
class UserViewModel : ViewModel() {
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    fun setUsername(name: String) {
        _username.value = name
    }
}

# Screens UI

- **LoginScreen:**  
  Formulario con validación y manejo de errores, llama a la API, guarda token y actualiza ViewModel.

- **WelcomeScreen:**  
  Muestra nombre de usuario, categorías con animaciones, botón logout con confirmación.

- **CategoriaXScreen:**  
  Cada categoría con quizzes específicos.

---

## 🚀 Cómo Ejecutar

1. Clona el repositorio.  
2. Abre en Android Studio.  
3. Configura el endpoint de la API REST en `RetrofitClient.kt`.  
4. Ejecuta la app en un emulador o dispositivo físico.  
5. Regístrate o inicia sesión con credenciales válidas.

---

## 📋 Notas Importantes

- La app usa **Jetpack Compose** para UI moderna y reactiva.  
- Se sigue el patrón **MVVM** para separar lógica de presentación.  
- El token JWT se guarda localmente para manejar sesión.  
- La navegación entre pantallas usa **Navigation Compose** con paso de parámetros y gestión del backstack.  
- Se usan **coroutines** para llamadas de red asíncronas y evitar bloqueos en UI.

---

## 🤝 Contribuciones

Bienvenidas mejoras en:

- Nuevas categorías y preguntas para quizzes.  
- Mejoras en UX/UI y accesibilidad.  
- Gestión más avanzada de sesiones y almacenamiento seguro.  
- Integración con bases de datos locales para historial de quizzes.


