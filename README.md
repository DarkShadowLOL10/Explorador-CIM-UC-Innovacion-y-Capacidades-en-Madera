# Explorador CIM UC (versión simplificada)

App Android (Kotlin + Jetpack Compose + MVVM) para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles**.
Permite explorar un catálogo **ficticio** del Centro UC de Innovación en Madera (CIM UC), guardar favoritos,
ver el detalle de cada contenido, cambiar la foto de perfil y enviar un contacto **simulado**.

> Todos los datos son ficticios. El contacto no se envía a nadie.

## Pantallas

| Pantalla | Qué muestra | Guía |
|---|---|---|
| **Explorar** | TopAppBar + menú lateral + barra inferior, buscador, filtros y tarjetas. Cambia de diseño según el tamaño de pantalla. | 8, 9, 10 |
| **Detalle** | Datos del contenido, botón *Guardar* animado y contenidos relacionados. | 10, 12 |
| **Guardados** | Favoritos guardados con DataStore. | 12 |
| **Mi perfil** | Foto circular desde galería o cámara. | 13 |
| **Contacto** | Formulario con validaciones por campo. | 11 |
| **Resumen** | Datos ingresados (mismo ViewModel) con check animado. | 11, 12 |

## Estructura del proyecto (MVVM)

```
app/src/main/java/com/example/innovamadera_grupo2/
├── MainActivity.kt            Llama a AppNavigation()
├── model/                     Contenido, ExplorarUiState, ContactoUiState (data class)
├── repository/                ContenidoRepository (catálogo ficticio local)
├── data/                      FavoritosDataStore (DataStore Preferences)
├── navigation/                Screen (rutas), NavigationEvent, AppNavigation (NavHost)
├── viewmodel/                 MainViewModel (eventos de navegación), ExplorarViewModel,
│                              FavoritosViewModel, ContactoViewModel, PerfilViewModel
└── ui/
    ├── theme/                 Colores, tipografía y tema Material 3
    ├── utils/                 WindowSizeUtils (tamaño de pantalla), ImagenUtils (Uri de la cámara)
    ├── components/            BarraSuperior, BarraInferior, TarjetaContenido, filtros, ImagenInteligente
    └── screens/               Pantallas (solo dibujan el estado que expone el ViewModel)
```

## Cómo se aplica cada guía

- **Guía 7 – Configuración MVVM:** carpetas `ui/`, `viewmodel/`, `model/`, `repository/` y nombre `InnovaMadera_Grupo2`.
- **Guía 8 – Pantalla base:** `Scaffold` + `TopAppBar` + `Column`/`Row`/`Text`/`Button`/`Image` (logo en `res/drawable/logo.xml`, visible en el menú lateral) con `Modifier`.
- **Guía 9 – Adaptabilidad:** `ui/utils/WindowSizeUtils.kt` y `ExplorarScreen.kt` (`ExplorarCompacta`, `ExplorarMediana`, `ExplorarExpandida`).
- **Guía 10 – Navegación:** `Screen` (sealed class), `NavigationEvent`, `MainViewModel` (`SharedFlow`), `NavHost`, `ModalNavigationDrawer` y `NavigationBar`.
- **Guía 11 – Formularios:** `ContactoUiState` / `ContactoErrores`, `ContactoViewModel.validarFormulario()`, `OutlinedTextField` con `isError` y `supportingText`, `Checkbox`; el `ResumenScreen` lee el mismo ViewModel.
- **Guía 12 – Animaciones y estados:** `DataStore` (favoritos), `CircularProgressIndicator` de carga, `animateColorAsState`, `derivedStateOf`, `AnimatedVisibility`, `rememberSaveable`.
- **Guía 13 – Cámara y galería:** permisos en `AndroidManifest.xml`, `FileProvider`, `GetContent()` y `TakePicture()` con `rememberLauncherForActivityResult`, componente `ImagenInteligente`.

## Cómo ejecutar

1. Abrir la carpeta en Android Studio y esperar la sincronización de Gradle (JDK 17+).
2. Elegir un emulador (Device Manager) o un teléfono real y ejecutar el módulo `app`.
3. Para ver el diseño adaptable, probar un teléfono, una tablet pequeña (Medium) y una tablet grande / escritorio (Expanded).
4. La cámara se prueba mejor en un teléfono real (en el emulador usa la cámara virtual).
5. Pruebas unitarias: `./gradlew testDebugUnitTest`.

## Trabajo colaborativo (GitHub + Trello)

Commits sugeridos por las guías: `Inicio de proyecto + estructura base MVVM`, `Pantalla HomeScreen con estructura Scaffold`,
`Navegación y menú generado`, `Formulario y resumen funcional`, `Estado y animaciones`, `Funcionalidad cámara y galería integrada`.
Tablero Trello con listas **Por hacer / En curso / Finalizado**.

## Integrantes

Equipo N°2: Carlos Pinilla · Matías Chávez · Matías Gómez · Enmanuel Chirinos

## Documentación del caso

Los documentos de análisis (replanteamiento del caso, requerimientos, historias de usuario, modelo de datos y mockups)
están en `docs/`. Se mantienen tal como se entregaron: describen el caso completo, y esta versión implementa solo una parte simple de él.
