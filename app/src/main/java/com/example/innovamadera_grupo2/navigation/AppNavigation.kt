package com.example.innovamadera_grupo2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.innovamadera_grupo2.ui.screens.ContactoScreen
import com.example.innovamadera_grupo2.ui.screens.DetalleScreen
import com.example.innovamadera_grupo2.ui.screens.ExplorarScreen
import com.example.innovamadera_grupo2.ui.screens.GuardadosScreen
import com.example.innovamadera_grupo2.ui.screens.PerfilScreen
import com.example.innovamadera_grupo2.ui.screens.ResumenScreen
import com.example.innovamadera_grupo2.viewmodel.ContactoViewModel
import com.example.innovamadera_grupo2.viewmodel.ExplorarViewModel
import com.example.innovamadera_grupo2.viewmodel.FavoritosViewModel
import com.example.innovamadera_grupo2.viewmodel.MainViewModel
import com.example.innovamadera_grupo2.viewmodel.PerfilViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Aquí creamos los ViewModels una sola vez y los compartimos entre pantallas
    val mainViewModel: MainViewModel = viewModel()
    val explorarViewModel: ExplorarViewModel = viewModel()
    val favoritosViewModel: FavoritosViewModel = viewModel()
    val contactoViewModel: ContactoViewModel = viewModel()
    val perfilViewModel: PerfilViewModel = viewModel()

    // Escuchar los eventos de navegación emitidos por el ViewModel
    LaunchedEffect(Unit) {
        mainViewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    navController.navigate(event.route.construirRuta()) {
                        event.popUpToRoute?.let { destino ->
                            popUpTo(destino.route) { inclusive = event.inclusive }
                        }
                        launchSingleTop = event.singleTop
                    }
                }
                is NavigationEvent.PopBackStack -> navController.popBackStack()
                is NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Explorar.route
    ) {
        composable(Screen.Explorar.route) {
            ExplorarScreen(mainViewModel, explorarViewModel, favoritosViewModel)
        }
        composable(
            route = Screen.RUTA_DETALLE,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            DetalleScreen(itemId, mainViewModel, favoritosViewModel, contactoViewModel)
        }
        composable(Screen.Guardados.route) {
            GuardadosScreen(mainViewModel, favoritosViewModel)
        }
        composable(Screen.Perfil.route) {
            PerfilScreen(mainViewModel, perfilViewModel)
        }
        composable(Screen.Contacto.route) {
            ContactoScreen(mainViewModel, contactoViewModel)
        }
        composable(Screen.Resumen.route) {
            ResumenScreen(mainViewModel, contactoViewModel)
        }
    }
}
