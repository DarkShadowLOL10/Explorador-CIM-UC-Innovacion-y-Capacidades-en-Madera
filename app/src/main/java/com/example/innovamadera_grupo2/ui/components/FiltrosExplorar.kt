package com.example.innovamadera_grupo2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.model.TipoContenido

// Campo de búsqueda por palabra clave
@Composable
fun BarraBusqueda(
    texto: String,
    onTextoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = texto,
        onValueChange = onTextoChange,
        label = { Text("Buscar proyectos, capacidades...") },
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (texto.isNotEmpty()) {
                IconButton(onClick = { onTextoChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Borrar búsqueda")
                }
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}

// Filtros por tipo en una fila horizontal (pantallas compactas y medianas)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosHorizontales(
    seleccionado: TipoContenido?,
    onSeleccion: (TipoContenido?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = seleccionado == null,
                onClick = { onSeleccion(null) },
                label = { Text("Todos") }
            )
        }
        items(TipoContenido.entries) { tipo ->
            FilterChip(
                selected = seleccionado == tipo,
                onClick = { onSeleccion(tipo) },
                label = { Text(tipo.plural) }
            )
        }
    }
}

// Filtros por tipo en una columna vertical (pantallas expandidas)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosVerticales(
    seleccionado: TipoContenido?,
    onSeleccion: (TipoContenido?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = seleccionado == null,
            onClick = { onSeleccion(null) },
            label = { Text("Todos") }
        )
        TipoContenido.entries.forEach { tipo ->
            FilterChip(
                selected = seleccionado == tipo,
                onClick = { onSeleccion(tipo) },
                label = { Text(tipo.plural) }
            )
        }
    }
}
