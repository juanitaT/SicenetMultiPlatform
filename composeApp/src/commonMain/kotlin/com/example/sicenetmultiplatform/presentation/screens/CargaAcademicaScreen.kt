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
import com.example.sicenetmultiplatform.presentation.components.CargaAcademicaItem
import com.example.sicenetmultiplatform.presentation.viewmodel.CargaAcademicaViewModel
import java.text.SimpleDateFormat
import java.util.*

// Paleta de colores en Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueLight   = Color(0xFF1E88E5)
private val BlueDark    = Color(0xFF0D47A1)
private val BlueSurface = Color(0xFFF5F7FA)

/**
 * Pantalla de carga académica del alumno.
 */
@Composable
fun CargaAcademicaScreen(viewModel: CargaAcademicaViewModel) {

    LaunchedEffect(Unit) {
        viewModel.verificarYSincronizar()
    }

    val carga             by viewModel.carga.collectAsState()
    val ultimaActualizacion by viewModel.ultimaActualizacion.collectAsState()
    val isLoading         by viewModel.isLoading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueSurface)
    ) {
        if (carga.isEmpty() && isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = BluePrimary,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Sincronizando carga...",
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
                                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                            )
                            .padding(top = 40.dp, bottom = 32.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Carga Académica",
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
                                        text = "Actualizado: ${fmt.format(Date(it))}",
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // Chips de estadísticas
                            if (carga.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(28.dp))
                                val totalCreditos = carga.sumOf { it.creditos }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    CargaStatChip("Materias", "${carga.size}", Modifier.weight(1f))
                                    CargaStatChip("Créditos", "$totalCreditos", Modifier.weight(1f))
                                    CargaStatChip("Semestre", (carga.firstOrNull()?.semestre?.toString() ?: "-"), Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

                items(carga) { materia ->
                    CargaAcademicaItem(materia)
                }
                
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
private fun CargaStatChip(label: String, value: String, modifier: Modifier = Modifier) {
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
                text = label.uppercase(),
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}
