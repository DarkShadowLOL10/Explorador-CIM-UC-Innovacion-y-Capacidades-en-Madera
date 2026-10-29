package com.example.innovamadera_grupo2.navigation

// Representa los distintos tipos de eventos de navegación
sealed class NavigationEvent {

    /**
     * Evento para navegar a un destino específico.
     * @param route destino (objeto Screen tipado)
     * @param popUpToRoute si no es null, se limpia la pila hasta esa ruta
     * @param inclusive si es true, también se elimina la ruta popUpToRoute
     * @param singleTop evita copias del mismo destino en la parte superior de la pila
     */
    data class NavigateTo(
        val route: Screen,
        val popUpToRoute: Screen? = null,
        val inclusive: Boolean = false,
        val singleTop: Boolean = false
    ) : NavigationEvent()

    // Evento para volver a la pantalla anterior
    object PopBackStack : NavigationEvent()

    // Evento para navegar "hacia arriba" en la jerarquía
    object NavigateUp : NavigationEvent()
}
