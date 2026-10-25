package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.viewmodel.FormularioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(viewModel: FormularioViewModel) {
    val tipoMadera by viewModel.tipoMadera.collectAsState()
    val errorTipo by viewModel.errorTipo.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Inspección de Madera") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = tipoMadera,
                onValueChange = { viewModel.onTipoCambio(it) },
                label = { Text("Tipo de madera") },
                isError = errorTipo != null,
                modifier = Modifier.fillMaxWidth()
            )

            // Animación funcional para errores (IE 2.2.2)
            AnimatedVisibility(visible = errorTipo != null) {
                Text(
                    text = errorTipo ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.guardar() },
                enabled = errorTipo == null && tipoMadera.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Inspección")
            }
        }
    }
}