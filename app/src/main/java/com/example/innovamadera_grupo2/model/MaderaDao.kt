package com.example.innovamadera_grupo2.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MaderaDao {
    @Insert
    suspend fun insertMadera(madera: MaderaEntity)

    @Query("SELECT * FROM maderas")
    fun getAllMaderas(): Flow<List<MaderaEntity>>
}