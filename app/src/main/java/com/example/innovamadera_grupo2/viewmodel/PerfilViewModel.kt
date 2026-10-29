package com.example.innovamadera_grupo2.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfilViewModel : ViewModel() {

    // Uri de la imagen seleccionada o capturada (null = sin imagen)
    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri

    // Resultado de la galería (uri es null si la persona cancela)
    fun actualizarImagenDesdeGaleria(uri: Uri?) {
        if (uri != null) _imagenUri.value = uri
    }

    // Resultado de la cámara (exito es false si la persona cancela)
    fun actualizarImagenDesdeCamara(exito: Boolean, uri: Uri?) {
        if (exito && uri != null) _imagenUri.value = uri
    }
}
