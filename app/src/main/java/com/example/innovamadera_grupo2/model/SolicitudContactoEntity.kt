package com.example.innovamadera_grupo2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Solicitud de contacto SIMULADA (CONTACT_REQUEST). Se guarda solo en el dispositivo. */
@Entity(tableName = "solicitudes")
data class SolicitudContactoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contenidoId: String,
    val contenidoTitulo: String,
    val nombre: String,
    val correo: String,
    val perfil: String,
    val motivo: String,
    val firmaSimulada: Boolean,
    val fotoPath: String?,
    val latitud: Double?,
    val longitud: Double?,
    val creadoEn: Long,
    val estado: String = "simulada"
)
