package com.example.innovamadera_grupo2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovamadera_grupo2.data.CimRepository
import com.example.innovamadera_grupo2.model.CimDatabase
import com.example.innovamadera_grupo2.model.SolicitudContactoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SolicitudesViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = CimRepository(CimDatabase.getInstance(application).cimDao())

    val solicitudes: StateFlow<List<SolicitudContactoEntity>> =
        repo.solicitudes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun eliminar(id: Int) {
        viewModelScope.launch { repo.eliminarSolicitud(id) }
    }
}
