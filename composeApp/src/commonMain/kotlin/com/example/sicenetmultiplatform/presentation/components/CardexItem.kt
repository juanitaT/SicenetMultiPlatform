package com.example.sicenetmultiplatform.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sicenetmultiplatform.data.local.entity.CardexEntity

// Colores del tema Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueDark    = Color(0xFF0D47A1)
private val BlueLight   = Color(0xFFE3F2FD)

@Composable
fun CardexItem(materia: CardexEntity) {

    val acreditado = materia.calificacion >= 70
    val enCurso    = materia.calificacion == 0

    val calColor = when {
        materia.calificacion >= 80 -> BlueDark
        materia.calificacion >= 70 -> BluePrimary
        materia.calificacion >  0  -> Color(0xFFC62828)
        else                       -> Color.Gray
    }

    val calBg = when {
        materia.calificacion >= 70 -> BlueLight
        materia.calificacion >  0  -> Color(0xFFFFEBEE)
        else                       -> Color(0xFFF5F5F5)
    }

    val statusColor = when {
        enCurso    -> Color.Gray
        acreditado -> BluePrimary
        else       -> Color(0xFFC62828)
    }

    val statusLabel = when {
        enCurso    -> "En curso"
        acreditado -> "✓ Acreditada"
        else       -> "✗ No acreditada"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Círculo con calificación
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(calBg),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (materia.calificacion > 0) "${materia.calificacion}" else "—",
                    fontWeight = FontWeight.Black,
                    color = calColor,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            // Datos de la materia
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = materia.materia,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF263238),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Badge de estatus
                    Surface(
                        color = statusColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = statusLabel,
                            fontSize = 11.sp,
                            color = statusColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = "Periodo: ${materia.periodo}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontStyle = FontStyle.Normal
                    )
                }
            }
        }
    }
}
