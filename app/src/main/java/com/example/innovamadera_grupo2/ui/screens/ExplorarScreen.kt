package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.ui.components.PantallaBase
import com.example.innovamadera_grupo2.ui.components.TarjetaContenido
import com.example.innovamadera_grupo2.viewmodel.ExplorarViewModel

private fun LazyGridScope.itemCompleto(content: @Composable LazyGridItemScope.() -> Unit) =
    item(span = { GridItemSpan(maxLineSpan) }, content = content)

@Composable
fun ExplorarScreen(viewModel: ExplorarViewModel, onAbrirDetalle: (String) -> Unit) {
    val consulta by viewModel.consulta.collectAsState()
    val tipo by viewModel.tipoSeleccionado.collectAsState()
    val resultados by viewModel.resultados.collectAsState()
    val favoritos by viewModel.favoritosIds.collectAsState()

    PantallaBase(titulo = "Explorador CIM UC") { padding ->
        // Grilla adaptable: 1 columna en teléfonos pequeños, más columnas en tablets (RNF-02).
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 220.dp),
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemCompleto {
                Column {
                    Text(
                        "INVESTIGACIÓN QUE TOMA FORMA",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Encuentra lo que PODEMOS CONSTRUIR.",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Explora proyectos, capacidades y personas del CIM UC.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            itemCompleto {
                OutlinedTextField(
                    value = consulta,
                    onValueChange = viewModel::onConsultaCambio,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar proyectos, capacidades...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = consulta.isNotEmpty(),
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
                        ) {
                            IconButton(onClick = { viewModel.onConsultaCambio("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Limpiar búsqueda")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            itemCompleto {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = tipo == null,
                            onClick = { viewModel.onTipoSeleccionado(null) },
                            label = { Text("Todo") }
                        )
                    }
                    items(viewModel.tiposDisponibles) { t ->
                        FilterChip(
                            selected = tipo == t,
                            onClick = { viewModel.onTipoSeleccionado(t) },
                            label = { Text(t.plural) },
                            leadingIcon = if (tipo == t) {
                                {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            } else null
                        )
                    }
                }
            }

            itemCompleto {
                AnimatedContent(targetState = resultados.size, label = "contador") { n ->
                    Text(
                        text = "$n resultado${if (n == 1) "" else "s"}",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            if (resultados.isEmpty()) {
                itemCompleto { EstadoVacio(onLimpiar = viewModel::limpiarFiltros) }
            } else {
                items(resultados, key = { it.id }) { contenido ->
                    TarjetaContenido(
                        contenido = contenido,
                        esFavorito = contenido.id in favoritos,
                        onClick = { onAbrirDetalle(contenido.id) },
                        onFavorito = { viewModel.alternarFavorito(contenido.id) },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}

/** HU-02: estado vacío con opción para limpiar. */
@Composable
private fun EstadoVacio(onLimpiar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))
        Text("Sin resultados", style = MaterialTheme.typography.titleMedium)
        Text("Prueba con otra palabra o quita los filtros.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onLimpiar) { Text("Limpiar búsqueda") }
    }
}
