# Práctica: Catálogo Interactivo de Interfaz de Usuario (UI)

**Alumno:** Hervey Gabriel Gutierrez Prats  
**Boleta:** 2022630373  
**Grupo:** 7CV4  
**Asignatura:** Desarrollo de aplicaciones móviles nativas  
**Fecha de entrega:** [Fecha de entrega]  

---

## Introducción
El presente proyecto consiste en el desarrollo de un catálogo interactivo de elementos de interfaz de usuario, implementado en tres tecnologías de desarrollo móvil distintas:
1. Android nativo con Views y XML
2. Android nativo con Jetpack Compose
3. Flutter (Desarrollo Multiplataforma)

El objetivo de la práctica es identificar los componentes básicos de una interfaz móvil, comprender sus equivalencias entre plataformas y contrastar las diferencias arquitectónicas en los enfoques de desarrollo (declarativo vs. imperativo).

Para hacer la demostración más atractiva y funcional, la aplicación fue tematizada como un "Zelda Map Companion" (basado en *The Minish Cap*), incluyendo un mapa interactivo con gestos de zoom y un sistema de inventario basado en datos reales del juego.

---

## Estructura del Repositorio

```
catalogo-ui/
├── version_flutter/   # Versión Flutter
├── version_xml/       # Versión Android nativo con Views y XML
├── version_compose/   # Versión Android nativo con Jetpack Compose
├── shared_assets/     # Mapas compartidos por las tres versiones
│   ├── map.jpg              # Mapa general (pixel art)
│   ├── map_satellite.webp   # Mapa general (vista satélite)
│   └── maps/                # 16 mapas detallados por región
└── docs/
    └── minish-cap-mapa.md   # Documento base con los datos del juego
```

---

## Desarrollo (Instrucciones de Compilación y Ejecución)

Las tres versiones están terminadas y tienen las mismas pantallas: Login/Registro, Menú, Filtros, Mapa interactivo e Inventario.

### 1. Flutter
1. Asegurarse de tener Flutter instalado y configurado.
2. Abrir una terminal en la carpeta `version_flutter/`.
3. Ejecutar `flutter pub get` para descargar las dependencias.
4. Ejecutar `flutter run` para iniciar la aplicación en el emulador o dispositivo activo.
5. (Opcional) Ejecutar `flutter test` para correr las pruebas.

### 2. Android (Views y XML)
1. Abrir la carpeta `version_xml/` en Android Studio.
2. Sincronizar el proyecto con Gradle.
3. Ejecutar la aplicación en el dispositivo físico o emulador.

### 3. Android (Jetpack Compose)
1. Abrir la carpeta `version_compose/` en Android Studio.
2. Sincronizar el proyecto con Gradle.
3. Ejecutar la aplicación en el dispositivo físico o emulador.

---

## Funcionalidades Principales

- **Mapa interactivo:** mapa de Hyrule con 17 regiones táctiles, zoom con gestos y dos estilos (Pixel y Satélite, se elige en Filtros). Al tocar una región se abre una hoja inferior con su mapa detallado; los emojis sobre el mapa se pueden tocar para ver cómo conseguir cada cosa.
  - 💖 Pieza de Corazón (44) · 🧚 Hada / Gran Hada (10) · 🗡️ Mazmorra (7) · 🌀 Portal de viento (8) · 🍶 Botella vacía (4)
- **Progreso del mundo:** las regiones se pueden marcar como completadas; la barra de progreso y el inventario se actualizan.
- **Inventario:**
  - *Técnicas y objetos:* 28 elementos. Deslizar para marcar como dominado (con Deshacer), arrastrar hacia abajo para recargar, estado vacío y detalle al tocar.
  - *Corazones:* cuadrícula con las 44 Piezas de Corazón; se marcan como obtenidas al completar su región.
  - *Kinstones:* lista con encabezados por color con las 9 formas y sus 100 fusiones.
- **Modo Rúbrica:** interruptor en la barra superior que muestra qué componente de UI se usa en cada parte de la pantalla.

---

## Tabla de Equivalencias de UI

| Elemento | Views / XML | Jetpack Compose | Flutter |
|----------|-------------|-----------------|---------|
| Campo de texto simple | `EditText` / `TextInputLayout` | `TextField` | `TextField` |
| Campo de contraseña | `EditText` (inputType="textPassword") | `TextField` (visualTransformation) | `TextField` (obscureText: true) |
| Campo multilínea | `EditText` (inputType="textMultiLine") | `TextField` (singleLine: false) | `TextField` (maxLines: null) |
| Barra de búsqueda | `SearchView` | `SearchBar` / `TextField` | `SearchBar` / `TextField` |
| Botón relleno | `Button` | `Button` | `ElevatedButton` / `FilledButton` |
| Botón con contorno | `MaterialButton` (style="...OutlinedButton") | `OutlinedButton` | `OutlinedButton` |
| Botón de solo texto | `Button` (style="...TextButton") | `TextButton` | `TextButton` |
| Botón con ícono | `MaterialButton` (app:icon) | `Button` con `Icon` | `ElevatedButton.icon` |
| FAB (Acción flotante) | `FloatingActionButton` | `FloatingActionButton` | `FloatingActionButton` |
| Botón de alternancia | `MaterialButtonToggleGroup` | `SegmentedButton` | `SegmentedButton` |
| Casilla de verificación | `CheckBox` | `Checkbox` | `Checkbox` |
| Botones de opción (Radio) | `RadioGroup` / `RadioButton` | `RadioButton` | `Radio` |
| Interruptor | `Switch` | `Switch` | `Switch` |
| Deslizador (Slider) | `Slider` | `Slider` | `Slider` |
| Lista desplegable | `Spinner` / `AutoCompleteTextView` | `DropdownMenu` | `DropdownButton` |
| Selector de fecha | `DatePickerDialog` | `DatePicker` | `showDatePicker` |
| Selector de hora | `TimePickerDialog` | `TimePicker` | `showTimePicker` |
| Chips de filtro | `ChipGroup` / `Chip` | `FilterChip` | `FilterChip` |
| Lista vertical | `RecyclerView` | `LazyColumn` | `ListView.builder` |
| Cuadrícula de elementos | `RecyclerView` (GridLayoutManager) | `LazyVerticalGrid` | `GridView.builder` |
| Textos con estilos | `TextView` | `Text` | `Text` |
| Imagen | `ImageView` | `Image` | `Image.asset` / `Image.network` |
| Indicador de progreso | `ProgressBar` | `CircularProgressIndicator` / `LinearProgressIndicator` | `CircularProgressIndicator` / `LinearProgressIndicator` |
| Toast / Snackbar | `Toast` / `Snackbar` | `Snackbar` | `SnackBar` (ScaffoldMessenger) |
| Diálogo de confirmación | `AlertDialog` | `AlertDialog` | `AlertDialog` |
| Hoja inferior | `BottomSheetDialogFragment` | `ModalBottomSheet` | `showModalBottomSheet` |
| Tarjeta | `CardView` | `Card` | `Card` |
| Separador | `View` / `MaterialDivider` | `HorizontalDivider` | `Divider` |
| Filas y Columnas | `LinearLayout` | `Row` / `Column` | `Row` / `Column` |
| Superposición | `FrameLayout` | `Box` | `Stack` |
| Desplazamiento vertical | `ScrollView` | `Modifier.verticalScroll()` | `SingleChildScrollView` |
| Barra superior | `Toolbar` / `MaterialToolbar` | `TopAppBar` | `AppBar` |
| Navegación inferior | `BottomNavigationView` | `NavigationBar` | `BottomNavigationBar` |
| Pesos proporcionales | `layout_weight` | `Modifier.weight()` | `Expanded` / `Flexible` |
| Pestañas | `TabLayout` | `PrimaryTabRow` / `Tab` | `TabBar` / `TabBarView` |
| Deslizar para eliminar | `ItemTouchHelper` | `SwipeToDismissBox` | `Dismissible` |
| Arrastrar para recargar | `SwipeRefreshLayout` | `PullToRefreshBox` | `RefreshIndicator` |
| Lista con encabezados | `RecyclerView` (varios `viewType`) | `LazyColumn` (`item` + `items`) | `ListView.builder` (según el tipo) |
| Zoom con gestos | — | `detectTransformGestures` + `graphicsLayer` | `InteractiveViewer` |
| Contador sobre ícono | `TextView` | `BadgedBox` / `Badge` | `Badge` |

---

## Capturas
*(Se agregarán capturas una vez desarrolladas las pantallas para todas las tecnologías)*

### Flutter (Completado)
*   *![Login - Flutter](capturas/flutter_login.png)*
*   *![Mapa - Flutter](capturas/flutter_mapa.png)*

### Android (Views y XML)
*   *Pendiente*

### Android (Jetpack Compose)
*   *Pendiente*

---

## Comparación de Enfoques de Interfaz
*(Por completar al finalizar el desarrollo)*
- **Facilidad de desarrollo:**
- **Cantidad de código:**
- **Diseño de la interfaz:**

## Conclusiones
*(Por completar al finalizar el desarrollo)*
- **¿En cuál tecnología resultó más rápido construir la interfaz?**
- **Dificultades encontradas:**
- **Preferencia personal:**

---

## Bibliografía
- Android Developers. (s.f.). *Documentación de Android*. Recuperado de https://developer.android.com/
- Flutter. (s.f.). *Flutter Documentation*. Recuperado de https://flutter.dev/docs/
- Zelda Wiki. (2026). *The Legend of Zelda: The Minish Cap* (ubicaciones, mazmorras y coleccionables). Recuperado de https://zeldawiki.wiki
- StrategyWiki. (2026). *The Legend of Zelda: The Minish Cap*. Recuperado de https://strategywiki.org/wiki/The_Legend_of_Zelda:_The_Minish_Cap

## Recursos Compartidos (Assets y Datos)
Para mantener consistencia entre las tres versiones, los mapas y los datos del juego están centralizados.

### Imágenes (`shared_assets/`)
- **XML y Compose** leen las imágenes directamente de `shared_assets/`. En cada `app/build.gradle.kts` se agrega como carpeta de assets:
  ```kotlin
  sourceSets {
      getByName("main") {
          assets.directories.add("../../shared_assets")
      }
  }
  ```
- **Flutter** no puede empaquetar archivos que estén fuera de su carpeta, así que usa una copia en `version_flutter/assets/`. Si se modifica algo en `shared_assets/`, hay que volver a copiarlo con:
  ```bash
  version_flutter/tool/sync_assets.sh
  ```

### Datos del juego
- **`docs/minish-cap-mapa.md`**: documento base obtenido por *web scraping* de Zelda Wiki y StrategyWiki. Incluye regiones, conexiones, mazmorras, las 44 Piezas de Corazón, las 100 fusiones de Kinstones y demás coleccionables.
- A partir de ese documento se crearon los archivos de datos que usa cada app (el mismo contenido en las tres):

| Datos | Flutter | XML y Compose |
|-------|---------|---------------|
| Regiones del mapa y puntos de interés | `lib/data/regions.dart` | `Data.kt` |
| Inventario (técnicas, objetos, Kinstones y corazones) | `lib/data/inventory.dart` | `Inventory.kt` |

> Las posiciones de los emojis dentro de los mapas detallados son aproximadas. Se ajustan cambiando los valores `x` y `y` (de 0 a 1) de cada punto en los archivos de datos.
