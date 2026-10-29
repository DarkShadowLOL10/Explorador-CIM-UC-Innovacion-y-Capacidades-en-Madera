package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.navigation.Screen
import com.example.innovamadera_grupo2.repository.ContenidoRepository
import com.example.innovamadera_grupo2.ui.components.BarraSuperior
import com.example.innovamadera_grupo2.ui.components.TarjetaContenido
import com.example.innovamadera_grupo2.viewmodel.ContactoViewModel
import com.example.innovamadera_grupo2.viewmodel.FavoritosViewModel
import com.example.innovamadera_grupo2.viewmodel.MainViewModel

// Detalle de un contenido: descripción, favorito animado y contenidos relacionados
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    itemId: String,
    mainViewModel: MainViewModel,
    favoritosViewModel: FavoritosViewModel,
    contactoViewModel: ContactoViewModel
) {
    val contenido = ContenidoRepository.obtenerPorId(itemId)
    val favoritos by favoritosViewModel.favoritos.collectAsState()
    val mostrarMensaje by favoritosViewModel.mostrarMensaje.collectAsState()
    val mensaje by favoritosViewModel.mensaje.collectAsState()

    Scaffold(
        topBar = {
            BarraSuperior(
                titulo = "Detalle",
                icono = Icons.AutoMirrored.Filled.ArrowBack,
                descripcionIcono = "Volver",
                onIconoClick = { mainViewModel.navigateBack() }
            )
        }
    ) { innerPadding ->
        if (contenido == null) {
            Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                Text("Contenido no encontrado.")
            }
            return@Scaffold
        }

        val esFavorito = contenido.id in (favoritos ?: emptySet())

        // Color del botón animado según el estado (Guía 12)
        val colorBoton by animateColorAsState(
            targetValue = if (esFavorito) MaterialTheme.colorScheme.tertiary
            else MaterialTheme.colorScheme.primary,
            animationSpec = tween(durationMillis = 500),
            label = "colorBoton"
        )
        // Texto derivado del estado
        val textoBoton by remember(esFavorito) {
            derivedStateOf { if (esFavorito) "Quitar de guardados" else "Guardar" }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = contenido.tipo.etiqueta.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = contenido.titulo, style = MaterialTheme.typography.headlineSmall)
            Text(text = contenido.descripcion, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Estado: ${contenido.estado}  ·  Año: ${contenido.anio}",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                contenido.etiquetas.forEach { etiqueta ->
                    AssistChip(onClick = {}, label = { Text(etiqueta) })
                }
            }

            Button(
                onClick = { favoritosViewModel.alternarFavorito(contenido.id) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorBoton,
                    contentColor = if (esFavorito) MaterialTheme.colorScheme.onTertiary
                    else MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(textoBoton)
            }

            // Mensaje que aparece y desaparece con animación
            AnimatedVisibility(visible = mostrarMensaje) {
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            OutlinedButton(
                onClick = {
                    contactoViewModel.onInteresChange(contenido.titulo)
                    mainViewModel.navigateTo(Screen.Contacto)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Quiero que me contacten")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Conectado con", style = MaterialTheme.typography.titleMedium)

            ContenidoRepository.obtenerRelacionados(contenido).forEach { relacionado ->
                TarjetaContenido(
                    contenido = relacionado,
                    esFavorito = relacionado.id in (favoritos ?: emptySet()),
                    onClick = { mainViewModel.navigateTo(Screen.Detalle(relacionado.id)) }
                )
            }
        }
    }
}
