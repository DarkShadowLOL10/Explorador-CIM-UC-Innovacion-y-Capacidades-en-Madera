package com.example.innovamadera_grupo2.repository

import com.example.innovamadera_grupo2.model.Contenido
import com.example.innovamadera_grupo2.model.TipoContenido

// Repositorio: simula el origen de los datos (catálogo ficticio local).
object ContenidoRepository {

    private val contenidos: List<Contenido> = listOf(
        Contenido(
            id = "P-01", tipo = TipoContenido.PROYECTO,
            titulo = "Bosque Urbano Modular",
            descripcion = "Sistema constructivo de baja huella para densificar barrios con madera local.",
            anio = "2026", estado = "En desarrollo",
            etiquetas = listOf("Construcción", "Circularidad"),
            relacionados = listOf("M-03", "C-02", "A-01")
        ),
        Contenido(
            id = "P-04", tipo = TipoContenido.PROYECTO,
            titulo = "Madera que regenera",
            descripcion = "Piloto de reutilización de residuos de madera de obra para mobiliario urbano.",
            anio = "2025", estado = "Piloto",
            etiquetas = listOf("Circularidad", "Materiales"),
            relacionados = listOf("C-02", "PU-01", "CO-01")
        ),
        Contenido(
            id = "C-02", tipo = TipoContenido.CAPACIDAD,
            titulo = "Diseño y cálculo estructural",
            descripcion = "Modelado, simulación y verificación de estructuras de madera de mediana altura.",
            anio = "2024", estado = "Vigente",
            etiquetas = listOf("Construcción", "Materiales"),
            relacionados = listOf("P-01", "M-03", "P-04", "F-01", "CO-01")
        ),
        Contenido(
            id = "M-03", tipo = TipoContenido.INTEGRANTE,
            titulo = "Laboratorio de Sistemas Constructivos",
            descripcion = "Equipo ficticio de investigación aplicada en sistemas constructivos con madera.",
            anio = "2023", estado = "Activo",
            etiquetas = listOf("Construcción", "Comunidad"),
            relacionados = listOf("P-01", "C-02", "PU-01")
        ),
        Contenido(
            id = "PU-01", tipo = TipoContenido.PUBLICACION,
            titulo = "Guía de circularidad en obra",
            descripcion = "Documento técnico de ejemplo sobre aprovechamiento de residuos de madera.",
            anio = "2025", estado = "Publicada",
            etiquetas = listOf("Circularidad", "Materiales"),
            relacionados = listOf("P-04", "M-03")
        ),
        Contenido(
            id = "A-01", tipo = TipoContenido.ACTIVIDAD,
            titulo = "Jornada de construcción en madera",
            descripcion = "Encuentro abierto con demostraciones de prototipos y charlas técnicas (ficticio).",
            anio = "2026", estado = "Próxima",
            etiquetas = listOf("Comunidad", "Construcción"),
            relacionados = listOf("P-01", "F-01")
        ),
        Contenido(
            id = "F-01", tipo = TipoContenido.FORMACION,
            titulo = "Taller de diseño con madera",
            descripcion = "Programa breve de formación en fundamentos de diseño y construcción con madera.",
            anio = "2026", estado = "Inscripciones abiertas",
            etiquetas = listOf("Formación", "Construcción"),
            relacionados = listOf("A-01", "C-02")
        ),
        Contenido(
            id = "CO-01", tipo = TipoContenido.COLABORACION,
            titulo = "Alianza para vivienda sustentable",
            descripcion = "Oportunidad ficticia de colaboración entre empresas y el centro para pilotos de vivienda en madera.",
            anio = "2026", estado = "Abierta",
            etiquetas = listOf("Colaboración", "Construcción"),
            relacionados = listOf("P-04", "C-02")
        )
    )

    fun obtenerTodos(): List<Contenido> = contenidos

    fun obtenerPorId(id: String): Contenido? = contenidos.find { it.id == id }

    fun obtenerRelacionados(contenido: Contenido): List<Contenido> =
        contenido.relacionados.mapNotNull { obtenerPorId(it) }

    // Busca por texto (título, descripción, etiquetas) y filtra por tipo.
    fun buscar(texto: String, tipo: TipoContenido?): List<Contenido> {
        val consulta = texto.trim().lowercase()
        return contenidos.filter { c ->
            val coincideTipo = tipo == null || c.tipo == tipo
            val coincideTexto = consulta.isEmpty() ||
                c.titulo.lowercase().contains(consulta) ||
                c.descripcion.lowercase().contains(consulta) ||
                c.etiquetas.any { it.lowercase().contains(consulta) }
            coincideTipo && coincideTexto
        }
    }
}
