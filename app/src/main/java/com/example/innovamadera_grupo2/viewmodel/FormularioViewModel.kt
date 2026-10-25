package com.example.innovamadera_grupo2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.innovamadera_grupo2.model.MaderaDatabase
import com.example.innovamadera_grupo2.model.MaderaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FormularioViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = MaderaDatabase.getInstance(application).maderaDao()

    private val _tipoMadera = MutableStateFlow("")
    val tipoMadera: StateFlow<String> = _tipoMadera.asStateFlow()

    private val _errorTipo = MutableStateFlow<String?>(null)
    val errorTipo: StateFlow<String?> = _errorTipo.asStateFlow()

    fun onTipoCambio(nuevoTipo: String) {
        _tipoMadera.value = nuevoTipo
        validarFormulario()
    }

    private fun validarFormulario() {
        if (_tipoMadera.value.isBlank()) {
            _errorTipo.value = "El tipo de madera no puede estar vacío"
        } else {
            _errorTipo.value = null
        }
    }

    fun guardar() {
        if (_tipoMadera.value.isBlank()) {
            validarFormulario()
            return
        }
        viewModelScope.launch {
            dao.insertMadera(
                MaderaEntity(
                    tipo = _tipoMadera.value.trim(),
                    observaciones = "",
                    latitud = null,
                    longitud = null
                )
            )
            _tipoMadera.value = ""
        }
    }
}
