package com.example.login.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.login.R
import androidx.compose.animation.core.*

@Composable
fun WelcomeScreen(navController: NavController, username: String) {
    val primaryColor = Color(0xFF00A0B0)
    val darkBlue = Color(0xFF035D75)

    var visible by remember { mutableStateOf(false) }
    var showLogoutConfirmDialog by remember { mutableStateOf(false) }

    val headerScale = remember { Animatable(0.95f) }

    LaunchedEffect(Unit) {
        visible = true
        headerScale.animateTo(
            1f,
            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = listOf(primaryColor, darkBlue))
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // HEADER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.White)
                    .shadow(4.dp)
                    .padding(horizontal = 16.dp)
                    .graphicsLayer(scaleX = headerScale.value, scaleY = headerScale.value),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { showLogoutConfirmDialog = true },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(primaryColor.copy(alpha = 0.12f))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Menú Principal",
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 22.sp),
                        color = primaryColor,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            // CONTENIDO ANIMADO
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut(),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Bienvenido, $username!",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 30.sp),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    // LOGO GRANDE CON ANIMACIÓN
                    val infiniteTransition = rememberInfiniteTransition()
                    val pulse by infiniteTransition.animateFloat(
                        initialValue = 0.98f,
                        targetValue = 1.05f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1500, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(130.dp)
                            .graphicsLayer(scaleX = pulse, scaleY = pulse)
                            .clip(CircleShape)
                            .shadow(12.dp)
                    )

                    Divider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)

                    // CATEGORÍAS CON EFECTOS
                    val categorias = listOf(
                        Triple("Domina Git y controla tu código", R.drawable.git, "categoria1"),
                        Triple("Explora el mundo de las APIs REST", R.drawable.api, "categoria2"),
                        Triple("SQL para héroes de datos", R.drawable.sql, "categoria3"),
                        Triple("Kotlin: Tu nuevo mejor amigo", R.drawable.kotlin, "categoria4"),
                        Triple("Laravel PHP: Código elegante y potente", R.drawable.laravel, "categoria5"),
                    )

                    categorias.forEach { (titulo, imagen, ruta) ->
                        QuizItem(
                            titulo = titulo,
                            descripcion = "Desafía tus conocimientos en esta categoría.",
                            imagenId = imagen,
                            onIniciar = { navController.navigate(ruta) }
                        )
                    }

                    Spacer(modifier = Modifier.height(80.dp)) // Para que no tape el FAB
                }
            }
        }

        // FAB Sorpresa
        FloatingActionButton(
            onClick = { /* aquí puedes mostrar un diálogo, changelog, easter egg... */ },
            containerColor = Color.White,
            contentColor = primaryColor,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Text("⭐", fontSize = 24.sp)
        }

        // DIALOGO CERRAR SESIÓN
        if (showLogoutConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutConfirmDialog = false },
                title = { Text("Cerrar sesión") },
                text = { Text("¿Estás seguro que quieres cerrar sesión?") },
                confirmButton = {
                    TextButton(onClick = {
                        showLogoutConfirmDialog = false
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Text("Sí, cerrar sesión")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutConfirmDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun QuizItem(
    titulo: String,
    descripcion: String,
    imagenId: Int,
    onIniciar: () -> Unit
) {
    val scale = remember { Animatable(0.9f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            1f,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        onClick = onIniciar,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                shadowElevation = 8f
                clip = true
            },
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                titulo,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(fontSize = 20.sp),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = imagenId),
                contentDescription = "Imagen de $titulo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(14.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                descripcion,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}