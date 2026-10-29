package com.example.innovamadera_grupo2.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.innovamadera_grupo2.navigation.Screen
import com.example.innovamadera_grupo2.ui.components.BarraInferior
import com.example.innovamadera_grupo2.ui.components.BarraSuperior
import com.example.innovamadera_grupo2.ui.components.ImagenInteligente
import com.example.innovamadera_grupo2.ui.utils.crearUriTemporal
import com.example.innovamadera_grupo2.viewmodel.MainViewModel
import com.example.innovamadera_grupo2.viewmodel.PerfilViewModel

// Foto de perfil con recursos nativos: galería y cámara (Guía 13)
@Composable
fun PerfilScreen(
    mainViewModel: MainViewModel,
    perfilViewModel: PerfilViewModel
) {
    val context = LocalContext.current
    val imagenUri by perfilViewModel.imagenUri.collectAsState()

    // Uri donde la cámara guardará la foto (rememberSaveable: sobrevive a un giro de pantalla)
    var uriTemporal by rememberSaveable { mutableStateOf<Uri?>(null) }
    var aviso by rememberSaveable { mutableStateOf<String?>(null) }

    // Lanzador de la galería
    val galeriaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        perfilViewModel.actualizarImagenDesdeGaleria(uri)
    }

    // Lanzador de la cámara
    val camaraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { exito ->
        perfilViewModel.actualizarImagenDesdeCamara(exito, uriTemporal)
    }

    // Crea el archivo temporal y abre la cámara
    fun abrirCamara() {
        try {
            val uri = crearUriTemporal(context)
            uriTemporal = uri
            camaraLauncher.launch(uri)
        } catch (e: Exception) {
            aviso = "No se pudo abrir la cámara en este dispositivo."
        }
    }

    // Lanzador que pide el permiso de cámara en tiempo de ejecución
    val permisoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            aviso = null
            abrirCamara()
        } else {
            aviso = "Sin permiso de cámara no se puede tomar la foto. Puedes usar la galería."
        }
    }

    Scaffold(
        topBar = { BarraSuperior(titulo = "Mi perfil") },
        bottomBar = { BarraInferior(Screen.Perfil, mainViewModel) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            ImagenInteligente(uri = imagenUri)

            Button(
                onClick = { galeriaLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Elegir de la galería")
            }

            OutlinedButton(
                onClick = {
                    val tienePermiso = ContextCompat.checkSelfPermission(
                        context, Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                    if (tienePermiso) abrirCamara()
                    else permisoLauncher.launch(Manifest.permission.CAMERA)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomar foto con la cámara")
            }

            aviso?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
