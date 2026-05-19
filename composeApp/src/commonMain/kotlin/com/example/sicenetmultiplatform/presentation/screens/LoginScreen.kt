package com.example.sicenetmultiplatform.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sicenetmultiplatform.presentation.viewmodel.LoginViewModel

// Paleta de colores en Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueLight   = Color(0xFF1E88E5)
private val BlueDark    = Color(0xFF0D47A1)

/**
 * Pantalla de autenticación del alumno con diseño renovado en Azul.
 * Pantalla de autenticación del alumno.
 * * Maneja Login online y offline.
 * * Basada en LoginScreen.kt del proyecto Android original.
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    var usuario  by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()
    val error     by viewModel.error.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BlueDark, BluePrimary, BlueLight)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.98f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Título
                    Text(
                        text = "SICENET",
                        fontWeight = FontWeight.Black,
                        color = BluePrimary,
                        fontSize = 42.sp,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Portal de Alumnos",
                        color = BluePrimary.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // Campo usuario
                    OutlinedTextField(
                        value = usuario,
                        onValueChange = {
                            usuario = it
                            viewModel.limpiarError()
                        },
                        label = { Text("Número de Control") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = BluePrimary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = BluePrimary,
                            unfocusedBorderColor = BlueLight.copy(alpha = 0.3f),
                            focusedLabelColor    = BluePrimary,
                            cursorColor          = BluePrimary
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            viewModel.limpiarError()
                        },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = BluePrimary
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = BluePrimary,
                            unfocusedBorderColor = BlueLight.copy(alpha = 0.3f),
                            focusedLabelColor    = BluePrimary,
                            cursorColor          = BluePrimary
                        ),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    // Mensaje de error
                    error?.let {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            color = Color(0xFFFFEBEE),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = it,
                                color = Color(0xFFD32F2F),
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón login
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        onClick = {
                            viewModel.login(
                                usuario  = usuario,
                                password = password,
                                onSuccess = onLoginSuccess
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BluePrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation  = 4.dp
                        ),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Text(
                                text = "INGRESAR",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
