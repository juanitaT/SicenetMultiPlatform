package com.example.sicenetmultiplatform.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sicenetmultiplatform.presentation.viewmodel.PerfilViewModel

// Paleta de colores en Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueLight   = Color(0xFF1E88E5)
private val BlueDark    = Color(0xFF0D47A1)

/**
 * Pantalla del perfil académico del alumno.
 * Basada en PerfilScreen.kt del proyecto Android original.
 */
@Composable
fun PerfilScreen(viewModel: PerfilViewModel) {

    val perfil by viewModel.perfil.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BlueDark, BluePrimary, BlueLight)
                )
            )
    ) {
        if (perfil == null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(64.dp),
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Sincronizando perfil...",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            val p = perfil!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Información General",
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontSize = 30.sp,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.98f)
                    )
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {

                        PerfilItem(
                            label = "NOMBRE COMPLETO",
                            valor = p.nombre,
                            valorSize = 22,
                            valorColor = BlueDark,
                            valorWeight = FontWeight.Black
                        )

                        PerfilDivider()

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.weight(1f)) {
                                PerfilItem(label = "MATRÍCULA", valor = p.matricula)
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                PerfilItem(label = "SEMESTRE", valor = p.semActual.toString())
                            }
                        }

                        PerfilDivider()

                        PerfilItem(
                            label = "CARRERA",
                            valor = p.carrera,
                            valorSize = 17
                        )

                        PerfilDivider()

                        PerfilItem(
                            label = "ESPECIALIDAD",
                            valor = p.especialidad,
                            valorSize = 17
                        )

                        PerfilDivider()

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.weight(1f)) {
                                PerfilItem(label = "CDTOS. ACUMULADOS", valor = p.cdtosAcumulados.toString())
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                PerfilItem(label = "ESTATUS", valor = p.estatus)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PerfilItem(
    label: String,
    valor: String,
    valorSize: Int = 18,
    valorColor: Color = Color(0xFF37474F),
    valorWeight: FontWeight = FontWeight.Bold
) {
    Column {
        Text(
            text = label,
            fontSize = 11.sp,
            color = BluePrimary,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = valor,
            fontSize = valorSize.sp,
            color = valorColor,
            fontWeight = valorWeight,
            lineHeight = (valorSize + 4).sp
        )
    }
}

@Composable
private fun PerfilDivider() {
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider(
        color = Color(0xFFECEFF1),
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(20.dp))
}
