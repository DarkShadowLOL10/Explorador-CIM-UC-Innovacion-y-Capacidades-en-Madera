# 01. Replanteamiento del caso

## Checkpoint VcM - EV2

**Asignatura:** DSY1105 - Desarrollo de Aplicaciones Móviles  
**Organización vinculada:** Centro UC de Innovación en Madera (CIM UC)  
**Equipo:** [Completar con los integrantes]  
**Fecha:** 28 de septiembre de 2026  

## 1. Nombre de la propuesta

**Explorador CIM UC: innovación y capacidades en madera**

## 2. Problema que se desea resolver

El Centro UC de Innovación en Madera (CIM UC) reúne información de distinta naturaleza: proyectos de investigación aplicada, capacidades I+D, integrantes, publicaciones, actividades, programas de formación y oportunidades de colaboración.

Actualmente, para una persona externa al Centro puede resultar difícil identificar rápidamente qué información existe, cómo se relacionan los contenidos y cuál es el siguiente paso según su interés. La diversidad de públicos y tipos de información puede generar una experiencia fragmentada, especialmente cuando la consulta se realiza desde un teléfono móvil.

El problema que abordará el equipo es la **dificultad para descubrir, filtrar y relacionar información relevante del CIM UC desde una experiencia móvil clara y simple**.

La solución no busca reemplazar el sitio institucional ni construir un sistema productivo. Busca demostrar, mediante un MVP académico, cómo una aplicación móvil puede orientar a una persona desde un interés inicial hacia contenidos pertinentes y una oportunidad de contacto simulada.

## 3. Usuarios o personas afectadas

La propuesta considera los siguientes perfiles de referencia:

- **Empresas e industrias:** buscan capacidades, proyectos o posibles colaboraciones para abordar desafíos de construcción y materiales en madera.
- **Universidades y centros de investigación:** buscan conocer proyectos, publicaciones, integrantes y capacidades de investigación aplicada.
- **Organismos públicos:** requieren identificar conocimiento, actividades o iniciativas relacionadas con innovación y construcción sustentable.
- **Estudiantes y profesionales:** buscan programas de formación, publicaciones y oportunidades para ampliar sus conocimientos.
- **Comunidad vinculada al ecosistema de innovación:** desea conocer actividades, proyectos y avances del Centro.

Para el MVP se priorizará el perfil de **persona interesada en conocer las capacidades y proyectos del CIM UC**, sin exigir autenticación ni distinguir permisos entre tipos de usuario.

## 4. Propósito de la solución

Diseñar y validar una experiencia móvil que permita a una persona:

1. Explorar una muestra de contenidos del CIM UC.
2. Buscar información mediante palabras clave.
3. Filtrar contenidos por categoría.
4. Consultar el detalle de un proyecto, capacidad, integrante, publicación, actividad o programa.
5. Visualizar relaciones entre contenidos vinculados.
6. Identificar oportunidades de formación o colaboración.
7. Enviar una solicitud de contacto simulada según su interés.

El propósito principal es **facilitar el descubrimiento guiado de conocimiento, capacidades y oportunidades del CIM UC desde teléfonos, tablets y computadores**.

## 5. Propuesta de solución

El equipo propone desarrollar **Explorador CIM UC**, un MVP responsive y orientado a dispositivos móviles.

La experiencia comenzará con una pantalla de exploración que mostrará contenidos sintéticos organizados por categorías. La persona usuaria podrá utilizar una búsqueda o filtros para reducir los resultados. Al seleccionar un contenido, accederá a una ficha de detalle con su descripción, estado, etiquetas y vínculos con otros contenidos.

Desde la ficha de detalle podrá continuar navegando hacia proyectos, capacidades, integrantes, publicaciones o actividades relacionadas. Cuando identifique un tema de interés, podrá abrir un formulario de contacto simulado. Este formulario solicitará un perfil, un motivo de contacto y permitirá representar opcionalmente una firma electrónica avanzada.

La aplicación utilizará exclusivamente información ficticia, sintética, pública o debidamente anonimizada. No tendrá conexión con sistemas productivos del CIM UC ni solicitará credenciales reales.

## 6. Alcance actual del MVP

### Incluido

- Catálogo sintético de proyectos, capacidades I+D, integrantes, publicaciones, actividades, formación y colaboración.
- Pantalla principal de exploración.
- Búsqueda por palabras clave.
- Filtros por tipo de contenido.
- Listado de resultados.
- Ficha de detalle de cada contenido.
- Etiquetas de área temática, año y estado cuando corresponda.
- Relaciones básicas entre contenidos.
- Navegación desde un contenido hacia sus contenidos relacionados.
- Formulario de contacto simulado.
- Simulación opcional de firma electrónica avanzada.
- Mensaje de confirmación posterior al envío.
- Diseño responsive para teléfonos, tablets y computadores.
- Datos locales de prueba y funcionamiento sin conexión mediante PWA.

### Flujo principal considerado

**Inicio / exploración -> búsqueda o filtro -> listado de resultados -> detalle de contenido -> contenidos relacionados -> contacto simulado -> confirmación**

## 7. Fuera de alcance por ahora

Quedan fuera del alcance de este checkpoint y del MVP académico:

- Reproducción completa del sitio web institucional del CIM UC.
- Conexión con bases de datos, APIs o sistemas productivos de la Pontificia Universidad Católica de Chile o del CIM UC.
- Uso de credenciales, cuentas institucionales o datos personales no públicos.
- Registro e inicio de sesión de usuarios.
- Administración de contenidos por parte del CIM UC.
- Gestión real de solicitudes, seguimiento de casos o mensajería institucional.
- Firma electrónica real, e-token real o validación legal de documentos.
- Publicación de la aplicación en tiendas como Google Play o App Store.
- Notificaciones push, analítica productiva y personalización avanzada.
- Evaluación científica o técnica de los proyectos presentados.
- Uso de información confidencial, comercial o restringida.

## 8. Decisiones y supuestos actuales

- El MVP se evaluará principalmente en teléfonos Android, pero la interfaz se diseñará para adaptarse también a iOS, tablets y computadores.
- La información se almacenará inicialmente como datos sintéticos locales para facilitar la demostración.
- El contacto será una simulación académica y no enviará información a personas reales.
- Las relaciones entre contenidos serán seleccionadas manualmente para demostrar navegación y descubrimiento.
- El equipo priorizará la claridad del flujo principal por sobre la cantidad de funcionalidades.

## 9. Resultado esperado

Al finalizar el MVP, una persona debería poder ingresar sin autenticarse, encontrar un contenido relevante del CIM UC, comprender su información principal, explorar contenidos relacionados y expresar interés mediante un contacto simulado.

El resultado esperado es una propuesta funcional, demostrable y coherente con el alcance de DSY1105, que sirva como base para continuar posteriormente con el diseño, implementación y validación de la aplicación móvil.

## 10. Relación con los siguientes productos del checkpoint

Este replanteamiento será la base para los demás entregables:

- Los requerimientos definirán las capacidades necesarias para explorar, buscar, relacionar y contactar.
- Las historias de usuario describirán las interacciones de los perfiles priorizados.
- El modelo de datos representará contenidos, categorías, relaciones y solicitudes simuladas.
- Los mockups mostrarán el flujo desde la exploración hasta la confirmación del contacto.
