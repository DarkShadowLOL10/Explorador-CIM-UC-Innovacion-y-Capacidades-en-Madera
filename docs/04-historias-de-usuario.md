# 04. Historias de usuario

## Solución: Explorador CIM UC

### HU-01. Explorar contenidos

**Como** persona interesada en la innovación en madera,  
**quiero** visualizar una muestra de proyectos, capacidades, publicaciones y actividades,  
**para** conocer qué información ofrece el CIM UC.

**Criterios de aceptación:**

- Se muestra un catálogo con contenidos sintéticos.
- Cada tarjeta identifica el tipo, título y un resumen.
- La vista funciona en móvil y computador.

**Requerimientos relacionados:** RF-01, RF-02, RNF-01, RNF-02.

### HU-02. Buscar contenido

**Como** persona usuaria,  
**quiero** buscar por palabras clave,  
**para** encontrar rápidamente un proyecto, capacidad o publicación de mi interés.

**Criterios de aceptación:**

- Existe un campo de búsqueda visible.
- La búsqueda considera título, descripción, tipo y etiquetas.
- Se muestra la cantidad de resultados.
- Si no hay coincidencias, se muestra un estado vacío y una opción para limpiar.

**Requerimientos relacionados:** RF-03, RF-04, RF-06.

### HU-03. Filtrar por categoría

**Como** persona usuaria,  
**quiero** filtrar el catálogo por tipo de contenido,  
**para** concentrarme en la información que necesito.

**Criterios de aceptación:**

- Se pueden seleccionar categorías como Proyectos, Capacidades I+D, Actividades y Publicaciones.
- La categoría seleccionada queda visualmente identificada.
- El listado se actualiza sin recargar la aplicación.

**Requerimientos relacionados:** RF-05, RF-06.

### HU-04. Consultar detalle

**Como** persona usuaria,  
**quiero** abrir el detalle de un contenido,  
**para** comprender su propósito, estado y área temática.

**Criterios de aceptación:**

- El detalle muestra identificador, tipo, descripción, año o vigencia, estado y etiquetas.
- El detalle se puede cerrar y permite volver a explorar.

**Requerimientos relacionados:** RF-07, RF-08, RF-16.

### HU-05. Explorar relaciones

**Como** persona usuaria,  
**quiero** ver contenidos conectados con el que estoy consultando,  
**para** ampliar mi comprensión del ecosistema CIM UC.

**Criterios de aceptación:**

- El detalle muestra una sección “Conectado con”.
- Cada relación indica título y tipo de contenido.
- Al seleccionar una relación se abre su detalle.

**Requerimientos relacionados:** RF-09, RNF-12.

### HU-06. Contactar por interés

**Como** representante de una empresa, universidad, organismo público o comunidad interesada,  
**quiero** enviar una solicitud de contacto simulada desde un contenido,  
**para** representar el paso posterior a descubrir una oportunidad de colaboración.

**Criterios de aceptación:**

- Existe un botón “Quiero conectar”.
- El formulario solicita perfil y motivo de contacto.
- Los campos obligatorios se validan.
- Se muestra una confirmación después del envío.
- La pantalla informa que se trata de una simulación.

**Requerimientos relacionados:** RF-11, RF-12, RF-13, RF-15, RF-17, RNF-09, RNF-11.

### HU-07. Simular firma

**Como** persona usuaria que desea completar el flujo de contacto,  
**quiero** activar una opción de firma electrónica simulada,  
**para** representar un mecanismo externo sin utilizar credenciales reales.

**Criterios de aceptación:**

- El formulario presenta una opción claramente etiquetada como simulación.
- Activarla no solicita certificados ni credenciales.
- El resultado indica que no tiene validez legal.

**Requerimientos relacionados:** RF-14, RNF-08, RNF-11.

## Flujo completo priorizado

**HU-01 -> HU-02 o HU-03 -> HU-04 -> HU-05 -> HU-06 -> confirmación**
