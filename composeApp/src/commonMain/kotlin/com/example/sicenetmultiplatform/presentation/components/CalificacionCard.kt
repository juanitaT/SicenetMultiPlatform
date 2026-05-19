package com.example.sicenetmultiplatform.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sicenetmultiplatform.data.local.entity.CalificacionFinalEntity
import com.example.sicenetmultiplatform.data.local.entity.CalificacionUnidadEntity

// Colores del tema Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueLight   = Color(0xFFE3F2FD)
private val BlueDark    = Color(0xFF0D47A1)

/**
 * Componente que muestra la calificación final y por unidad con tema azul.
 */
@Composable
fun CalificacionCard(
    materiaFinal: CalificacionFinalEntity,
    unidades: List<CalificacionUnidadEntity>
) {
    val calFinal = materiaFinal.calificacionFinal
    var expandido by remember { mutableStateOf(false) }

    // Lógica de colores basada en azul
    val (scoreColor, scoreBg) = when {
        calFinal >= 80 -> Pair(BlueDark, BlueLight)
        calFinal >= 70 -> Pair(BluePrimary, Color(0xFFF0F7FF))
        calFinal >  0  -> Pair(Color(0xFFC62828), Color(0xFFFFEBEE))
        else           -> Pair(Color.Gray, Color(0xFFF5F5F5))
    }

    val unidadesValidas = unidades.filter {
        !(unidades.size == 1 && it.calificacion == 0)
    }

    val tienemasDecinco = unidadesValidas.size > 5

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = materiaFinal.materia,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF263238),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(scoreBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (calFinal == 0) "—" else "$calFinal",
                        fontWeight = FontWeight.Black,
                        color = scoreColor,
                        fontSize = 18.sp
                    )
                }
            }

            if (unidadesValidas.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFECEFF1), thickness = 1.dp)
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "UNIDADES",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        unidadesValidas.take(5).forEach { unidad ->
                            UnidadBadge(unidad)
                        }
                    }

                    if (tienemasDecinco) {
                        IconButton(
                            onClick = { expandido = !expandido },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (expandido)
                                    Icons.Default.KeyboardArrowUp
                                else
                                    Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = BluePrimary
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = expandido && tienemasDecinco) {
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            mainAxisSpacing = 8.dp,
                            crossAxisSpacing = 8.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 65.dp)
                        ) {
                            unidadesValidas.drop(5).forEach { unidad ->
                                UnidadBadge(unidad)
                            }
                        }
                    }
                }

            } else {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Calificaciones parciales no disponibles",
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun UnidadBadge(unidad: CalificacionUnidadEntity) {
    val uColor = if (unidad.calificacion >= 70) BluePrimary else Color(0xFFD32F2F)
    val uBg = if (unidad.calificacion >= 70) BlueLight else Color(0xFFFFEBEE)

    Surface(
        color = uBg,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = "U${unidad.unidad}: ${unidad.calificacion}",
            fontSize = 11.sp,
            color = uColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    crossAxisSpacing: androidx.compose.ui.unit.Dp = 0.dp,
    content: @Composable () -> Unit
) {
    // Implementación simplificada para multiplatform si no está disponible FlowRow de Material3
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(mainAxisSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}
