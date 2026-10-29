package com.example.innovamadera_grupo2.ui.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

// Crea un archivo temporal en cache/imagenes/ y devuelve su Uri (vía FileProvider)
// para que la cámara guarde ahí la foto.
fun crearUriTemporal(context: Context): Uri {
    val carpeta = File(context.cacheDir, "imagenes").apply { mkdirs() }
    val archivo = File.createTempFile("foto_", ".jpg", carpeta)
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)
}
