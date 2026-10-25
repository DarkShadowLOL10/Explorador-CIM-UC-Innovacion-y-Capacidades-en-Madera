package com.example.innovamadera_grupo2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "maderas")
data class MaderaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: String,
    val observaciones: String,
    val latitud: Double?,
    val longitud: Double?
)
