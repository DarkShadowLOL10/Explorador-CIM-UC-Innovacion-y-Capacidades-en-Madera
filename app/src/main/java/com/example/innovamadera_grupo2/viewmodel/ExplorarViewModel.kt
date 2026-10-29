package com.example.innovamadera_grupo2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.innovamadera_grupo2.model.ExplorarUiState
import com.example.innovamadera_grupo2.model.TipoContenido
import com.example.innovamadera_grupo2.repository.ContenidoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ExplorarViewModel : ViewModel() {

    // Estado interno mutable
    private val _estado = MutableStateFlow(
        ExplorarUiState(resultados = ContenidoRepository.obtenerTodos())
    )

    // Estado expuesto para la UI
    val estado: StateFlow<ExplorarUiState> = _estado

    // Actualiza el texto de búsqueda y recalcula los resultados
    fun onBusquedaChange(valor: String) {
        _estado.update { actualizarResultados(it.copy(busqueda = valor)) }
    }

    // Actualiza el filtro por tipo (null = todos)
    fun onTipoChange(tipo: TipoContenido?) {
        _estado.update { actualizarResultados(it.copy(tipo = tipo)) }
    }

    // Quita búsqueda y filtro
    fun limpiarFiltros() {
        _estado.update { actualizarResultados(it.copy(busqueda = "", tipo = null)) }
    }

    private fun actualizarResultados(estado: ExplorarUiState): ExplorarUiState {
        return estado.copy(resultados = ContenidoRepository.buscar(estado.busqueda, estado.tipo))
    }
}
