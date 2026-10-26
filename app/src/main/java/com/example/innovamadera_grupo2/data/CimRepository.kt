package com.example.innovamadera_grupo2.data

import com.example.innovamadera_grupo2.model.CimDao
import com.example.innovamadera_grupo2.model.Contenido
import com.example.innovamadera_grupo2.model.FavoritoEntity
import com.example.innovamadera_grupo2.model.SolicitudContactoEntity
import com.example.innovamadera_grupo2.model.TipoContenido
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** Única puerta de acceso a datos para los ViewModels (catálogo en memoria + Room). */
class CimRepository(private val dao: CimDao) {

    val catalogo: List<Contenido> = CatalogoSintetico.contenidos

    val favoritosIds: Flow<Set<String>> = dao.observarFavoritos().map { it.toSet() }
    val solicitudes: Flow<List<SolicitudContactoEntity>> = dao.observarSolicitudes()

    /** RF-03/RF-04/RF-05: busca por título, descripción, tipo, área y etiquetas; filtra por tipo. */
    fun buscar(consulta: String, tipo: TipoContenido?): List<Contenido> {
        val q = consulta.trim().lowercase()
        return catalogo.filter { c ->
            val coincideTipo = tipo == null || c.tipo == tipo
            val textos = listOf(c.titulo, c.descripcion, c.tipo.etiqueta, c.area) + c.etiquetas
            coincideTipo && (q.isEmpty() || textos.any { it.lowercase().contains(q) })
        }
    }

    fun porId(id: String): Contenido? = catalogo.firstOrNull { it.id == id }

    fun relacionados(contenido: Contenido): List<Contenido> =
        contenido.relacionados.mapNotNull { porId(it) }

    suspend fun alternarFavorito(id: String, esFavorito: Boolean) {
        if (esFavorito) dao.quitarFavorito(id) else dao.agregarFavorito(FavoritoEntity(id))
    }

    suspend fun guardarSolicitud(solicitud: SolicitudContactoEntity) {
        dao.insertarSolicitud(solicitud)
    }

    suspend fun eliminarSolicitud(id: Int) = dao.eliminarSolicitud(id)
}
