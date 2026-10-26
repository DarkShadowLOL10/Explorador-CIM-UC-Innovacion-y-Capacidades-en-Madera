package com.example.innovamadera_grupo2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovamadera_grupo2.data.CimRepository
import com.example.innovamadera_grupo2.model.CimDatabase
import com.example.innovamadera_grupo2.model.Contenido
import com.example.innovamadera_grupo2.model.TipoContenido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** Estado de Explorar / Detalle / Guardados: búsqueda, filtro por tipo y favoritos. */
class ExplorarViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = CimRepository(CimDatabase.getInstance(application).cimDao())

    private val _consulta = MutableStateFlow("")
    val consulta: StateFlow<String> = _consulta.asStateFlow()

    private val _tipoSeleccionado = MutableStateFlow<TipoContenido?>(null)
    val tipoSeleccionado: StateFlow<TipoContenido?> = _tipoSeleccionado.asStateFlow()

    val tiposDisponibles: List<TipoContenido> = repo.catalogo.map { it.tipo }.distinct()

    val resultados: StateFlow<List<Contenido>> =
        combine(_consulta, _tipoSeleccionado) { q, t -> repo.buscar(q, t) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), repo.catalogo)

    val favoritosIds: StateFlow<Set<String>> =
        repo.favoritosIds.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    val favoritos: StateFlow<List<Contenido>> =
        favoritosIds.map { ids -> repo.catalogo.filter { it.id in ids } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onConsultaCambio(valor: String) { _consulta.value = valor }

    fun onTipoSeleccionado(tipo: TipoContenido?) {
        _tipoSeleccionado.value = if (_tipoSeleccionado.value == tipo) null else tipo
    }

    fun limpiarFiltros() {
        _consulta.value = ""
        _tipoSeleccionado.value = null
    }

    fun contenidoPorId(id: String): Contenido? = repo.porId(id)

    fun relacionados(contenido: Contenido): List<Contenido> = repo.relacionados(contenido)

    fun alternarFavorito(id: String) {
        viewModelScope.launch { repo.alternarFavorito(id, id in favoritosIds.value) }
    }
}
