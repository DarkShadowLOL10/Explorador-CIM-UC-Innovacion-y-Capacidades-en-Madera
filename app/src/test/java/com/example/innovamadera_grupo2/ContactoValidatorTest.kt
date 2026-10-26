package com.example.innovamadera_grupo2

import com.example.innovamadera_grupo2.data.CatalogoSintetico
import com.example.innovamadera_grupo2.utils.ContactoValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/** Pruebas de la lógica de validación (IE 2.2.1): corre en el PC, sin emulador. */
class ContactoValidatorTest {

    @Test fun nombreVacio_esInvalido() = assertNotNull(ContactoValidator.validarNombre(""))
    @Test fun nombreCorto_esInvalido() = assertNotNull(ContactoValidator.validarNombre("Al"))
    @Test fun nombreValido_noTieneError() = assertNull(ContactoValidator.validarNombre("Ana Pérez"))

    @Test fun correoSinArroba_esInvalido() = assertNotNull(ContactoValidator.validarCorreo("ana.cl"))
    @Test fun correoSinDominio_esInvalido() = assertNotNull(ContactoValidator.validarCorreo("ana@correo"))
    @Test fun correoValido_noTieneError() = assertNull(ContactoValidator.validarCorreo("ana@correo.cl"))

    @Test fun perfilVacio_esInvalido() = assertNotNull(ContactoValidator.validarPerfil(""))

    @Test fun motivoCorto_esInvalido() = assertNotNull(ContactoValidator.validarMotivo("hola"))
    @Test fun motivoValido_noTieneError() = assertNull(ContactoValidator.validarMotivo("Me interesa colaborar"))
    @Test fun motivoMuyLargo_esInvalido() =
        assertNotNull(ContactoValidator.validarMotivo("x".repeat(ContactoValidator.MOTIVO_MAX + 1)))

    @Test fun formularioVacio_tieneErrores() =
        assertTrue(ContactoValidator.validarTodo("", "", "", "").hayErrores)

    @Test fun formularioCompleto_noTieneErrores() = assertFalse(
        ContactoValidator.validarTodo("Ana Pérez", "ana@correo.cl", "Empresa o industria", "Me interesa colaborar").hayErrores
    )

    @Test fun catalogo_relacionesApuntanAContenidosExistentes() {
        val ids = CatalogoSintetico.contenidos.map { it.id }.toSet()
        assertTrue(CatalogoSintetico.contenidos.all { c -> c.relacionados.all { it in ids } })
    }
}
