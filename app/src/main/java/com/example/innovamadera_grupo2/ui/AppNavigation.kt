package com.example.innovamadera_grupo2.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.innovamadera_grupo2.ui.screens.ConfirmacionScreen
import com.example.innovamadera_grupo2.ui.screens.ContactoScreen
import com.example.innovamadera_grupo2.ui.screens.DetalleScreen
import com.example.innovamadera_grupo2.ui.screens.ExplorarScreen
import com.example.innovamadera_grupo2.ui.screens.GuardadosScreen
import com.example.innovamadera_grupo2.ui.screens.SolicitudesScreen
import com.example.innovamadera_grupo2.viewmodel.ContactoViewModel
import com.example.innovamadera_grupo2.viewmodel.ExplorarViewModel
import com.example.innovamadera_grupo2.viewmodel.SolicitudesViewModel

private object Rutas {
    const val EXPLORAR = "explorar"
    const val GUARDADOS = "guardados"
    const val SOLICITUDES = "solicitudes"
    const val DETALLE = "detalle/{id}"
    const val CONTACTO = "contacto/{id}"
    const val CONFIRMACION = "confirmacion"

    fun detalle(id: String) = "detalle/$id"
    fun contacto(id: String) = "contacto/$id"
}

private data class ItemNavegacion(val ruta: String, val etiqueta: String, val icono: ImageVector)

private val itemsNavegacion = listOf(
    ItemNavegacion(Rutas.EXPLORAR, "Explorar", Icons.Default.Home),
    ItemNavegacion(Rutas.GUARDADOS, "Guardados", Icons.Default.Favorite),
    ItemNavegacion(Rutas.SOLICITUDES, "Solicitudes", Icons.Default.Email)
)

/**
 * Flujo principal (06-mockups.md):
 * Explorar -> Detalle -> (relacionados) -> Contacto simulado -> Confirmación.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val entrada by navController.currentBackStackEntryAsState()
    val rutaActual = entrada?.destination?.route
    val mostrarBarra = itemsNavegacion.any { it.ruta == rutaActual }

    Scaffold(
        bottomBar = {
            // La barra inferior solo aparece en las 3 pestañas principales (animada).
            AnimatedVisibility(
                visible = mostrarBarra,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                NavigationBar {
                    itemsNavegacion.forEach { item ->
                        NavigationBarItem(
                            selected = rutaActual == item.ruta,
                            onClick = {
                                navController.navigate(item.ruta) {
                                    popUpTo(Rutas.EXPLORAR) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icono, contentDescription = item.etiqueta) },
                            label = { Text(item.etiqueta) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Rutas.EXPLORAR,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding()),
            enterTransition = { slideInHorizontally(initialOffsetX = { it / 6 }) + fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it / 6 }) + fadeOut() }
        ) {
            composable(Rutas.EXPLORAR) {
                val vm: ExplorarViewModel = viewModel()
                ExplorarScreen(vm, onAbrirDetalle = { navController.navigate(Rutas.detalle(it)) })
            }
            composable(Rutas.GUARDADOS) {
                val vm: ExplorarViewModel = viewModel()
                GuardadosScreen(vm, onAbrirDetalle = { navController.navigate(Rutas.detalle(it)) })
            }
            composable(Rutas.SOLICITUDES) {
                val vm: SolicitudesViewModel = viewModel()
                SolicitudesScreen(vm)
            }
            composable(
                route = Rutas.DETALLE,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id").orEmpty()
                val vm: ExplorarViewModel = viewModel()
                DetalleScreen(
                    viewModel = vm,
                    id = id,
                    onVolver = { navController.popBackStack() },
                    onAbrirRelacionado = { navController.navigate(Rutas.detalle(it)) },
                    onConectar = { navController.navigate(Rutas.contacto(it)) }
                )
            }
            composable(
                route = Rutas.CONTACTO,
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) {
                // El id llega al ViewModel por SavedStateHandle.
                val vm: ContactoViewModel = viewModel()
                ContactoScreen(
                    viewModel = vm,
                    onVolver = { navController.popBackStack() },
                    onEnviado = {
                        navController.navigate(Rutas.CONFIRMACION) {
                            popUpTo(Rutas.EXPLORAR) { inclusive = false }
                        }
                    }
                )
            }
            composable(Rutas.CONFIRMACION) {
                ConfirmacionScreen(onVolver = { navController.popBackStack(Rutas.EXPLORAR, false) })
            }
        }
    }
}
