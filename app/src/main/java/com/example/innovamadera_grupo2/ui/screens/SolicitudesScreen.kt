package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.ui.components.FotoMiniatura
import com.example.innovamadera_grupo2.ui.components.PantallaBase
import com.example.innovamadera_grupo2.viewmodel.SolicitudesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Historial de solicitudes simuladas guardadas en Room (evidencia visible de la persistencia). */
@Composable
fun SolicitudesScreen(viewModel: SolicitudesViewModel) {
    val solicitudes by viewModel.solicitudes.collectAsState()
    val formato = Locale.forLanguageTag("es-CL").let { SimpleDateFormat("dd/MM/yyyy HH:mm", it) }

    PantallaBase(titulo = "Mis solicitudes") { padding ->
        if (solicitudes.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Todavía no hay solicitudes simuladas.\nEntra a un contenido y pulsa \"Quiero conectar\".",
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
                items(solicitudes, key = { it.id }) { s ->
                    Card(
                        modifier = Modifier.fillMaxWidth().animateItem(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            s.fotoPath?.let {
                                FotoMiniatura(ruta = it, modifier = Modifier.size(56.dp))
                                Spacer(Modifier.width(12.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(s.contenidoTitulo, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                                Text("${s.nombre} · ${s.perfil}", style = MaterialTheme.typography.bodySmall)
                                Text(s.motivo, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (s.latitud != null) {
                                        Icon(Icons.Default.LocationOn, contentDescription = "Con ubicación", modifier = Modifier.size(14.dp))
                                        Spacer(Modifier.width(4.dp))
                                    }
                                    Text(
                                        "${formato.format(Date(s.creadoEn))} · ${s.estado}" +
                                            if (s.firmaSimulada) " · firma simulada" else "",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            IconButton(onClick = { viewModel.eliminar(s.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar solicitud")
                            }
                        }
                    }
                }
            }
        }
    }
}
