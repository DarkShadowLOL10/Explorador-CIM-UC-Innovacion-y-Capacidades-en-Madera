package com.example.innovamadera_grupo2.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.model.Contenido
import com.example.innovamadera_grupo2.model.TipoContenido
import com.example.innovamadera_grupo2.ui.theme.InnovaMadera_Grupo2Theme

// Tarjeta que resume un contenido del catálogo
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaContenido(
    contenido: Contenido,
    esFavorito: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = contenido.tipo.etiqueta.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = contenido.titulo, style = MaterialTheme.typography.titleMedium)
            Text(
                text = contenido.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${contenido.estado} · ${contenido.anio}",
                    style = MaterialTheme.typography.bodySmall
                )
                if (esFavorito) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Guardado",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaContenidoPreview() {
    InnovaMadera_Grupo2Theme {
        TarjetaContenido(
            contenido = Contenido(
                id = "P-01", tipo = TipoContenido.PROYECTO,
                titulo = "Bosque Urbano Modular",
                descripcion = "Sistema constructivo de baja huella para densificar barrios.",
                anio = "2026", estado = "En desarrollo",
                etiquetas = listOf("Construcción"), relacionados = emptyList()
            ),
            esFavorito = true,
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
