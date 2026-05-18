package com.example.sicenetmultiplatform.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sicenetmultiplatform.SessionManager
import com.example.sicenetmultiplatform.data.di.AppContainer



@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = _root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.LOGIN
    ) {

        // Login
        composable(_root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.LOGIN) {
            val viewModel = remember {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.viewmodel.LoginViewModel(
                    networkRepository = AppContainer.networkRepository,
                    localRepository = AppContainer.localRepository
                )
            }

            _root_ide_package_.com.example.sicenetmultiplatform.presentation.screens.LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(_root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.PERFIL) {
                        popUpTo(_root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.LOGIN) {
                            _root_ide_package_.androidx.navigation.PopUpToBuilder.inclusive = true
                        }
                    }
                }
            )
        }

        // Perfil
        composable(_root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.PERFIL) {
            val viewModel = remember {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.viewmodel.PerfilViewModel(
                    localRepository = AppContainer.localRepository,
                    matricula = SessionManager.matricula
                )
            }
            _root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.AppScaffold(
                navController = navController
            ) {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.screens.PerfilScreen(
                    viewModel = viewModel
                )
            }
        }

        // Carga Académica
        composable(_root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.CARGA) {
            val viewModel = remember {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.viewmodel.CargaAcademicaViewModel(
                    localRepository = AppContainer.localRepository,
                    networkRepository = AppContainer.networkRepository,
                    matricula = SessionManager.matricula
                )
            }
            _root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.AppScaffold(
                navController = navController
            ) {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.screens.CargaAcademicaScreen(
                    viewModel = viewModel
                )
            }
        }

        // Cardex
        composable(_root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.CARDEX) {
            val viewModel = remember {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.viewmodel.CardexViewModel(
                    localRepository = AppContainer.localRepository,
                    networkRepository = AppContainer.networkRepository
                )
            }
            _root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.AppScaffold(
                navController = navController
            ) {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.screens.CardexScreen(
                    viewModel = viewModel
                )
            }
        }

        // Calificaciones
        composable(_root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.Routes.CALIFICACIONES) {
            val viewModel = remember {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.viewmodel.CalificacionesViewModel(
                    localRepository = AppContainer.localRepository,
                    networkRepository = AppContainer.networkRepository,
                    matricula = SessionManager.matricula
                )
            }
            _root_ide_package_.com.example.sicenetmultiplatform.presentation.navigation.AppScaffold(
                navController = navController
            ) {
                _root_ide_package_.com.example.sicenetmultiplatform.presentation.screens.CalificacionesScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}