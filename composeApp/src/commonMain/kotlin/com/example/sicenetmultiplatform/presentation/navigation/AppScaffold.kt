package com.example.sicenetmultiplatform.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sicenetmultiplatform.SessionManager
import kotlinx.coroutines.launch

// Nueva paleta de colores en Azul
private val BluePrimary = Color(0xFF1565C0)
private val BlueLight   = Color(0xFF1E88E5)
private val BlueDark    = Color(0xFF0D47A1)
private val AccentColor = Color(0xFFBBDEFB)

/**
 * Scaffold principal con un diseño renovado y profesional en tonos azules.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    title: String = "SICENET",
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope       = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)
            ) {
                DrawerContent(
                    onNavigate = { route ->
                        scope.launch { drawerState.close() }
                        if (route == "LOGOUT") {
                            showLogoutDialog = true
                        } else {
                            navController.navigate(route) {
                                popUpTo(Routes.PERFIL) { inclusive = false }
                            }
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            letterSpacing = 1.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor     = BluePrimary,
                        titleContentColor  = Color.White
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF5F7FA))
            ) {
                content()
            }
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Cerrar Sesión",
                    fontWeight = FontWeight.Bold,
                    color = BluePrimary
                )
            },
            text = { Text("¿Deseas salir de tu cuenta?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        SessionManager.cerrarSesion()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text("Salir", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar", color = Color.Gray)
                }
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun DrawerContent(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header moderno con círculo y gradiente
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(BlueDark, BluePrimary)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "SICENET",
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    fontSize = 28.sp
                )
                Text(
                    text = "Portal del Estudiante",
                    color = AccentColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Items del Menú
        DrawerItem(Icons.Default.Person, "Mi Perfil", { onNavigate(Routes.PERFIL) })
        DrawerItem(Icons.Default.School, "Carga Académica", { onNavigate(Routes.CARGA) })
        DrawerItem(Icons.Default.Assignment, "Cardex Escolar", { onNavigate(Routes.CARDEX) })
        DrawerItem(Icons.Default.Grade, "Calificaciones", { onNavigate(Routes.CALIFICACIONES) })

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp),
            thickness = 1.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )

        DrawerItem(
            icon    = Icons.Default.Logout,
            texto   = "Cerrar Sesión",
            onClick = { onNavigate("LOGOUT") },
            isLogout = true
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    texto: String,
    onClick: () -> Unit,
    isLogout: Boolean = false
) {
    val contentColor = if (isLogout) Color(0xFFE53935) else Color(0xFF37474F)
    
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isLogout) contentColor else BluePrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = texto,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}
