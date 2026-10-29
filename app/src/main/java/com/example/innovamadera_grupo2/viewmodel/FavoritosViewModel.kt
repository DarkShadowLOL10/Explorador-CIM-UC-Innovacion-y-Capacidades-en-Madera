package com.example.innovamadera_grupo2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovamadera_grupo2.data.FavoritosDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// ViewModel extendido desde AndroidViewModel porque usamos contexto (DataStore)
class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    // DataStore creado con contexto de aplicación
    private val favoritosDataStore = FavoritosDataStore(application)

    // Ids favoritos. null = todavía cargando (se muestra el CircularProgressIndicator)
    private val _favoritos = MutableStateFlow<Set<String>?>(null)
    val favoritos: StateFlow<Set<String>?> = _favoritos

    // Estado para mostrar u ocultar el mensaje animado
    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    init {
        // Al iniciar el ViewModel, cargamos los favoritos desde DataStore
        cargarFavoritos()
    }

    private fun cargarFavoritos() {
        viewModelScope.launch {
            // Simula demora para mostrar el loader
            delay(1000)
            _favoritos.value = favoritosDataStore.obtenerFavoritos().first()
        }
    }

    // Agrega o quita un contenido de favoritos y lo guarda en DataStore
    fun alternarFavorito(id: String) {
        val actuales = _favoritos.value ?: return   // aún cargando
        viewModelScope.launch {
            val esNuevo = id !in actuales
            val nuevos = if (esNuevo) actuales + id else actuales - id

            favoritosDataStore.guardarFavoritos(nuevos)
            _favoritos.value = nuevos

            // Mostramos el mensaje visual animado y lo ocultamos después de 2 segundos
            _mensaje.value = if (esNuevo) "¡Guardado en favoritos!" else "Quitado de favoritos"
            _mostrarMensaje.value = true
            delay(2000)
            _mostrarMensaje.value = false
        }
    }
}
