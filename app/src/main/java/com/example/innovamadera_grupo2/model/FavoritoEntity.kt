package com.example.innovamadera_grupo2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Contenido guardado por la persona usuaria (pestaña "Guardados"). */
@Entity(tableName = "favoritos")
data class FavoritoEntity(
    @PrimaryKey val contenidoId: String
)
