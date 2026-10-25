package com.example.innovamadera_grupo2.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.innovamadera_grupo2.ui.screens.FormularioScreen
import com.example.innovamadera_grupo2.viewmodel.FormularioViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "formulario") {
        composable("formulario") {
            // viewModel() crea y conserva el ViewModel con el ciclo de vida correcto
            val viewModel: FormularioViewModel = viewModel()
            FormularioScreen(viewModel = viewModel)
        }
        // Agregar más vistas como la lista de datos guardados
    }
}
