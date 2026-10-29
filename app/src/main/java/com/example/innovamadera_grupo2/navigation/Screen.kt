package com.example.innovamadera_grupo2.navigation

// Sealed class para definir rutas tipo-safe en la navegación
sealed class Screen(val route: String) {

    // Rutas simples (sin argumentos)
    data object Explorar : Screen("explorar_page")
    data object Guardados : Screen("guardados_page")
    data object Perfil : Screen("perfil_page")
    data object Contacto : Screen("contacto_page")
    data object Resumen : Screen("resumen_page")

    // Ruta con argumento: el id del contenido que se quiere ver.
    data class Detalle(val itemId: String) : Screen(RUTA_DETALLE) {
        // Construye la ruta final reemplazando {itemId} por el valor real
        override fun construirRuta(): String = route.replace("{itemId}", itemId)
    }

    // Ruta final que se entrega al navController (por defecto es la misma route)
    open fun construirRuta(): String = route

    companion object {
        // Patrón de ruta usado en el NavHost
        const val RUTA_DETALLE = "detalle_page/{itemId}"
    }
}
