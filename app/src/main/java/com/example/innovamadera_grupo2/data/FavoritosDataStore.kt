package com.example.innovamadera_grupo2.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Declaramos el DataStore con nombre 'preferencias_usuario'
val Context.dataStore by preferencesDataStore(name = "preferencias_usuario")

class FavoritosDataStore(private val context: Context) {

    // Clave usada para guardar los ids de los contenidos favoritos
    private val CLAVE_FAVORITOS = stringSetPreferencesKey("favoritos")

    // Función para guardar los favoritos en DataStore
    suspend fun guardarFavoritos(favoritos: Set<String>) {
        context.dataStore.edit { preferencias ->
            preferencias[CLAVE_FAVORITOS] = favoritos
        }
    }

    // Función para obtener los favoritos como Flow (reactivo)
    fun obtenerFavoritos(): Flow<Set<String>> {
        return context.dataStore.data.map { preferencias ->
            preferencias[CLAVE_FAVORITOS] ?: emptySet()
        }
    }
}
