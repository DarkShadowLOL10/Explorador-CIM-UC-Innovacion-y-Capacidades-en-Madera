package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.navigation.Screen
import com.example.innovamadera_grupo2.ui.components.BarraSuperior
import com.example.innovamadera_grupo2.viewmodel.ContactoViewModel
import com.example.innovamadera_grupo2.viewmodel.MainViewModel

// Resumen: lee los datos del mismo ContactoViewModel (sin pasar argumentos entre rutas)
@Composable
fun ResumenScreen(
    mainViewModel: MainViewModel,
    viewModel: ContactoViewModel
) {
    val estado by viewModel.estado.collectAsState()

    // El check de confirmación aparece con animación al entrar a la pantalla
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Scaffold(
        topBar = { BarraSuperior(titulo = "Resumen") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = visible, enter = fadeIn() + scaleIn()) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Enviado",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(96.dp)
                )
            }
            Text("¡Solicitud enviada!", style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "Simulación académica: no se envió información a ninguna persona real.",
                style = MaterialTheme.typography.bodySmall
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Interés: ${estado.interes.ifBlank { "General" }}")
                Text("Nombre: ${estado.nombre}")
                Text("Correo: ${estado.correo}")
                Text("Motivo: ${estado.mensaje}")
                Text("Términos: ${if (estado.aceptaTerminos) "Aceptados" else "No aceptados"}")
            }

            Button(
                onClick = {
                    viewModel.limpiarFormulario()
                    mainViewModel.navigateTo(
                        screen = Screen.Explorar,
                        popUpToRoute = Screen.Explorar,
                        singleTop = true
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver a explorar")
            }
        }
    }
}
