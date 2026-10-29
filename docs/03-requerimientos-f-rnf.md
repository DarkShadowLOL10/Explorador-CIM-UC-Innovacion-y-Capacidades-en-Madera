# 03. Requerimientos funcionales y no funcionales

## 1. Requerimientos funcionales

| ID | Requerimiento | Prioridad | Trazabilidad |
|---|---|---|---|
| RF-01 | El sistema deberá mostrar un catálogo de contenidos sintéticos del CIM UC. | Must | RAL-01 |
| RF-02 | El sistema deberá clasificar cada contenido por tipo: proyecto, capacidad I+D, integrante, publicación, actividad, formación o colaboración. | Must | RAL-01, RAL-03 |
| RF-03 | La persona usuaria deberá poder ingresar una palabra o frase en el buscador. | Must | RAL-02 |
| RF-04 | El sistema deberá filtrar los contenidos cuyo título, descripción, tipo o etiquetas coincidan con la búsqueda. | Must | RAL-02 |
| RF-05 | La persona usuaria deberá poder seleccionar una categoría para filtrar el catálogo. | Must | RAL-03 |
| RF-06 | El sistema deberá mostrar la cantidad de resultados disponibles. | Should | RAL-02, RAL-03 |
| RF-07 | La persona usuaria deberá poder abrir la ficha de detalle de un contenido. | Must | RAL-04 |
| RF-08 | La ficha deberá mostrar identificador, tipo, título, descripción, año o vigencia, estado y etiquetas cuando correspondan. | Must | RAL-04 |
| RF-09 | La ficha deberá mostrar los contenidos relacionados y permitir navegar hacia ellos. | Must | RAL-05 |
| RF-10 | El sistema deberá mostrar contenidos de formación y oportunidades de colaboración dentro del catálogo. | Should | RAL-06 |
| RF-11 | La persona usuaria deberá poder abrir un formulario de contacto simulado desde un contenido. | Must | RAL-07 |
| RF-12 | El formulario deberá solicitar perfil de usuario y motivo de contacto. | Must | RAL-07 |
| RF-13 | El sistema deberá validar que los campos obligatorios del formulario estén completos. | Must | RAL-07 |
| RF-14 | El sistema deberá permitir marcar la simulación de firma electrónica avanzada. | Could | RAL-08 |
| RF-15 | El sistema deberá mostrar una confirmación cuando la solicitud simulada sea enviada. | Must | RAL-07 |
| RF-16 | El sistema deberá permitir cerrar modales y volver al catálogo sin perder el contexto principal. | Should | RAL-01, RAL-04 |
| RF-17 | El sistema no deberá enviar la información a sistemas productivos ni a destinatarios reales. | Must | RAL-09 |

## 2. Requerimientos no funcionales

| ID | Categoría | Requerimiento | Prioridad |
|---|---|---|---|
| RNF-01 | Usabilidad | La interfaz deberá priorizar una navegación simple, legible y comprensible desde una pantalla táctil. | Must |
| RNF-02 | Responsive | La solución deberá adaptarse a teléfonos pequeños, teléfonos grandes, tablets y computadores. | Must |
| RNF-03 | Compatibilidad | Deberá funcionar en versiones actuales de Chrome, Edge, Firefox y Safari. | Should |
| RNF-04 | Orientación | La interfaz deberá ser utilizable en orientación vertical y horizontal. | Should |
| RNF-05 | Accesibilidad | Los controles deberán tener nombres accesibles, contraste suficiente y áreas táctiles adecuadas. | Must |
| RNF-06 | Rendimiento | La pantalla inicial deberá cargar el catálogo local sin depender de una API externa. | Must |
| RNF-07 | Disponibilidad | La aplicación deberá poder consultar el catálogo sin conexión después de cargar el app shell. | Should |
| RNF-08 | Seguridad | No se deberán utilizar credenciales, datos personales no públicos ni información confidencial. | Must |
| RNF-09 | Privacidad | El formulario de contacto será una simulación y no almacenará ni transmitirá datos reales. | Must |
| RNF-10 | Mantenibilidad | Los datos de prueba deberán estar separados de la lógica de presentación para facilitar su reemplazo posterior. | Should |
| RNF-11 | Identidad | La solución deberá comunicar claramente que el contacto y la firma son simulaciones académicas. | Must |
| RNF-12 | Escalabilidad | El modelo deberá permitir agregar nuevos tipos de contenido y relaciones sin rediseñar el flujo principal. | Should |

## 3. Reglas de negocio y restricciones

- Solo se utilizarán datos ficticios, sintéticos, públicos o anonimizados.
- El contacto no representa una solicitud institucional real.
- La firma electrónica avanzada no tiene validez legal.
- La aplicación es un MVP académico y no reemplaza el sitio institucional.
- No se requiere autenticación para consultar contenidos.
