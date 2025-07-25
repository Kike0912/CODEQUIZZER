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
fun Categoria3(navController: NavController) {
    val preguntas = remember { obtenerPreguntasCategoria3() }
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
                                    //modifier = Modifier.padding(6.dp))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun obtenerPreguntasCategoria3(): List<Pregunta> = listOf(
    Pregunta(
        texto = "¿Qué comando SQL se usa para seleccionar datos de una tabla?",
        imagenId = R.drawable.c3p1,
        opciones = listOf("SELECT", "INSERT", "UPDATE", "DELETE"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando elimina filas de una tabla?",
        imagenId = R.drawable.c3p2,
        opciones = listOf("DROP", "DELETE", "REMOVE", "TRUNCATE"),
        indiceCorrecto = 1
    ),
    Pregunta(
        texto = "¿Cuál es la cláusula para filtrar resultados en SQL?",
        imagenId = R.drawable.c3p3,
        opciones = listOf("WHERE", "HAVING", "FILTER", "LIMIT"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando agrega filas nuevas a una tabla?",
        imagenId = R.drawable.c3p4,
        opciones = listOf("INSERT INTO", "ADD", "UPDATE", "CREATE"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué tipo de JOIN devuelve solo las filas que coinciden en ambas tablas?",
        imagenId = R.drawable.c3p5,
        opciones = listOf("INNER JOIN", "LEFT JOIN", "RIGHT JOIN", "FULL JOIN"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando muestra la estructura de una tabla?",
        imagenId = R.drawable.c3p6,
        opciones = listOf("DESCRIBE", "SHOW", "STRUCTURE", "TABLEINFO"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cuál es la función para contar filas en SQL?",
        imagenId = R.drawable.c3p7,
        opciones = listOf("COUNT()", "SUM()", "TOTAL()", "NUMBER()"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando elimina una tabla completa?",
        imagenId = R.drawable.c3p8,
        opciones = listOf("DROP TABLE", "DELETE TABLE", "REMOVE TABLE", "TRUNCATE TABLE"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se ordenan los resultados en SQL?",
        imagenId = R.drawable.c3p9,
        opciones = listOf("ORDER BY", "SORT BY", "GROUP BY", "FILTER BY"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando modifica datos existentes en una tabla?",
        imagenId = R.drawable.c3p10,
        opciones = listOf("UPDATE", "ALTER", "MODIFY", "CHANGE"),
        indiceCorrecto = 0
    )
)
