package com.example.innovamadera_grupo2.model

/** Tipos de contenido del catálogo (ver 05-modelo-datos.md: CONTENT_TYPE). */
enum class TipoContenido(val etiqueta: String, val plural: String) {
    PROYECTO("Proyecto", "Proyectos"),
    CAPACIDAD("Capacidad I+D", "Capacidades I+D"),
    INTEGRANTE("Integrante", "Integrantes"),
    PUBLICACION("Publicación", "Publicaciones"),
    ACTIVIDAD("Actividad", "Actividades"),
    FORMACION("Formación", "Formación"),
    COLABORACION("Colaboración", "Colaboración")
}

/** Contenido del catálogo sintético (ver 05-modelo-datos.md: CONTENT). */
data class Contenido(
    val id: String,
    val tipo: TipoContenido,
    val titulo: String,
    val descripcion: String,
    val area: String,
    val anio: String,
    val estado: String,
    val etiquetas: List<String>,
    val relacionados: List<String>
)
