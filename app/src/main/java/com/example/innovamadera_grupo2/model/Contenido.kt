package com.example.innovamadera_grupo2.model

// Tipos de contenido que muestra el catálogo del CIM UC.
enum class TipoContenido(val etiqueta: String, val plural: String) {
    PROYECTO("Proyecto", "Proyectos"),
    CAPACIDAD("Capacidad I+D", "Capacidades"),
    INTEGRANTE("Integrante", "Integrantes"),
    PUBLICACION("Publicación", "Publicaciones"),
    ACTIVIDAD("Actividad", "Actividades"),
    FORMACION("Formación", "Formación"),
    COLABORACION("Colaboración", "Colaboración")
}

// Un elemento del catálogo (todos los datos son ficticios).
data class Contenido(
    val id: String,
    val tipo: TipoContenido,
    val titulo: String,
    val descripcion: String,
    val anio: String,
    val estado: String,
    val etiquetas: List<String>,
    val relacionados: List<String>   // ids de otros contenidos
)
