# Explorador CIM UC: innovación y capacidades en madera

App móvil Android (Kotlin + Jetpack Compose) desarrollada para la asignatura **DSY1105 – Desarrollo de Aplicaciones Móviles** (Evaluación Parcial 2).
Es un MVP académico que ayuda a una persona externa a **descubrir, filtrar y relacionar** proyectos, capacidades, integrantes, publicaciones, actividades y formación del Centro UC de Innovación en Madera (CIM UC), y a **expresar interés mediante un contacto simulado**.

> Usa únicamente datos ficticios, sintéticos, públicos o anonimizados. El contacto y la firma electrónica son **simulaciones** sin validez legal ni conexión a sistemas reales.

## Integrantes

| Nombre | Rol / aportes principales |
|---|---|
| _Completar nombre y apellido_ | _Completar_ |
| _Completar nombre y apellido_ | _Completar_ |

**Equipo N°:** _completar_  ·  **Planificación (Trello):** _pegar enlace del tablero_

## Funcionalidades implementadas

- **Explorar** el catálogo con tarjetas, **búsqueda** por palabra clave y **filtros** por tipo (con contador de resultados y estado vacío).
- **Detalle** del contenido con etiquetas y sección **“Conectado con”** para navegar entre contenidos relacionados.
- **Guardados**: marcar/desmarcar favoritos (persisten en Room).
- **Contacto simulado** con formulario validado campo por campo (nombre, correo, perfil, motivo), simulación de firma electrónica y **confirmación**.
- **Mis solicitudes**: historial de solicitudes guardadas en el dispositivo (con opción de eliminar).
- **Recursos nativos**: **cámara** (foto de referencia) y **ubicación** (GPS), ambos con permisos en tiempo de ejecución y manejo de rechazo.
- **Animaciones**: transiciones entre pantallas, aparición animada de errores, contador animado, favorito con rebote, reordenamiento de listas y check de confirmación.
- **Material 3** con paleta propia (verde bosque + lima), modo claro/oscuro y grilla adaptable a tablets.

## Arquitectura (MVVM)

```
app/src/main/java/com/example/innovamadera_grupo2/
├── model/       Entidades Room, DAO, base de datos y modelos del dominio
├── data/        CimRepository (única fuente de datos) y CatalogoSintetico (datos de prueba)
├── viewmodel/   ExplorarViewModel, ContactoViewModel, SolicitudesViewModel (StateFlow)
├── utils/       ContactoValidator (validación centralizada), FotoHelper, UbicacionHelper
└── ui/
    ├── theme/       Tema Material 3
    ├── components/  Componentes reutilizables (tarjetas, campos validados, barra base)
    ├── screens/     Explorar, Detalle, Contacto, Confirmación, Guardados, Solicitudes
    └── AppNavigation.kt   NavHost + barra de navegación inferior
```

- La **validación** vive en `ContactoValidator` (Kotlin puro, con tests unitarios) y la interfaz solo dibuja el estado que expone el ViewModel.
- **Persistencia local** con Room (KSP): tablas `solicitudes` y `favoritos`.

## Cómo ejecutar

1. Requisitos: Android Studio reciente, JDK 17+ y un emulador o dispositivo con Android 7.0 (API 24) o superior.
2. Clonar el repositorio y abrir la carpeta del proyecto en Android Studio.
3. Esperar la sincronización de Gradle y ejecutar el módulo `app` (▶).
4. Para probar la ubicación en el emulador: `⋮ > Location` y definir una posición. Para la cámara, el emulador usa una cámara virtual.
5. Ver los datos guardados: `App Inspection > Database Inspector > innovamadera.db`.
6. Pruebas unitarias: `./gradlew testDebugUnitTest`.

## Flujo de trabajo del equipo

- Rama `main` protegida por revisión; cada funcionalidad se desarrolla en una rama `feature/<nombre>` y se integra mediante Pull Request revisado por la otra persona del equipo.
- Commits pequeños, en español técnico, con el formato `tipo: descripción` (ej. `feat: agrega validación de correo en el formulario de contacto`).
- Tareas y responsables en el tablero de Trello (columnas: Backlog, En progreso, En revisión, Hecho).

## Documentación del caso

`01-replanteamiento-caso.md` · `02-requerimientos-alto-nivel.md` · `03-requerimientos-f-rnf.md` · `04-historias-de-usuario.md` · `05-modelo-datos.md` · `06-mockups.md`
