package com.example.innovamadera_grupo2.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.innovamadera_grupo2.model.PerfilesContacto
import com.example.innovamadera_grupo2.ui.components.CampoDesplegable
import com.example.innovamadera_grupo2.ui.components.CampoValidado
import com.example.innovamadera_grupo2.ui.components.FotoMiniatura
import com.example.innovamadera_grupo2.ui.components.PantallaBase
import com.example.innovamadera_grupo2.utils.ContactoValidator
import com.example.innovamadera_grupo2.utils.FotoHelper
import com.example.innovamadera_grupo2.utils.UbicacionHelper
import com.example.innovamadera_grupo2.viewmodel.ContactoViewModel
import kotlinx.coroutines.launch

@Composable
fun ContactoScreen(viewModel: ContactoViewModel, onVolver: () -> Unit, onEnviado: () -> Unit) {
    val estado by viewModel.estado.collectAsState()
    val contexto = LocalContext.current
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fun mensaje(texto: String) {
        scope.launch { snackbar.showSnackbar(texto) }
    }

    // Al guardarse la solicitud en Room, se navega a la confirmación.
    LaunchedEffect(estado.enviado) { if (estado.enviado) onEnviado() }

    // ---------- Recurso nativo 1: CÁMARA (con permiso en tiempo de ejecución) ----------
    val camara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            viewModel.onFotoTomada(FotoHelper.guardarMiniatura(contexto, bitmap))
        } else {
            mensaje("No se tomó ninguna foto")
        }
    }
    val permisoCamara = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        if (concedido) camara.launch(null) else mensaje("Sin permiso de cámara no se puede adjuntar la foto")
    }

    // ---------- Recurso nativo 2: UBICACIÓN (con permiso en tiempo de ejecución) ----------
    fun obtenerUbicacion() {
        UbicacionHelper.obtenerUbicacionActual(contexto) { ubicacion ->
            if (ubicacion != null) {
                viewModel.onUbicacion(ubicacion.latitude, ubicacion.longitude)
            } else {
                mensaje("No se pudo obtener la ubicación. Activa el GPS e inténtalo de nuevo")
            }
        }
    }
    val permisoUbicacion = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultado ->
        if (resultado.values.any { it }) obtenerUbicacion() else mensaje("Permiso de ubicación denegado")
    }

    PantallaBase(
        titulo = "Contacto simulado",
        onVolver = onVolver,
        snackbarHostState = snackbar
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // RNF-11: dejar claro que es una simulación académica.
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Conexión simulada: no se envía a nadie. Usa datos de prueba.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Text("Cuéntanos qué te interesa", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            EncabezadoContenido(viewModel)

            CampoValidado(
                valor = estado.nombre,
                onCambio = viewModel::onNombreCambio,
                etiqueta = "Nombre (ficticio)",
                icono = Icons.Default.Person,
                error = estado.errores.nombre
            )
            CampoValidado(
                valor = estado.correo,
                onCambio = viewModel::onCorreoCambio,
                etiqueta = "Correo (de prueba)",
                icono = Icons.Default.Email,
                error = estado.errores.correo,
                tipoTeclado = KeyboardType.Email
            )
            CampoDesplegable(
                valor = estado.perfil,
                opciones = PerfilesContacto.opciones,
                onSeleccion = viewModel::onPerfilCambio,
                etiqueta = "Tu perfil",
                error = estado.errores.perfil
            )
            CampoValidado(
                valor = estado.motivo,
                onCambio = { if (it.length <= ContactoValidator.MOTIVO_MAX) viewModel.onMotivoCambio(it) },
                etiqueta = "Motivo de contacto",
                icono = Icons.Default.Create,
                error = estado.errores.motivo,
                unaLinea = false,
                lineasMinimas = 3,
                ayuda = "${estado.motivo.length}/${ContactoValidator.MOTIVO_MAX}"
            )

            // HU-07: firma electrónica simulada.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.onFirmaCambio(!estado.firmaSimulada) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = estado.firmaSimulada, onCheckedChange = viewModel::onFirmaCambio)
                Column {
                    Text("Simular firma electrónica avanzada", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "Simulación académica: sin credenciales ni validez legal.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // ---------- Adjuntos con recursos nativos (opcionales) ----------
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Adjuntos opcionales", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)

                    OutlinedButton(
                        onClick = {
                            val permitido = ContextCompat.checkSelfPermission(
                                contexto, Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                            if (permitido) camara.launch(null) else permisoCamara.launch(Manifest.permission.CAMERA)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.AddCircle, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (estado.fotoPath == null) "Tomar foto de referencia" else "Volver a tomar la foto")
                    }
                    AnimatedVisibility(visible = estado.fotoPath != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            estado.fotoPath?.let { FotoMiniatura(ruta = it, modifier = Modifier.size(96.dp)) }
                            Spacer(Modifier.width(8.dp))
                            TextButton(onClick = viewModel::quitarFoto) { Text("Quitar foto") }
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            if (UbicacionHelper.tienePermiso(contexto)) {
                                obtenerUbicacion()
                            } else {
                                permisoUbicacion.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(if (estado.latitud == null) "Adjuntar mi ubicación" else "Actualizar ubicación")
                    }
                    AnimatedVisibility(visible = estado.latitud != null && estado.longitud != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Lat ${"%.4f".format(estado.latitud ?: 0.0)} · Lon ${"%.4f".format(estado.longitud ?: 0.0)}",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(onClick = viewModel::quitarUbicacion) { Text("Quitar") }
                        }
                    }
                }
            }

            Spacer(Modifier.height(4.dp))
            Button(
                onClick = viewModel::enviar,
                enabled = !estado.enviando,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (estado.enviando) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                } else {
                    Text("Enviar solicitud")
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                }
            }
        }
    }
}

/** Muestra sobre qué contenido se está pidiendo contacto. */
@Composable
private fun EncabezadoContenido(viewModel: ContactoViewModel) {
    val contenido = viewModel.contenido
    Text(
        text = if (contenido != null) "Sobre: ${contenido.titulo}" else "Contenido no encontrado",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
