package com.example.innovamadera_grupo2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.innovamadera_grupo2.data.CimRepository
import com.example.innovamadera_grupo2.model.CimDatabase
import com.example.innovamadera_grupo2.model.Contenido
import com.example.innovamadera_grupo2.model.SolicitudContactoEntity
import com.example.innovamadera_grupo2.utils.ContactoErrores
import com.example.innovamadera_grupo2.utils.ContactoValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Todo lo que la pantalla necesita dibujar del formulario, en un único objeto inmutable. */
data class ContactoUiState(
    val nombre: String = "",
    val correo: String = "",
    val perfil: String = "",
    val motivo: String = "",
    val firmaSimulada: Boolean = false,
    val fotoPath: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val errores: ContactoErrores = ContactoErrores(),
    val enviando: Boolean = false,
    val enviado: Boolean = false
)

/** Gestiona el formulario de contacto simulado. La validación se delega en [ContactoValidator]. */
class ContactoViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repo = CimRepository(CimDatabase.getInstance(application).cimDao())

    /** El argumento "id" de la ruta de navegación llega aquí automáticamente. */
    val contenido: Contenido? = repo.porId(savedStateHandle.get<String>("id").orEmpty())

    private val _estado = MutableStateFlow(ContactoUiState())
    val estado: StateFlow<ContactoUiState> = _estado.asStateFlow()

    fun onNombreCambio(valor: String) = _estado.update {
        it.copy(nombre = valor, errores = it.errores.copy(nombre = ContactoValidator.validarNombre(valor)))
    }

    fun onCorreoCambio(valor: String) = _estado.update {
        it.copy(correo = valor, errores = it.errores.copy(correo = ContactoValidator.validarCorreo(valor)))
    }

    fun onPerfilCambio(valor: String) = _estado.update {
        it.copy(perfil = valor, errores = it.errores.copy(perfil = ContactoValidator.validarPerfil(valor)))
    }

    fun onMotivoCambio(valor: String) = _estado.update {
        it.copy(motivo = valor, errores = it.errores.copy(motivo = ContactoValidator.validarMotivo(valor)))
    }

    fun onFirmaCambio(activa: Boolean) = _estado.update { it.copy(firmaSimulada = activa) }

    fun onFotoTomada(ruta: String?) = _estado.update { it.copy(fotoPath = ruta) }

    fun quitarFoto() = _estado.update { it.copy(fotoPath = null) }

    fun onUbicacion(latitud: Double, longitud: Double) =
        _estado.update { it.copy(latitud = latitud, longitud = longitud) }

    fun quitarUbicacion() = _estado.update { it.copy(latitud = null, longitud = null) }

    /** Valida todos los campos; si hay errores los muestra, si no guarda la solicitud en Room. */
    fun enviar() {
        val actual = _estado.value
        val errores = ContactoValidator.validarTodo(actual.nombre, actual.correo, actual.perfil, actual.motivo)
        if (errores.hayErrores) {
            _estado.update { it.copy(errores = errores) }
            return
        }
        val destino = contenido ?: return
        _estado.update { it.copy(errores = errores, enviando = true) }
        viewModelScope.launch {
            repo.guardarSolicitud(
                SolicitudContactoEntity(
                    contenidoId = destino.id,
                    contenidoTitulo = destino.titulo,
                    nombre = actual.nombre.trim(),
                    correo = actual.correo.trim(),
                    perfil = actual.perfil,
                    motivo = actual.motivo.trim(),
                    firmaSimulada = actual.firmaSimulada,
                    fotoPath = actual.fotoPath,
                    latitud = actual.latitud,
                    longitud = actual.longitud,
                    creadoEn = System.currentTimeMillis()
                )
            )
            _estado.update { it.copy(enviando = false, enviado = true) }
        }
    }
}
