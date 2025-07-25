package com.example.login.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.login.R
import kotlinx.coroutines.delay

data class Pregunta(
    val texto: String,
    val imagenId: Int,
    val opciones: List<String>,
    val indiceCorrecto: Int
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Categoria1(navController: NavController) {
    val preguntas = remember { obtenerPreguntasCategoria1() }
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
        val total = preguntas.size
        val progreso = aciertos.toFloat() / total
        val animatedProgress by animateFloatAsState(
            targetValue = progreso,
            animationSpec = tween(1000),
            label = "Progreso animado"
        )

        val (mensajeFinal, colorBarra) = when {
            aciertos < 3 -> "Debes seguir practicando" to Color(0xFFFF6B6B)
            aciertos in 3..7 -> "¡Vas mejorando, sigue así!" to Color(0xFFFFC107)
            else -> "¡Lo hiciste genial, felicidades!" to Color(0xFF4CAF50)
        }

        AnimatedVisibility(
            visible = mostrarFinal,
            enter = fadeIn(tween(700)) + scaleIn(initialScale = 0.8f, animationSpec = tween(700))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
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
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Aciertos: $aciertos de $total",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    mensajeFinal,
                    color = colorBarra,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(50)),
                    color = colorBarra,
                    trackColor = Color.LightGray.copy(alpha = 0.3f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navController.navigate("inicio") },
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                ) {
                    Text(
                        "Volver al menú",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp)
                    )
                }
            }
        }

    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorFondo)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Pregunta ${indiceActual + 1} / ${preguntas.size}",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AnimatedContent(
                        targetState = pregunta?.texto ?: "No hay preguntas",
                        transitionSpec = {
                            fadeIn(tween(300)) with fadeOut(tween(300))
                        },
                        label = "PreguntaAnimada"
                    ) { textoAnimado ->
                        Text(
                            text = textoAnimado,
                            style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    pregunta?.let {
                        AnimatedContent(
                            targetState = it.imagenId,
                            transitionSpec = {
                                fadeIn(tween(300)) with fadeOut(tween(300))
                            },
                            label = "AnimacionImagen"
                        ) { imagenId ->
                            Image(
                                painter = painterResource(id = imagenId),
                                contentDescription = "Imagen de la pregunta",
                                modifier = Modifier
                                    .height(180.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        AnimatedContent(
                            targetState = it,
                            transitionSpec = {
                                fadeIn(tween(300)) with fadeOut(tween(300))
                            },
                            label = "AnimacionOpciones"
                        ) { preguntaAnimada ->
                            Column {
                                preguntaAnimada.opciones.forEachIndexed { index, textoOpcion ->
                                    Button(
                                        onClick = {
                                            if (respuestaSeleccionada == null) {
                                                respuestaSeleccionada = index
                                                if (index == preguntaAnimada.indiceCorrecto) {
                                                    aciertos++
                                                    colorFondo = Color(0xFFB9FBC0)
                                                } else {
                                                    colorFondo = Color(0xFFFFC2C2)
                                                }
                                            }
                                        },
                                        enabled = respuestaSeleccionada == null,
                                        modifier = Modifier
                                            .width(275.dp)
                                            .padding(vertical = 8.dp)
                                    ) {
                                        Text(
                                            text = textoOpcion,
                                            style = MaterialTheme.typography.headlineSmall.copy(
                                                fontSize = 15.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun obtenerPreguntasCategoria1(): List<Pregunta> = listOf(
    Pregunta(
        texto = "¿Qué comando se usa para iniciar un repositorio Git?",
        imagenId = R.drawable.c1p1,
        opciones = listOf("git start", "git init", "git begin", "git create"),
        indiceCorrecto = 1
    ),
    Pregunta(
        texto = "¿Cómo se llaman los cambios que aún no has guardado en Git?",
        imagenId = R.drawable.c1p2,
        opciones = listOf("Commits", "Pushes", "Staged", "Unstaged"),
        indiceCorrecto = 3
    ),
    Pregunta(
        texto = "¿Qué comando se usa para guardar los cambios localmente?",
        imagenId = R.drawable.c1p3,
        opciones = listOf("git push", "git commit", "git pull", "git status"),
        indiceCorrecto = 1
    ),
    Pregunta(
        texto = "¿Qué comando sube tus cambios al repositorio remoto?",
        imagenId = R.drawable.c1p4,
        opciones = listOf("git fetch", "git push", "git clone", "git merge"),
        indiceCorrecto = 1
    ),
    Pregunta(
        texto = "¿Cómo se llama la copia local de un repositorio remoto?",
        imagenId = R.drawable.c1p5,
        opciones = listOf("Branch", "Clone", "Fork", "Commit"),
        indiceCorrecto = 1
    ),
    Pregunta(
        texto = "¿Qué comando muestra el estado de tus archivos?",
        imagenId = R.drawable.c1p6,
        opciones = listOf("git status", "git log", "git diff", "git branch"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Cómo se llama una versión paralela del repositorio para trabajar?",
        imagenId = R.drawable.c1p7,
        opciones = listOf("Fork", "Branch", "Clone", "Commit"),
        indiceCorrecto = 1
    ),
    Pregunta(
        texto = "¿Qué comando combina cambios de una rama a otra?",
        imagenId = R.drawable.c1p8,
        opciones = listOf("git merge", "git rebase", "git pull", "git push"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué archivo contiene la configuración para ignorar archivos?",
        imagenId = R.drawable.c1p9,
        opciones = listOf(".gitignore", "README.md", "LICENSE", ".gitconfig"),
        indiceCorrecto = 0
    ),
    Pregunta(
        texto = "¿Qué comando se usa para descargar un repositorio remoto por primera vez?",
        imagenId = R.drawable.c1p10,
        opciones = listOf("git pull", "git fetch", "git clone", "git init"),
        indiceCorrecto = 2
    )
)
