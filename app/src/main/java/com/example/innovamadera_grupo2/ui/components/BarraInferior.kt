package com.example.innovamadera_grupo2.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.innovamadera_grupo2.navigation.Screen
import com.example.innovamadera_grupo2.viewmodel.MainViewModel

private data class ItemBarra(val screen: Screen, val titulo: String, val icono: ImageVector)

private val itemsBarra = listOf(
    ItemBarra(Screen.Explorar, "Explorar", Icons.Default.Home),
    ItemBarra(Screen.Guardados, "Guardados", Icons.Default.Favorite),
    ItemBarra(Screen.Perfil, "Perfil", Icons.Default.Person)
)

// NavigationBar reutilizable para las 3 pantallas principales
@Composable
fun BarraInferior(seleccionada: Screen, mainViewModel: MainViewModel) {
    NavigationBar {
        itemsBarra.forEach { item ->
            NavigationBarItem(
                selected = item.screen == seleccionada,
                onClick = {
                    if (item.screen != seleccionada) {
                        // Volvemos a Explorar en la pila para no acumular pantallas
                        mainViewModel.navigateTo(
                            screen = item.screen,
                            popUpToRoute = Screen.Explorar,
                            singleTop = true
                        )
                    }
                },
                label = { Text(item.titulo) },
                icon = { Icon(imageVector = item.icono, contentDescription = item.titulo) }
            )
        }
    }
}
