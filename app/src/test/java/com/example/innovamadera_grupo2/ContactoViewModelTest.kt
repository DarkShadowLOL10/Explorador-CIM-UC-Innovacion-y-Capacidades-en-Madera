package com.example.innovamadera_grupo2

import com.example.innovamadera_grupo2.viewmodel.ContactoViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

// Pruebas simples de la validación del formulario (Guía 11)
class ContactoViewModelTest {

    @Test
    fun formularioVacio_noEsValido_yMarcaErrores() {
        val vm = ContactoViewModel()

        assertFalse(vm.validarFormulario())

        val errores = vm.estado.value.errores
        assertNotNull(errores.nombre)
        assertNotNull(errores.correo)
        assertNotNull(errores.mensaje)
        assertNotNull(errores.terminos)
    }

    @Test
    fun correoSinArroba_esInvalido() {
        val vm = ContactoViewModel()
        vm.onNombreChange("Ana")
        vm.onCorreoChange("ana.correo.cl")
        vm.onMensajeChange("Quiero saber más del proyecto")
        vm.onAceptarTerminosChange(true)

        assertFalse(vm.validarFormulario())
        assertEquals("Correo inválido", vm.estado.value.errores.correo)
        assertNull(vm.estado.value.errores.nombre)
    }

    @Test
    fun formularioCompleto_esValido() {
        val vm = ContactoViewModel()
        vm.onNombreChange("Ana")
        vm.onCorreoChange("ana@correo.cl")
        vm.onMensajeChange("Quiero saber más del proyecto")
        vm.onAceptarTerminosChange(true)

        assertTrue(vm.validarFormulario())
    }

    @Test
    fun escribirEnUnCampo_limpiaSuError() {
        val vm = ContactoViewModel()
        vm.validarFormulario()
        assertNotNull(vm.estado.value.errores.nombre)

        vm.onNombreChange("Ana")
        assertNull(vm.estado.value.errores.nombre)
    }
}
