package com.example.innovamadera_grupo2.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/** Guarda la foto tomada con la cámara en el almacenamiento PRIVADO de la app (sin permisos extra). */
object FotoHelper {
    fun guardarMiniatura(context: Context, bitmap: Bitmap): String? = try {
        val carpeta = File(context.filesDir, "fotos").apply { mkdirs() }
        val archivo = File(carpeta, "ref_${System.currentTimeMillis()}.jpg")
        FileOutputStream(archivo).use { bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it) }
        archivo.absolutePath
    } catch (e: IOException) {
        null
    }
}
