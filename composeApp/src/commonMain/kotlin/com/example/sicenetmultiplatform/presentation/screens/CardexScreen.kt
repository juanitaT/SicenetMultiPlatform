package com.example.sicenetmultiplatform.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sicenetmultiplatform.presentation.components.CardexItem
import com.example.sicenetmultiplatform.presentation.viewmodel.CardexViewModel
import java.text.SimpleDateFormat
import java.util.*

// Paleta de colores en Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueLight   = Color(0xFF1E88E5)
private val BlueDark    = Color(0xFF0D47A1)
private val BackgroundBlue = Color(0xFFF0F4F8)

/**
 * Pantalla del kardex académico del alumno.
 * Basada en CardexScreen.kt del proyecto Android original. */
@Composable
fun CardexScreen(viewModel: CardexViewModel) {

    LaunchedEffect(Unit) {
        viewModel.verificarYSincronizar()
    }

    val cardex              by viewModel.cardex.collectAsState()
    val ultimaActualizacion by viewModel.ultimaActualizacion.collectAsState()
    val isLoading           by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundBlue)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Header moderno con diseño de tarjeta flotante
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                    color = BluePrimary,
                    shadowElevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(BlueDark, BluePrimary)
                                )
                            )
                            .padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Kárdex Académico",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            ultimaActualizacion?.let {
                                val fmt = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                                Surface(
                                    color = Color.White.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        text = "Sincronizado: ${fmt.format(Date(it))}",
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // Chips resumen con nuevo diseño
                            if (cardex.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(28.dp))
                                val aprobadas  = cardex.count { it.calificacion >= 70 }
                                val reprobadas = cardex.size - aprobadas
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    CardexStatChip("MATERIAS", "${cardex.size}", Modifier.weight(1f))
                                    CardexStatChip("APROBADAS", "$aprobadas", Modifier.weight(1f))
                                    CardexStatChip("POR CURSAR", "$reprobadas", Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Estado de carga o lista de materias
            if (cardex.isEmpty() && isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = BluePrimary, strokeWidth = 3.dp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Obteniendo información...",
                                color = BluePrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            } else {
                items(cardex) { materia ->
                    CardexItem(materia)
                }
            }
        }
    }
}

@Composable
private fun CardexStatChip(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = 0.12f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontSize = 22.sp
            )
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}
