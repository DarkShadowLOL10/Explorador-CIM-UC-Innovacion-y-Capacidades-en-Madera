package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.navigation.Screen
import com.example.innovamadera_grupo2.repository.ContenidoRepository
import com.example.innovamadera_grupo2.ui.components.BarraInferior
import com.example.innovamadera_grupo2.ui.components.BarraSuperior
import com.example.innovamadera_grupo2.ui.components.TarjetaContenido
import com.example.innovamadera_grupo2.viewmodel.FavoritosViewModel
import com.example.innovamadera_grupo2.viewmodel.MainViewModel

// Lista de contenidos guardados como favoritos (persisten con DataStore)
@Composable
fun GuardadosScreen(
    mainViewModel: MainViewModel,
    favoritosViewModel: FavoritosViewModel
) {
    val favoritos by favoritosViewModel.favoritos.collectAsState()

    Scaffold(
        topBar = { BarraSuperior(titulo = "Guardados") },
        bottomBar = { BarraInferior(Screen.Guardados, mainViewModel) }
    ) { innerPadding ->
        val ids = favoritos
        val guardados = ContenidoRepository.obtenerTodos().filter { ids != null && it.id in ids }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                ids == null -> CircularProgressIndicator()
                guardados.isEmpty() -> Text(
                    text = "Aún no has guardado contenidos.",
                    modifier = Modifier.padding(24.dp)
                )
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(guardados, key = { it.id }) { contenido ->
                        TarjetaContenido(
                            contenido = contenido,
                            esFavorito = true,
                            onClick = { mainViewModel.navigateTo(Screen.Detalle(contenido.id)) }
                        )
                    }
                }
            }
        }
    }
}
