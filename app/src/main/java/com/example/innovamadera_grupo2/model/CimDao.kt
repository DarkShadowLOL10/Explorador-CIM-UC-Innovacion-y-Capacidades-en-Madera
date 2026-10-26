package com.example.innovamadera_grupo2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CimDao {
    @Insert
    suspend fun insertarSolicitud(solicitud: SolicitudContactoEntity): Long

    @Query("SELECT * FROM solicitudes ORDER BY creadoEn DESC")
    fun observarSolicitudes(): Flow<List<SolicitudContactoEntity>>

    @Query("DELETE FROM solicitudes WHERE id = :id")
    suspend fun eliminarSolicitud(id: Int)

    @Query("SELECT contenidoId FROM favoritos")
    fun observarFavoritos(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarFavorito(favorito: FavoritoEntity)

    @Query("DELETE FROM favoritos WHERE contenidoId = :contenidoId")
    suspend fun quitarFavorito(contenidoId: String)
}
