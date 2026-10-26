package com.example.innovamadera_grupo2.utils

/** Errores por campo del formulario de contacto (null = campo válido). */
data class ContactoErrores(
    val nombre: String? = null,
    val correo: String? = null,
    val perfil: String? = null,
    val motivo: String? = null
) {
    val hayErrores: Boolean
        get() = nombre != null || correo != null || perfil != null || motivo != null
}

/**
 * Lógica de validación CENTRALIZADA y desacoplada de la interfaz (IE 2.2.1).
 * Es Kotlin puro: no depende de Compose ni de Android, por eso se puede probar con tests unitarios.
 */
object ContactoValidator {
    const val NOMBRE_MIN = 3
    const val MOTIVO_MIN = 10
    const val MOTIVO_MAX = 300

    private val EMAIL = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun validarNombre(valor: String): String? = when {
        valor.isBlank() -> "Ingresa tu nombre"
        valor.trim().length < NOMBRE_MIN -> "Debe tener al menos $NOMBRE_MIN caracteres"
        else -> null
    }

    fun validarCorreo(valor: String): String? = when {
        valor.isBlank() -> "Ingresa un correo (usa uno de prueba)"
        !EMAIL.matches(valor.trim()) -> "Formato inválido, ej: nombre@dominio.cl"
        else -> null
    }

    fun validarPerfil(valor: String): String? =
        if (valor.isBlank()) "Selecciona un perfil" else null

    fun validarMotivo(valor: String): String? = when {
        valor.isBlank() -> "Cuéntanos tu motivo de contacto"
        valor.trim().length < MOTIVO_MIN -> "Escribe al menos $MOTIVO_MIN caracteres (${valor.trim().length}/$MOTIVO_MIN)"
        valor.length > MOTIVO_MAX -> "Máximo $MOTIVO_MAX caracteres"
        else -> null
    }

    fun validarTodo(nombre: String, correo: String, perfil: String, motivo: String) = ContactoErrores(
        nombre = validarNombre(nombre),
        correo = validarCorreo(correo),
        perfil = validarPerfil(perfil),
        motivo = validarMotivo(motivo)
    )
}
