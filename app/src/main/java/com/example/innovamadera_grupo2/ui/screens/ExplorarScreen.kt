package com.example.innovamadera_grupo2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.innovamadera_grupo2.R
import com.example.innovamadera_grupo2.model.Contenido
import com.example.innovamadera_grupo2.model.ExplorarUiState
import com.example.innovamadera_grupo2.navigation.Screen
import com.example.innovamadera_grupo2.ui.components.BarraBusqueda
import com.example.innovamadera_grupo2.ui.components.BarraInferior
import com.example.innovamadera_grupo2.ui.components.BarraSuperior
import com.example.innovamadera_grupo2.ui.components.FiltrosHorizontales
import com.example.innovamadera_grupo2.ui.components.FiltrosVerticales
import com.example.innovamadera_grupo2.ui.components.TarjetaContenido
import com.example.innovamadera_grupo2.ui.utils.obtenerWindowSizeClass
import com.example.innovamadera_grupo2.viewmodel.ExplorarViewModel
import com.example.innovamadera_grupo2.viewmodel.FavoritosViewModel
import com.example.innovamadera_grupo2.viewmodel.MainViewModel
import kotlinx.coroutines.launch

// Pantalla principal: TopAppBar + menú lateral (Drawer) + barra inferior.
// El contenido cambia según el tamaño de pantalla (Guía 9).
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorarScreen(
    mainViewModel: MainViewModel,
    explorarViewModel: ExplorarViewModel,
    favoritosViewModel: FavoritosViewModel
) {
    val estado by explorarViewModel.estado.collectAsState()
    val favoritos by favoritosViewModel.favoritos.collectAsState()
    val windowSizeClass = obtenerWindowSizeClass()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Navega a una pantalla y cierra el menú
    fun irA(screen: Screen) {
        scope.launch { drawerState.close() }
        mainViewModel.navigateTo(screen)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo CIM UC",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Menú",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Explorar") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Guardados") },
                    selected = false,
                    onClick = { irA(Screen.Guardados) }
                )
                NavigationDrawerItem(
                    label = { Text("Contacto") },
                    selected = false,
                    onClick = { irA(Screen.Contacto) }
                )
                NavigationDrawerItem(
                    label = { Text("Mi perfil") },
                    selected = false,
                    onClick = { irA(Screen.Perfil) }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                BarraSuperior(
                    titulo = "Explorador CIM UC",
                    icono = Icons.Default.Menu,
                    descripcionIcono = "Menú",
                    onIconoClick = { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = { BarraInferior(Screen.Explorar, mainViewModel) }
        ) { innerPadding ->
            val modificador = Modifier
                .padding(innerPadding)
                .fillMaxSize()

            val idsFavoritos = favoritos
            if (idsFavoritos == null) {
                // Simulación de carga inicial (Guía 12)
                Box(modifier = modificador, contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val onContenidoClick: (Contenido) -> Unit = {
                    mainViewModel.navigateTo(Screen.Detalle(it.id))
                }
                // Un diseño distinto para cada tamaño de pantalla
                when (windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact -> ExplorarCompacta(
                        modificador, estado, idsFavoritos, explorarViewModel, onContenidoClick
                    )
                    WindowWidthSizeClass.Medium -> ExplorarMediana(
                        modificador, estado, idsFavoritos, explorarViewModel, onContenidoClick
                    )
                    else -> ExplorarExpandida(
                        modificador, estado, idsFavoritos, explorarViewModel, onContenidoClick
                    )
                }
            }
        }
    }
}

// Teléfono: búsqueda + filtros horizontales + lista de 1 columna
@Composable
fun ExplorarCompacta(
    modifier: Modifier,
    estado: ExplorarUiState,
    favoritos: Set<String>,
    viewModel: ExplorarViewModel,
    onContenidoClick: (Contenido) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        BarraBusqueda(
            texto = estado.busqueda,
            onTextoChange = viewModel::onBusquedaChange,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        )
        FiltrosHorizontales(estado.tipo, viewModel::onTipoChange)
        Text(
            text = "${estado.resultados.size} resultados",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ListaResultados(estado.resultados, 1, favoritos, onContenidoClick, viewModel::limpiarFiltros)
    }
}

// Tablet pequeña: lo mismo, pero con una grilla de 2 columnas
@Composable
fun ExplorarMediana(
    modifier: Modifier,
    estado: ExplorarUiState,
    favoritos: Set<String>,
    viewModel: ExplorarViewModel,
    onContenidoClick: (Contenido) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        BarraBusqueda(
            texto = estado.busqueda,
            onTextoChange = viewModel::onBusquedaChange,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
        )
        FiltrosHorizontales(estado.tipo, viewModel::onTipoChange)
        Text(
            text = "${estado.resultados.size} resultados",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        ListaResultados(estado.resultados, 2, favoritos, onContenidoClick, viewModel::limpiarFiltros)
    }
}

// Tablet grande / escritorio: panel lateral con búsqueda y filtros + grilla de 3 columnas
@Composable
fun ExplorarExpandida(
    modifier: Modifier,
    estado: ExplorarUiState,
    favoritos: Set<String>,
    viewModel: ExplorarViewModel,
    onContenidoClick: (Contenido) -> Unit
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .width(280.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BarraBusqueda(
                texto = estado.busqueda,
                onTextoChange = viewModel::onBusquedaChange
            )
            FiltrosVerticales(estado.tipo, viewModel::onTipoChange)
            Text(
                text = "${estado.resultados.size} resultados",
                style = MaterialTheme.typography.labelLarge
            )
        }
        ListaResultados(estado.resultados, 3, favoritos, onContenidoClick, viewModel::limpiarFiltros)
    }
}

// Grilla de tarjetas (con 1 columna se ve como una lista) y estado vacío
@Composable
fun ListaResultados(
    resultados: List<Contenido>,
    columnas: Int,
    favoritos: Set<String>,
    onContenidoClick: (Contenido) -> Unit,
    onLimpiar: () -> Unit
) {
    if (resultados.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No hay resultados para tu búsqueda.")
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(onClick = onLimpiar) { Text("Limpiar filtros") }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnas),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(resultados, key = { it.id }) { contenido ->
                TarjetaContenido(
                    contenido = contenido,
                    esFavorito = contenido.id in favoritos,
                    onClick = { onContenidoClick(contenido) }
                )
            }
        }
    }
}
