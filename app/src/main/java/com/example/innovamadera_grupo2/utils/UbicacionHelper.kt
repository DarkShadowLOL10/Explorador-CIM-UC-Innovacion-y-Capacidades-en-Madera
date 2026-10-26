package com.example.innovamadera_grupo2.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat

/** Acceso a la ubicación del dispositivo (recurso nativo #2). Sin dependencias extra. */
object UbicacionHelper {

    private fun concedido(context: Context, permiso: String) =
        ContextCompat.checkSelfPermission(context, permiso) == PackageManager.PERMISSION_GRANTED

    fun tienePermiso(context: Context): Boolean =
        concedido(context, Manifest.permission.ACCESS_FINE_LOCATION) ||
            concedido(context, Manifest.permission.ACCESS_COARSE_LOCATION)

    /** Entrega la ubicación actual en [onResultado], o null si no hay permiso / GPS apagado. */
    @SuppressLint("MissingPermission")
    fun obtenerUbicacionActual(context: Context, onResultado: (Location?) -> Unit) {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val fina = concedido(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val proveedor = when {
            fina && manager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> LocationManager.GPS_PROVIDER
            tienePermiso(context) && manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> LocationManager.NETWORK_PROVIDER
            else -> null
        }
        if (proveedor == null) {
            onResultado(null)
            return
        }
        try {
            LocationManagerCompat.getCurrentLocation(
                manager, proveedor, null, ContextCompat.getMainExecutor(context)
            ) { ubicacion -> onResultado(ubicacion) }
        } catch (e: SecurityException) {
            onResultado(null)
        }
    }
}
