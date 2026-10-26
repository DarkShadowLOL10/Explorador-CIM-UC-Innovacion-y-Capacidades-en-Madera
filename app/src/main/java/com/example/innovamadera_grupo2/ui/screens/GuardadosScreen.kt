package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.ui.components.PantallaBase
import com.example.innovamadera_grupo2.ui.components.TarjetaContenido
import com.example.innovamadera_grupo2.viewmodel.ExplorarViewModel

@Composable
fun GuardadosScreen(viewModel: ExplorarViewModel, onAbrirDetalle: (String) -> Unit) {
    val favoritos by viewModel.favoritos.collectAsState()

    PantallaBase(titulo = "Guardados") { padding ->
        if (favoritos.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Aún no guardas contenidos.\nToca el corazón en una tarjeta para agregarlo.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(24.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoritos, key = { it.id }) { contenido ->
                    TarjetaContenido(
                        contenido = contenido,
                        esFavorito = true,
                        onClick = { onAbrirDetalle(contenido.id) },
                        onFavorito = { viewModel.alternarFavorito(contenido.id) },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}
