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
fun Categoria4(navController: NavController) {
    val preguntas = remember { obtenerPreguntasCategoria4() }
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

fun obtenerPreguntasCategoria4(): List<Pregunta> = listOf(
    Pregunta(
        texto = "¿Cómo se declara una variable inmutable en Kotlin?",
        imagenId = R.drawable.c5p1,
        opciones = listOf("val", "var", "let", "const"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué palabra clave se usa para definir una función en Kotlin?",
        imagenId = R.drawable.c5p2,
        opciones = listOf("fun", "function", "def", "func"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se escribe un comentario de una sola línea en Kotlin?",
        imagenId = R.drawable.c5p3,
        opciones = listOf("// comentario", "/* comentario */", "# comentario", "<!-- comentario -->"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cuál es el tipo para números enteros en Kotlin?",
        imagenId = R.drawable.c5p4,
        opciones = listOf("Int", "Integer", "Number", "Long"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué función se usa para imprimir en consola?",
        imagenId = R.drawable.c5p5,
        opciones = listOf("println()", "print()", "echo()", "write()"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se crea una clase en Kotlin?",
        imagenId = R.drawable.c5p6,
        opciones = listOf("class MiClase {}", "def MiClase {}", "object MiClase {}", "struct MiClase {}"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué palabra clave permite crear una función de extensión?",
        imagenId = R.drawable.c5p7,
        opciones = listOf("fun", "extend", "override", "extendfun"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se define un parámetro con valor por defecto en una función?",
        imagenId = R.drawable.c5p8,
        opciones = listOf("fun f(x: Int = 0)", "fun f(x: Int?)", "fun f(x: Int!)", "fun f(x: Int)"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué operador se usa para la comparación de igualdad?",
        imagenId = R.drawable.c5p9,
        opciones = listOf("==", "=", "equals()", "==="),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se declara una función lambda?",
        imagenId = R.drawable.c5p10,
        opciones = listOf("{ x -> x * 2 }", "(x) => x * 2", "lambda x: x * 2", "func(x) { return x * 2 }"),
        indiceCorrecto = 0
    )
)