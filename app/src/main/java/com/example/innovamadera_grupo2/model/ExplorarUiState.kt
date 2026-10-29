package com.example.innovamadera_grupo2.model

// Estado de la pantalla Explorar: lo que escribió el usuario y los resultados.
data class ExplorarUiState(
    val busqueda: String = "",
    val tipo: TipoContenido? = null,      // null = todos los tipos
    val resultados: List<Contenido> = emptyList()
)
