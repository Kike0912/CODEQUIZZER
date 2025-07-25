package com.example.login.ui.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.example.login.R

@Composable
fun Categoria5(navController: NavController) {
    val preguntas = remember { obtenerPreguntasCategoria5() }
    var indiceActual by remember { mutableStateOf(0) }
    var aciertos by remember { mutableStateOf(0) }
    var respuestaSeleccionada by remember { mutableStateOf<Int?>(null) }
    var colorFondo by remember { mutableStateOf(Color(0xFFF5F5F5)) }
    var mostrarFinal by remember { mutableStateOf(false) }

    val pregunta = preguntas.getOrNull(indiceActual)

    LaunchedEffect(respuestaSeleccionada) {
        if (respuestaSeleccionada != null) {
            delay(1000)
            if (indiceActual == preguntas.lastIndex) {
                mostrarFinal = true
            } else {
                indiceActual++
                respuestaSeleccionada = null
                colorFondo = Color(0xFFF5F5F5)
            }
        }
    }

    if (mostrarFinal) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("¡Juego terminado!", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.trofeo),
                contentDescription = "Trofeo de victoria",
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    //.padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Aciertos: $aciertos de ${preguntas.size}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("inicio") },
                modifier = Modifier.width(200.dp)
                    .height(50.dp)) {
                Text("Volver al menú",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp))
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(colorFondo)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Pregunta ${indiceActual + 1} / ${preguntas.size}",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                modifier = Modifier.padding(8.dp))
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ){
                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = pregunta?.texto ?: "No hay preguntas",
                        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary),
                        textAlign = TextAlign.Center,
                        //modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (pregunta != null) {
                        Image(
                            painter = painterResource(id = pregunta.imagenId),
                            contentDescription = "Imagen de la pregunta",
                            modifier = Modifier
                                .height(180.dp)
                                .fillMaxWidth()
                                //.padding(16.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        pregunta.opciones.forEachIndexed { index, textoOpcion ->
                            Button(
                                onClick = {
                                    if (respuestaSeleccionada == null) {
                                        respuestaSeleccionada = index
                                        if (index == pregunta.indiceCorrecto) {
                                            aciertos++
                                            colorFondo = Color(0xFFB9FBC0) // verde
                                        } else {
                                            colorFondo = Color(0xFFFFC2C2) // rojo
                                        }
                                    }
                                },
                                enabled = respuestaSeleccionada == null,
                                modifier = Modifier.width(275.dp).padding(vertical = 8.dp)
                            ) {
                                Text(text = textoOpcion,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 15.sp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun obtenerPreguntasCategoria5(): List<Pregunta> = listOf(
    Pregunta(
        texto = "¿Qué comando se usa para crear un nuevo proyecto Laravel?",
        imagenId = R.drawable.c4p1,
        opciones = listOf("laravel new proyecto", "php new proyecto", "composer create proyecto", "php artisan new proyecto"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cuál archivo define las rutas web en Laravel?",
        imagenId = R.drawable.c4p2,
        opciones = listOf("routes/web.php", "routes/api.php", "config/routes.php", "app/Http/routes.php"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando ejecuta las migraciones en Laravel?",
        imagenId = R.drawable.c4p3,
        opciones = listOf("php artisan migrate", "php artisan migrate:run", "php migrate", "composer migrate"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué ORM usa Laravel por defecto?",
        imagenId = R.drawable.c4p4,
        opciones = listOf("Eloquent", "Doctrine", "Propel", "RedBean"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se llama la carpeta donde se colocan los controladores?",
        imagenId = R.drawable.c4p5,
        opciones = listOf("app/Http/Controllers", "app/Controllers", "app/Http", "app/Controllers/Web"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando crea un nuevo controlador?",
        imagenId = R.drawable.c4p6,
        opciones = listOf("php artisan make:controller NombreController", "php artisan create:controller", "php make:controller", "composer make:controller"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cuál archivo se usa para configurar la base de datos?",
        imagenId = R.drawable.c4p7,
        opciones = listOf(".env", "config/database.php", "config/app.php", "database/config.php"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando limpia la caché de la aplicación?",
        imagenId = R.drawable.c4p8,
        opciones = listOf("php artisan cache:clear", "php artisan clear:cache", "php clear cache", "composer cache:clear"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se llaman las vistas en Laravel?",
        imagenId = R.drawable.c4p9,
        opciones = listOf("Blade templates", "PHP templates", "Twig templates", "Smarty templates"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando genera un modelo con migración?",
        imagenId = R.drawable.c4p10,
        opciones = listOf("php artisan make:model Nombre -m", "php artisan create:model", "php artisan model:create", "composer make:model"),
        indiceCorrecto = 0
    )
)

