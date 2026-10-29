package com.example.innovamadera_grupo2.model

// Modelo principal que representa el estado del formulario de contacto
data class ContactoUiState(
    val interes: String = "",               // contenido que motivó el contacto
    val nombre: String = "",
    val correo: String = "",
    val mensaje: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: ContactoErrores = ContactoErrores()
)

// Errores de validación por campo (null = sin error)
data class ContactoErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val mensaje: String? = null,
    val terminos: String? = null
)
