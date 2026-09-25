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

## Desarrollo (Instrucciones de Compilación y Ejecución)

### 1. Flutter (Terminado)
1. Asegurarse de tener Flutter instalado y configurado.
2. Abrir una terminal en la carpeta `flutter/`.
3. Ejecutar `flutter pub get` para descargar las dependencias.
4. Ejecutar `flutter run` para iniciar la aplicación en el emulador o dispositivo activo.

### 2. Android (Views y XML) - (En proceso)
1. Abrir la carpeta `android-views/` en Android Studio.
2. Sincronizar el proyecto con Gradle.
3. Ejecutar la aplicación en el dispositivo físico o emulador.

### 3. Android (Jetpack Compose) - (En proceso)
1. Abrir la carpeta `android-compose/` en Android Studio.
2. Sincronizar el proyecto con Gradle.
3. Ejecutar la aplicación en el dispositivo físico o emulador.

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
| Separador | `View` | `Divider` | `Divider` |
| Filas y Columnas | `LinearLayout` | `Row` / `Column` | `Row` / `Column` |
| Superposición | `FrameLayout` | `Box` | `Stack` |
| Desplazamiento vertical | `ScrollView` | `Modifier.verticalScroll()` | `SingleChildScrollView` |
| Barra superior | `Toolbar` / `MaterialToolbar` | `TopAppBar` | `AppBar` |
| Navegación inferior | `BottomNavigationView` | `NavigationBar` | `BottomNavigationBar` |
| Pesos proporcionales | `layout_weight` | `Modifier.weight()` | `Expanded` / `Flexible` |

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
- Zelda Wiki. (2026). *The Minish Cap Map Data*. Recuperado de https://zeldawiki.wiki

## Recursos Compartidos (Assets y Datos)
Para mantener consistencia entre las tres versiones de la aplicación, los recursos visuales e información se encuentran centralizados:
- **`shared_assets/`**: Contiene las imágenes de los mapas (pixel art y satélite) y sub-mapas en formato WebP y JPG.
- **`docs/minish-cap-mapa.md`**: Contiene el documento base producto de Web Scraping, que incluye todas las coordenadas de las regiones, listado de objetos, piezas de corazón e inventario general usado en las tres aplicaciones.
