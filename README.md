[README.md](https://github.com/user-attachments/files/32768128/README.md)
# Unidad 5: Cómo conectarse a Internet (Android Basics with Compose)

## Ruta 1 – Cómo obtener datos de Internet

| Carpeta | Codelab | Qué hace |
|---|---|---|
| `ruta1/01-corrutinas-playground` | Introducción a las corrutinas en el Playground de Kotlin | `Corrutinas.kt` con cada ejemplo: síncrono, `launch`, `async`, `coroutineScope`, excepciones, cancelación, jerarquía de Jobs y `Dispatchers`. Pégalo en https://play.kotlinlang.org y descomenta el ejemplo en `main()`. |
| `ruta1/02-race-tracker` | Introducción a las corrutinas en Android Studio | App *Race Tracker*: dos corredores avanzan en paralelo con `LaunchedEffect` + `coroutineScope`, se pueden pausar (cancelación) y reiniciar. Incluye pruebas con `runTest`, `advanceTimeBy` y `runCurrent`. |
| `ruta1/03-mars-photos-obtener-datos` | Cómo obtener datos de Internet | *Mars Photos* con Retrofit + kotlinx.serialization, `viewModelScope`, estados Loading/Success/Error y permiso de Internet. Muestra cuántas fotos se obtuvieron. |

## Ruta 2 – Cómo cargar y mostrar imágenes de Internet

| Carpeta | Codelab | Qué hace |
|---|---|---|
| `ruta2/01-mars-photos-repositorio-coil` | Cómo agregar el repositorio y la DI manual + Cómo cargar y mostrar imágenes | *Mars Photos* final: `MarsPhotosRepository`, `AppContainer`, `MarsPhotosApplication`, fábrica del ViewModel, cuadrícula `LazyVerticalGrid` con `AsyncImage` de Coil, botón Reintentar. Pruebas con repositorio/servicio falsos y `TestDispatcherRule`. |
| `ruta2/02-amphibians` | Práctica: Compila la app de anfibios | Lista de anfibios desde `.../amphibians` con la misma arquitectura (repositorio + DI + Coil). Incluye pruebas. |
| `ruta2/03-bookshelf` | Proyecto: Crea una app de Bookshelf | Busca en la API de Google Books (por defecto "jazz history"), pide el detalle de cada libro en paralelo con `async/awaitAll`, cambia `http` por `https` en las miniaturas y las muestra en cuadrícula. Tiene barra de búsqueda y pruebas. |


