package com.example.innovamadera_grupo2.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SolicitudContactoEntity::class, FavoritoEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CimDatabase : RoomDatabase() {
    abstract fun cimDao(): CimDao

    companion object {
        @Volatile
        private var INSTANCE: CimDatabase? = null

        fun getInstance(context: Context): CimDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CimDatabase::class.java,
                    "innovamadera.db"
                )
                    // MVP académico: si cambia el esquema (la v1 tenía la tabla "maderas"), se recrea.
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
