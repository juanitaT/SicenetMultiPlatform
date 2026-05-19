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
import com.example.sicenetmultiplatform.presentation.components.CalificacionCard
import com.example.sicenetmultiplatform.presentation.viewmodel.CalificacionesViewModel

// Paleta de colores en Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueLight   = Color(0xFF1E88E5)
private val BlueDark    = Color(0xFF0D47A1)
private val BlueSurface = Color(0xFFF5F7FA)

/**
 * * Pantalla de calificaciones finales y por unidad del alumno.
 *  * Basada en CalificacionesScreen.kt del proyecto Android original.
 *  *
 */
@Composable
fun CalificacionesScreen(viewModel: CalificacionesViewModel) {

    LaunchedEffect(Unit) {
        viewModel.verificarYSincronizar()
    }

    val finales   by viewModel.calificacionesFinales.collectAsState()
    val unidades  by viewModel.calificacionesUnidad.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueSurface)
    ) {
        if (finales.isEmpty() && isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = BluePrimary,
                    modifier = Modifier.size(56.dp),
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Obteniendo calificaciones...",
                    color = BluePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                // Header con gradiente Azul
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(BlueDark, BluePrimary)
                                ),
                                shape = RoundedCornerShape(
                                    bottomStart = 32.dp,
                                    bottomEnd   = 32.dp
                                )
                            )
                            .padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Reporte de Calificaciones",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(
                                color = Color.White.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(
                                    text = "${finales.size} materias en este ciclo",
                                    fontSize = 12.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Spacer(modifier = Modifier.height(28.dp))
                            
                            val aprobadas  = finales.count { it.calificacionFinal >= 70 }
                            val reprobadas = finales.count { it.calificacionFinal in 1..69 }
                            val pendientes = finales.count { it.calificacionFinal == 0 }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                CalifHeaderChip("Aprobadas", "$aprobadas")
                                CalifHeaderChip("Reprobadas", "$reprobadas")
                                CalifHeaderChip("Pendientes", "$pendientes")
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                // Lista de materias
                items(finales) { materiaFinal ->
                    val unidadesMateria = unidades.filter {
                        it.materia.equals(materiaFinal.materia, ignoreCase = true)
                    }
                    CalificacionCard(
                        materiaFinal = materiaFinal,
                        unidades = unidadesMateria
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
private fun CalifHeaderChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontSize = 24.sp
        )
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.7f),
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}
