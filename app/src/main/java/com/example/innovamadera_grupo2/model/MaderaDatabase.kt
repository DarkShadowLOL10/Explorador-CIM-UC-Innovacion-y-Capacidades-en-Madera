package com.example.innovamadera_grupo2.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MaderaEntity::class], version = 1, exportSchema = false)
abstract class MaderaDatabase : RoomDatabase() {
    abstract fun maderaDao(): MaderaDao

    companion object {
        @Volatile
        private var INSTANCE: MaderaDatabase? = null

        fun getInstance(context: Context): MaderaDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MaderaDatabase::class.java,
                    "innovamadera.db"
                ).build().also { INSTANCE = it }
            }
    }
}
