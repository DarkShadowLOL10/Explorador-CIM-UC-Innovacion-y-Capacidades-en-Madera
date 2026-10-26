package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.ui.components.IconoFavorito
import com.example.innovamadera_grupo2.ui.components.PantallaBase
import com.example.innovamadera_grupo2.ui.theme.Lima
import com.example.innovamadera_grupo2.viewmodel.ExplorarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleScreen(
    viewModel: ExplorarViewModel,
    id: String,
    onVolver: () -> Unit,
    onAbrirRelacionado: (String) -> Unit,
    onConectar: (String) -> Unit
) {
    val contenido = remember(id) { viewModel.contenidoPorId(id) }
    val favoritos by viewModel.favoritosIds.collectAsState()

    PantallaBase(
        titulo = contenido?.tipo?.plural ?: "Detalle",
        onVolver = onVolver,
        acciones = {
            if (contenido != null) {
                IconoFavorito(
                    esFavorito = contenido.id in favoritos,
                    onClick = { viewModel.alternarFavorito(contenido.id) },
                    tintActivo = Lima,
                    tintInactivo = Color.White
                )
            }
        }
    ) { padding ->
        if (contenido == null) {
            Text("Contenido no encontrado", modifier = Modifier.padding(padding).padding(16.dp))
            return@PantallaBase
        }
        val relacionados = remember(id) { viewModel.relacionados(contenido) }

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                "${contenido.tipo.etiqueta.uppercase()} · ${contenido.id}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                "${contenido.anio} · ${contenido.estado.uppercase()}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Text(contenido.titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(contenido.descripcion, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(4.dp))
            Text(
                "Área: ${contenido.area}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                contenido.etiquetas.forEach { etiqueta ->
                    SuggestionChip(onClick = {}, label = { Text(etiqueta) })
                }
            }

            Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "CONECTADO CON",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text("${relacionados.size} vínculos", style = MaterialTheme.typography.labelMedium)
            }
            Spacer(Modifier.height(8.dp))

            relacionados.forEach { rel ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable { onAbrirRelacionado(rel.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(rel.titulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                            Text(rel.tipo.plural, style = MaterialTheme.typography.labelMedium)
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Abrir ${rel.titulo}")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { onConectar(contenido.id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Quiero conectar")
                Spacer(Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
            }
        }
    }
}
