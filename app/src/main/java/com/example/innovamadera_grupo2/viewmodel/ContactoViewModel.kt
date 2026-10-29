package com.example.innovamadera_grupo2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.innovamadera_grupo2.model.ContactoErrores
import com.example.innovamadera_grupo2.model.ContactoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ContactoViewModel : ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(ContactoUiState())

    // Estado expuesto para la UI
    val estado: StateFlow<ContactoUiState> = _estado

    // Guarda el contenido por el que la persona quiere ser contactada
    fun onInteresChange(valor: String) {
        _estado.update { it.copy(interes = valor) }
    }

    // Actualiza el campo nombre y limpia su error
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    // Actualiza el campo correo
    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    // Actualiza el campo mensaje
    fun onMensajeChange(valor: String) {
        _estado.update { it.copy(mensaje = valor, errores = it.errores.copy(mensaje = null)) }
    }

    // Actualiza checkbox de aceptación
    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor, errores = it.errores.copy(terminos = null)) }
    }

    // Validación global del formulario: retorna true si no hay errores
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = ContactoErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!CORREO_REGEX.matches(estadoActual.correo.trim())) "Correo inválido" else null,
            mensaje = if (estadoActual.mensaje.trim().length < 10) "Escribe al menos 10 caracteres" else null,
            terminos = if (!estadoActual.aceptaTerminos) "Debes aceptar los términos" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.mensaje,
            errores.terminos
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // Deja el formulario vacío (después de enviar)
    fun limpiarFormulario() {
        _estado.value = ContactoUiState()
    }

    private companion object {
        val CORREO_REGEX = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    }
}
