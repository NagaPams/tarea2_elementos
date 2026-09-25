import 'package:flutter/material.dart';

import 'data/regions.dart';

// Notificadores globales
final ValueNotifier<bool> rubricModeNotifier = ValueNotifier(false);
final ValueNotifier<String> mapStyleNotifier = ValueNotifier('Pixel');
final ValueNotifier<Set<String>> completedRegionsNotifier = ValueNotifier({});

void main() {
  runApp(const MinishApp());
}

class MinishApp extends StatelessWidget {
  const MinishApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Zelda Map Companion',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0xFF2E7D32),
          secondary: const Color(0xFFFFC107),
        ),
        useMaterial3: true,
      ),
      darkTheme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: const Color(0xFF1B5E20),
          secondary: const Color(0xFFFFB300),
          brightness: Brightness.dark,
        ),
        useMaterial3: true,
      ),
      themeMode: ThemeMode.system,
      home: const LoginScreen(),
    );
  }
}

// Widget de ayuda que muestra la documentación técnica solo si el modo Rúbrica está activo
class RubricDoc extends StatelessWidget {
  final String title;
  final String description;

  const RubricDoc({super.key, required this.title, required this.description});

  @override
  Widget build(BuildContext context) {
    return ValueListenableBuilder<bool>(
      valueListenable: rubricModeNotifier,
      builder: (context, show, child) {
        if (!show) return const SizedBox.shrink();
        return Container(
          width: double.infinity,
          margin: const EdgeInsets.only(bottom: 8),
          padding: const EdgeInsets.all(8),
          decoration: BoxDecoration(
            color: Colors.blueGrey.shade50,
            border: Border.all(color: Colors.blueGrey),
            borderRadius: BorderRadius.circular(4),
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                title,
                style: const TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 12,
                  color: Colors.blueGrey,
                ),
              ),
              const SizedBox(height: 4),
              Text(
                description,
                style: const TextStyle(fontSize: 11, color: Colors.black87),
              ),
            ],
          ),
        );
      },
    );
  }
}

enum LoginMode { welcome, login, register }

// PANTALLA 1: LOGIN Y REGISTRO (CUBRE SECCIÓN 1 DE LA TAREA)
class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  LoginMode _mode = LoginMode.welcome;
  final _formKey = GlobalKey<FormState>();
  bool _obscurePassword = true;

  static const List<String> _countries = [
    'Hyrule',
    'Termina',
    'Holodrum',
    'Labrynna',
    'Koholint',
  ];

  void _navToDashboard() {
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => const MainDashboard()),
    );
  }

  void _submitForm() {
    if (_formKey.currentState!.validate()) {
      _navToDashboard();
    }
  }

  Widget _buildWelcome() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        const SizedBox(height: 40),
        const Icon(Icons.map, size: 80, color: Colors.green),
        const SizedBox(height: 16),
        const Text(
          'Minish Companion',
          textAlign: TextAlign.center,
          style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 48),
        ElevatedButton(
          onPressed: () => setState(() => _mode = LoginMode.login),
          style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.symmetric(vertical: 16),
          ),
          child: const Text('Iniciar Sesión', style: TextStyle(fontSize: 16)),
        ),
        const SizedBox(height: 16),
        OutlinedButton(
          onPressed: () => setState(() => _mode = LoginMode.register),
          style: OutlinedButton.styleFrom(
            padding: const EdgeInsets.symmetric(vertical: 16),
          ),
          child: const Text('Registrarse', style: TextStyle(fontSize: 16)),
        ),
        const SizedBox(height: 24),
        TextButton(
          onPressed: _navToDashboard,
          child: const Text(
            'Continuar como invitado',
            style: TextStyle(color: Colors.grey),
          ),
        ),
      ],
    );
  }

  Widget _buildLogin() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Row(
          children: [
            IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => setState(() => _mode = LoginMode.welcome),
            ),
            const Text(
              'Iniciar Sesión',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
          ],
        ),
        const SizedBox(height: 24),

        const RubricDoc(
          title: 'TextField (Email)',
          description: 'El inicio de sesión pide datos básicos.',
        ),
        TextFormField(
          keyboardType: TextInputType.emailAddress,
          decoration: const InputDecoration(
            labelText: 'Correo electrónico',
            border: OutlineInputBorder(),
            prefixIcon: Icon(Icons.email),
          ),
          validator: (val) =>
              val == null || val.isEmpty ? 'El correo es obligatorio' : null,
        ),
        const SizedBox(height: 16),

        const RubricDoc(
          title: 'TextField (Contraseña)',
          description: 'Campo oculto para la contraseña.',
        ),
        TextFormField(
          obscureText: _obscurePassword,
          decoration: InputDecoration(
            labelText: 'Contraseña',
            border: const OutlineInputBorder(),
            prefixIcon: const Icon(Icons.lock),
            suffixIcon: IconButton(
              icon: Icon(
                _obscurePassword ? Icons.visibility : Icons.visibility_off,
              ),
              onPressed: () =>
                  setState(() => _obscurePassword = !_obscurePassword),
            ),
          ),
          validator: (val) => val == null || val.isEmpty
              ? 'La contraseña es obligatoria'
              : null,
        ),
        const SizedBox(height: 32),

        ElevatedButton(
          onPressed: _submitForm,
          style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.symmetric(vertical: 16),
          ),
          child: const Text('Entrar', style: TextStyle(fontSize: 16)),
        ),
      ],
    );
  }

  Widget _buildRegister() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Row(
          children: [
            IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => setState(() => _mode = LoginMode.welcome),
            ),
            const Text(
              'Registro',
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
          ],
        ),
        const SizedBox(height: 24),

        const RubricDoc(
          title: 'TextField (Email y Contraseña)',
          description: 'TextFormField con validación.',
        ),
        TextFormField(
          keyboardType: TextInputType.emailAddress,
          decoration: const InputDecoration(
            labelText: 'Correo electrónico',
            border: OutlineInputBorder(),
            prefixIcon: Icon(Icons.email),
          ),
          validator: (val) => val == null || val.isEmpty ? 'Requerido' : null,
        ),
        const SizedBox(height: 16),
        TextFormField(
          obscureText: _obscurePassword,
          decoration: InputDecoration(
            labelText: 'Contraseña',
            border: const OutlineInputBorder(),
            prefixIcon: const Icon(Icons.lock),
            suffixIcon: IconButton(
              icon: Icon(
                _obscurePassword ? Icons.visibility : Icons.visibility_off,
              ),
              onPressed: () =>
                  setState(() => _obscurePassword = !_obscurePassword),
            ),
          ),
          validator: (val) => val == null || val.isEmpty ? 'Requerido' : null,
        ),
        const SizedBox(height: 16),

        const RubricDoc(
          title: 'TextField (Teléfono)',
          description: 'Despliega teclado numérico telefónico.',
        ),
        const TextField(
          keyboardType: TextInputType.phone,
          decoration: InputDecoration(
            labelText: 'Teléfono',
            border: OutlineInputBorder(),
            prefixIcon: Icon(Icons.phone),
          ),
        ),
        const SizedBox(height: 16),

        const RubricDoc(
          title: 'Autocomplete<String>',
          description: 'Muestra opciones de regiones al escribir (ej. Hyrule).',
        ),
        Autocomplete<String>(
          optionsBuilder: (textEditingValue) {
            if (textEditingValue.text.isEmpty) {
              return const Iterable<String>.empty();
            }
            return _countries.where(
              (opt) => opt.toLowerCase().contains(
                textEditingValue.text.toLowerCase(),
              ),
            );
          },
          fieldViewBuilder: (context, controller, focusNode, onFieldSubmitted) {
            return TextField(
              controller: controller,
              focusNode: focusNode,
              decoration: const InputDecoration(
                labelText: 'Región (Ej. Hyrule)',
                border: OutlineInputBorder(),
                prefixIcon: Icon(Icons.public),
              ),
            );
          },
        ),
        const SizedBox(height: 16),

        const RubricDoc(
          title: 'TextField (Multilínea)',
          description: 'Campo que crece para textos largos (maxLines: 3).',
        ),
        const TextField(
          maxLines: 3,
          decoration: InputDecoration(
            labelText: 'Breve biografía',
            border: OutlineInputBorder(),
            alignLabelWithHint: true,
          ),
        ),
        const SizedBox(height: 32),

        ElevatedButton(
          onPressed: _submitForm,
          style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.symmetric(vertical: 16),
          ),
          child: const Text('Crear cuenta', style: TextStyle(fontSize: 16)),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          _mode == LoginMode.welcome
              ? 'Bienvenido'
              : (_mode == LoginMode.login ? 'Iniciar Sesión' : 'Registro'),
        ),
        actions: [
          Row(
            children: [
              const Text('Modo Rúbrica', style: TextStyle(fontSize: 12)),
              ValueListenableBuilder<bool>(
                valueListenable: rubricModeNotifier,
                builder: (context, value, child) {
                  return Switch(
                    value: value,
                    onChanged: (val) => rubricModeNotifier.value = val,
                  );
                },
              ),
            ],
          ),
        ],
      ),
      body: SafeArea(
        child: Form(
          key: _formKey,
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(24.0),
            child: _mode == LoginMode.welcome
                ? _buildWelcome()
                : (_mode == LoginMode.login ? _buildLogin() : _buildRegister()),
          ),
        ),
      ),
    );
  }
}

// PANTALLA PRINCIPAL (DASHBOARD) QUE CONTIENE LAS OTRAS SECCIONES
class MainDashboard extends StatefulWidget {
  const MainDashboard({super.key});

  @override
  State<MainDashboard> createState() => _MainDashboardState();
}

class _MainDashboardState extends State<MainDashboard> {
  int _currentIndex = 0;

  final List<Widget> _screens = [
    const Section2MenuScreen(),
    const Section3FiltersScreen(),
    const Section56MapScreen(),
    const Section4InventoryScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Minish Companion'),
        actions: [
          Row(
            children: [
              const Text('Modo Rúbrica', style: TextStyle(fontSize: 12)),
              ValueListenableBuilder<bool>(
                valueListenable: rubricModeNotifier,
                builder: (context, value, child) {
                  return Switch(
                    value: value,
                    onChanged: (val) => rubricModeNotifier.value = val,
                  );
                },
              ),
            ],
          ),
        ],
      ),
      body: _screens[_currentIndex],
      bottomNavigationBar: NavigationBar(
        selectedIndex: _currentIndex,
        onDestinationSelected: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        destinations: const [
          NavigationDestination(icon: Icon(Icons.dashboard), label: 'Menú'),
          NavigationDestination(icon: Icon(Icons.tune), label: 'Filtros'),
          NavigationDestination(icon: Icon(Icons.map), label: 'Mapa'),
          NavigationDestination(
            icon: Icon(Icons.inventory),
            label: 'Inventario',
          ),
        ],
      ),
    );
  }
}

// PANTALLA 2: MENÚ / DASHBOARD (CUBRE SECCIÓN 2 DE LA TAREA: BOTONES Y ACCIONES)
class Section2MenuScreen extends StatefulWidget {
  const Section2MenuScreen({super.key});

  @override
  State<Section2MenuScreen> createState() => _Section2MenuScreenState();
}

class _Section2MenuScreenState extends State<Section2MenuScreen> {
  bool _isLoading = false;
  String _selectedView = 'Humano'; // Para el SegmentedButton

  void _simularCarga() async {
    setState(() => _isLoading = true);
    await Future.delayed(const Duration(seconds: 2));
    if (mounted) setState(() => _isLoading = false);
  }

  @override
  Widget build(BuildContext context) {
    return ListView(
      padding: const EdgeInsets.all(16),
      children: [
        const Text(
          'Acciones del Mapa',
          style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 16),

        // 1. Botón Relleno
        const RubricDoc(
          title: 'ElevatedButton / FilledButton',
          description: 'Botón con fondo relleno. Llama la atención y se usa para la acción más importante.',
        ),
        ElevatedButton(
          onPressed: () {
            ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Usa el ícono de Mapa en la barra inferior para entrar.')));
          },
          style: ElevatedButton.styleFrom(
            backgroundColor: Theme.of(context).colorScheme.primary,
            foregroundColor: Theme.of(context).colorScheme.onPrimary,
            padding: const EdgeInsets.symmetric(vertical: 12),
          ),
          child: const Text(
            'Entrar al Mapa Interactivo',
            style: TextStyle(fontSize: 16),
          ),
        ),
        const SizedBox(height: 16),

        // 2. Botón de contorno
        const RubricDoc(
          title: 'OutlinedButton',
          description: 'Botón con borde, fondo transparente. Para acciones secundarias que no deben robar atención.',
        ),
        OutlinedButton(
          onPressed: () {
            ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Tus ítems están en la pestaña de Inventario.')));
          },
          child: const Text('Ver Registro de Ítems'),
        ),
        const SizedBox(height: 16),

        // 3. Botón de solo texto
        const RubricDoc(
          title: 'TextButton',
          description: 'Botón sin borde ni fondo. Usado para acciones sutiles.',
        ),
        TextButton(
          onPressed: () {
            showDialog(context: context, builder: (_) => AlertDialog(title: const Text('Créditos'), content: const Text('Zelda Map Companion\n\nDesarrollado por Hervey Gabriel Gutierrez Prats.'), actions: [TextButton(onPressed: () => Navigator.pop(context), child: const Text('Cerrar'))]));
          },
          child: const Text('Leer créditos de la app'),
        ),
        const SizedBox(height: 24),

        // 4. Botones con ícono
        const RubricDoc(
          title: 'IconButton y ElevatedButton.icon',
          description: 'Botones que incluyen un ícono. Ayudan a identificar rápido la acción visualmente.',
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            ElevatedButton.icon(
              onPressed: () {
                showModalBottomSheet(context: context, builder: (_) => Container(padding: const EdgeInsets.all(16), height: 200, child: const Center(child: Text('Rumor: Hay un muro misterioso en las Colinas del Este.', style: TextStyle(fontSize: 18)))));
              },
              icon: const Icon(Icons.menu_book),
              label: const Text('Notas Secretas'),
            ),
            IconButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Abriendo menú de ajustes...')));
              },
              icon: const Icon(Icons.settings),
              tooltip: 'Ajustes de Juego',
              style: IconButton.styleFrom(
                backgroundColor: Theme.of(context)
                    .colorScheme
                    .surfaceContainerHighest,
              ),
            ),
          ],
        ),
        const SizedBox(height: 24),

        // 5. FAB (Floating Action Button) Normal y Extendido
        const RubricDoc(
          title: 'FloatingActionButton',
          description: 'Botón de Acción Flotante. Normalmente va fijo abajo a la derecha. Puede ser normal (ícono) o extendido (ícono+texto).',
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            FloatingActionButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(const SnackBar(content: Text('Agregando marcador de destino...')));
              },
              heroTag: 'fab_normal',
              child: const Icon(Icons.add_location_alt),
            ),
            FloatingActionButton.extended(
              onPressed: () {
                showDialog(context: context, barrierDismissible: false, builder: (_) { Future.delayed(const Duration(seconds: 2), () { if (context.mounted) Navigator.pop(context); }); return const AlertDialog(content: Row(children: [CircularProgressIndicator(), SizedBox(width: 20), Text('Sincronizando...')])); });
              },
              heroTag: 'fab_extendido',
              icon: const Icon(Icons.sync),
              label: const Text('Sincronizar Nube'),
            ),
          ],
        ),
        const SizedBox(height: 24),

        // 6. Toggle / Segmented Button
        const RubricDoc(
          title: 'SegmentedButton',
          description:
              'Permite alternar entre opciones excluyentes rápidamente.',
        ),
        SegmentedButton<String>(
          segments: const [
            ButtonSegment(
              value: 'Humano',
              label: Text('Tamaño Humano'),
              icon: Icon(Icons.person),
            ),
            ButtonSegment(
              value: 'Minish',
              label: Text('Tamaño Minish'),
              icon: Icon(Icons.bug_report),
            ),
          ],
          selected: {_selectedView},
          onSelectionChanged: (Set<String> newSelection) {
            setState(() {
              _selectedView = newSelection.first;
            });
          },
        ),
        const SizedBox(height: 24),

        // 7. Botones de estado (Deshabilitado y Cargando)
        const RubricDoc(
          title: 'Botón Deshabilitado y Estado de Carga',
          description: 'Si onPressed es null, se deshabilita y pierde color. El de carga muestra un CircularProgressIndicator.',
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            const ElevatedButton(
              onPressed:
                  null, // Poner null lo deshabilita visual y funcionalmente
              child: Text('Región Bloqueada'),
            ),
            ElevatedButton(
              onPressed: _isLoading ? null : _simularCarga,
              child: _isLoading
                  ? const SizedBox(
                      width: 20,
                      height: 20,
                      child: CircularProgressIndicator(strokeWidth: 2),
                    )
                  : const Text('Descargar Mapa'),
            ),
          ],
        ),
        const SizedBox(height: 32),
      ],
    );
  }
}

// PANTALLA 3: FILTROS (CUBRE SECCIÓN 3 DE LA TAREA: ELEMENTOS DE SELECCIÓN)
class Section3FiltersScreen extends StatefulWidget {
  const Section3FiltersScreen({super.key});

  @override
  State<Section3FiltersScreen> createState() => _Section3FiltersScreenState();
}

class _Section3FiltersScreenState extends State<Section3FiltersScreen> {
  bool? _showBosses = true; // Tristate: true, false, null
  bool _proximityAlerts = false; // Sensor // Switch
  double _zoomLevel = 1.0; // Slider
  RangeValues _difficultyRange = const RangeValues(1, 5); // RangeSlider
  String _selectedRegion = 'Ciudadela de Hyrule'; // Dropdown

  final List<String> _kinstones = ['Verde', 'Roja', 'Azul'];
  final Set<String> _selectedKinstones = {'Verde'}; // FilterChips

  Future<void> _selectDate(BuildContext context) async {
    await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2100),
    );
  }

  Future<void> _selectTime(BuildContext context) async {
    await showTimePicker(context: context, initialTime: TimeOfDay.now());
  }

  @override
  Widget build(BuildContext context) {
    return ListView(
      padding: const EdgeInsets.all(16),
      children: [
        const Text(
          'Filtros del Mapa',
          style: TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 16),

        // 1. Checkbox
        const RubricDoc(
          title: 'Checkbox (Tristate)',
          description: 'Casilla de verificación. Soporta verdadero, falso y nulo (estado indeterminado, útil para "seleccionar algunos").',
        ),
        CheckboxListTile(
          title: const Text('Rastrear Fragmentos de Kinstone'),
          subtitle: const Text('Activo / Inactivo / Indeterminado'),
          value: _showBosses,
          tristate: true,
          onChanged: (bool? value) {
            setState(() {
              _showBosses = value;
            });
          },
        ),

        const Divider(),

        // 2. Radio Buttons
        const RubricDoc(
          title: 'Radio',
          description: 'Botones de opción mutuamente excluyentes. Solo uno puede estar activo en el grupo.',
        ),
        const Text(
          'Estilo de Mapa:',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        ValueListenableBuilder<String>(
          valueListenable: mapStyleNotifier,
          builder: (context, style, child) {
            return RadioGroup<String>(
              groupValue: style,
              onChanged: (val) => mapStyleNotifier.value = val!,
              child: const Column(
                children: [
                  RadioListTile<String>(
                    title: Text('Pixel Art Original'),
                    value: 'Pixel',
                  ),
                  RadioListTile<String>(
                    title: Text('Satélite Ilustrado'),
                    value: 'Satelite',
                  ),
                ],
              ),
            );
          },
        ),

        const Divider(),

        // 3. Switch
        const RubricDoc(
          title: 'Switch',
          description:
              'Interruptor para encender/apagar una configuración rápidamente.',
        ),
        SwitchListTile(
          title: const Text('Alertas de Proximidad Sonoras'),
          value: _proximityAlerts,
          onChanged: (val) => setState(() => _proximityAlerts = val),
        ),

        const Divider(),

        // 4. Sliders
        const RubricDoc(
          title: 'Slider y RangeSlider',
          description: 'Permite seleccionar un valor único (Slider) o un rango entre dos valores (RangeSlider) arrastrando el control.',
        ),
        const Text(
          'Nivel de Zoom por Defecto:',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        Slider(
          value: _zoomLevel,
          min: 1.0,
          max: 5.0,
          divisions: 4,
          label: _zoomLevel.toString(),
          onChanged: (val) => setState(() => _zoomLevel = val),
        ),
        const Text(
          'Filtro por Dificultad de Enemigos:',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        RangeSlider(
          values: _difficultyRange,
          min: 1,
          max: 10,
          divisions: 9,
          labels: RangeLabels(
            _difficultyRange.start.round().toString(),
            _difficultyRange.end.round().toString(),
          ),
          onChanged: (val) => setState(() => _difficultyRange = val),
        ),

        const Divider(),

        // 5. Dropdown
        const RubricDoc(
          title: 'DropdownButton',
          description: 'Lista desplegable para seleccionar un elemento de un menú de opciones.',
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            const Text(
              'Región Inicial:',
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            DropdownButton<String>(
              value: _selectedRegion,
              items:
                  <String>[
                        'Ciudadela de Hyrule',
                        'Monte Gongol',
                        'Lago Hylia',
                        'Pantano de Tabanta',
                      ]
                      .map(
                        (String value) => DropdownMenuItem<String>(
                          value: value,
                          child: Text(value),
                        ),
                      )
                      .toList(),
              onChanged: (val) => setState(() => _selectedRegion = val!),
            ),
          ],
        ),

        const Divider(),

        // 6. Date / Time Picker
        const RubricDoc(
          title: 'DatePicker y TimePicker',
          description: 'Llaman a diálogos nativos del sistema para seleccionar fechas y horas.',
        ),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [
            OutlinedButton.icon(
              onPressed: () => _selectDate(context),
              icon: const Icon(Icons.calendar_today),
              label: const Text('Día de Partida'),
            ),
            OutlinedButton.icon(
              onPressed: () => _selectTime(context),
              icon: const Icon(Icons.access_time),
              label: const Text('Hora'),
            ),
          ],
        ),

        const Divider(),

        // 7. Filter Chips
        const RubricDoc(
          title: 'FilterChip',
          description: 'Etiquetas compactas seleccionables, actúan como checkboxes visuales. Ideales para filtrado múltiple.',
        ),
        const Text(
          'Filtrar Piedras de la Suerte (Kinstones):',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
        Wrap(
          spacing: 8.0,
          children: _kinstones.map((kinstone) {
            return FilterChip(
              label: Text(kinstone),
              selected: _selectedKinstones.contains(kinstone),
              onSelected: (bool selected) {
                setState(() {
                  if (selected) {
                    _selectedKinstones.add(kinstone);
                  } else {
                    _selectedKinstones.remove(kinstone);
                  }
                });
              },
            );
          }).toList(),
        ),
        const SizedBox(height: 32),
      ],
    );
  }
}

// PANTALLA 4: INVENTARIO (CUBRE SECCIÓN 4 DE LA TAREA: LISTAS Y COLECCIONES)
class Section4InventoryScreen extends StatefulWidget {
  const Section4InventoryScreen({super.key});

  @override
  State<Section4InventoryScreen> createState() =>
      _Section4InventoryScreenState();
}

class _Section4InventoryScreenState extends State<Section4InventoryScreen> {
  // Lista de +15 elementos: Maestros de Espada
  List<String> _tecnicas = [
    'Ataque giratorio (Swiftblade)',
    'Rompe-rocas (Swiftblade)',
    'Ataque con carrera (Swiftblade)',
    'Estocada descendente (Swiftblade)',
    'Ataque rodando (Grayblade)',
    'Rayo del peligro (Waveblade)',
    'Rayo de espada (Grimblade)',
    'Gran ataque giratorio (Swiftblade I)',
    'Carga rápida del giro (Scarblade)',
    'Carga rápida del desdoble (Splitblade)',
    'Gran giro más largo (Greatblade)',
    'Poción azul (Syrup)',
    'Carcaj más grande (Hada Libélula)',
    'Bolsa de bombas grande (Hada Efímera)',
    'Cartera más grande (Hada Mariposa)',
    'Escudo espejo (Biggoron)'
  ];

  Future<void> _refresh() async {
    await Future.delayed(const Duration(seconds: 1));
    setState(() {
      _tecnicas = [
        'Ataque giratorio (Swiftblade)',
        'Rompe-rocas (Swiftblade)',
        'Ataque con carrera (Swiftblade)',
        'Estocada descendente (Swiftblade)',
        'Ataque rodando (Grayblade)',
        'Rayo del peligro (Waveblade)',
        'Rayo de espada (Grimblade)',
        'Gran ataque giratorio (Swiftblade I)',
        'Carga rápida del giro (Scarblade)',
        'Carga rápida del desdoble (Splitblade)',
        'Gran giro más largo (Greatblade)',
        'Poción azul (Syrup)',
        'Carcaj más grande (Hada Libélula)',
        'Bolsa de bombas grande (Hada Efímera)',
        'Cartera más grande (Hada Mariposa)',
        'Escudo espejo (Biggoron)'
      ];
    });
  }

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 3,
      child: Column(
        children: [
          const RubricDoc(
            title: 'Tabs (Pestañas)',
            description: 'Navegación horizontal deslizable entre distintas vistas (Listas, Cuadrícula, etc).',
          ),
          const TabBar(
            tabs: [
              Tab(icon: Icon(Icons.list), text: 'Técnicas'),
              Tab(icon: Icon(Icons.favorite), text: 'Corazones'),
              Tab(icon: Icon(Icons.category), text: 'Kinstones'),
            ],
          ),
          Expanded(
            child: TabBarView(
              children: [
                _buildQuestsList(),
                _buildGrid(),
                _buildSectionedList(),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildQuestsList() {
    return Column(
      children: [
        const RubricDoc(
          title: 'ListView, Swipe to Delete, Pull to Refresh',
          description: 'Desliza una técnica para marcarla como Dominada (borrar). Arrastra hacia abajo para recuperar todas (RefreshIndicator).',
        ),
        Expanded(
          child: _tecnicas.isEmpty
              ? const Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Icon(Icons.verified, size: 80, color: Colors.amber),
                      SizedBox(height: 16),
                      Text('¡Dominaste todas las técnicas!', style: TextStyle(fontSize: 18, color: Colors.grey)),
                    ],
                  ),
                )
              : RefreshIndicator(
                  onRefresh: _refresh,
                  child: ListView.builder(
                    itemCount: _tecnicas.length,
                    itemBuilder: (context, index) {
                      final tecnica = _tecnicas[index];
                      return Dismissible(
                        key: Key(tecnica),
                        background: Container(
                          color: Colors.green,
                          alignment: Alignment.centerRight,
                          padding: const EdgeInsets.only(right: 20),
                          child: const Icon(Icons.check, color: Colors.white),
                        ),
                        onDismissed: (direction) {
                          setState(() {
                            _tecnicas.removeAt(index);
                          });
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(content: Text('Técnica $tecnica aprendida')),
                          );
                        },
                        child: ListTile(
                          leading: const Icon(Icons.menu_book),
                          title: Text(tecnica),
                          subtitle: const Text('Desliza para marcar como dominada'),
                        ),
                      );
                    },
                  ),
                ),
        ),
      ],
    );
  }

  Widget _buildGrid() {
    // 44 piezas de corazón mapeadas a regiones de ejemplo.
    // Usamos el completedRegionsNotifier para pintarlas de rojo si la región fue explorada.
    List<String> heartRegions = List.filled(44, 'mount_crenel');
    heartRegions[0] = 'hyrule_town'; heartRegions[1] = 'hyrule_town'; heartRegions[2] = 'hyrule_town';
    heartRegions[3] = 'lake_hylia'; heartRegions[4] = 'lake_hylia';
    heartRegions[5] = 'north_hyrule_field'; heartRegions[6] = 'south_hyrule_field';
    heartRegions[7] = 'lon_lon_ranch'; heartRegions[8] = 'eastern_hills';
    heartRegions[9] = 'western_wood'; heartRegions[10] = 'castor_wilds';
    
    return Column(
      children: [
        const RubricDoc(
          title: 'GridView',
          description: 'Muestra las 44 piezas de corazón. Se colorean rojas si marcaste la zona como completada en el mapa.',
        ),
        Expanded(
          child: ValueListenableBuilder<Set<String>>(
            valueListenable: completedRegionsNotifier,
            builder: (context, completedRegions, child) {
              return GridView.builder(
                padding: const EdgeInsets.all(8),
                gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 4,
                  crossAxisSpacing: 8,
                  mainAxisSpacing: 8,
                ),
                itemCount: 44,
                itemBuilder: (context, index) {
                  bool isObtained = completedRegions.contains(heartRegions[index]);
                  return Card(
                    color: isObtained ? Colors.red.shade50 : Colors.grey.shade200,
                    child: Center(
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          Icon(
                            isObtained ? Icons.favorite : Icons.favorite_border, 
                            color: isObtained ? Colors.red : Colors.grey, 
                            size: 32
                          ),
                          Text(
                            '#${index + 1}',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              color: isObtained ? Colors.red.shade900 : Colors.grey,
                              fontWeight: FontWeight.bold
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                },
              );
            }
          ),
        ),
      ],
    );
  }

  Widget _buildSectionedList() {
    final items = [
      'ENCABEZADO: Verdes (Comunes)',
      'Divisoria triangular',
      'Cuadrada',
      'Punta afilada',
      'ENCABEZADO: Azules (Raras)',
      'Forma de gota',
      'Forma en L',
      'ENCABEZADO: Rojas (Muy Raras)',
      'Ondulada',
      'Dentada',
      'Sector de círculo',
      'ENCABEZADO: Doradas (Obligatorias)',
      'Rey Gustaf',
      '3 Estatuas Misteriosas (Pantano)',
      '5 Nubes Misteriosas (Cielo)',
    ];

    return Column(
      children: [
        const RubricDoc(
          title: 'Lista con Encabezados',
          description: 'Catálogo de tipos de Kinstones según el color.',
        ),
        Expanded(
          child: ListView.builder(
            itemCount: items.length,
            itemBuilder: (context, index) {
              final item = items[index];
              if (item.startsWith('ENCABEZADO:')) {
                return Container(
                  color: Colors.amber.shade100,
                  padding: const EdgeInsets.all(8),
                  child: Text(
                    item.replaceAll('ENCABEZADO: ', ''),
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 16,
                      color: Colors.amber.shade900
                    ),
                  ),
                );
              }
              return ListTile(
                leading: const Icon(Icons.star, color: Colors.amber),
                title: Text(item),
              );
            },
          ),
        ),
      ],
    );
  }
}
// PANTALLAS 5 y 6: MAPA INTERACTIVO (INFORMACIÓN Y CONTENEDORES)
class Section56MapScreen extends StatefulWidget {
  const Section56MapScreen({super.key});

  @override
  State<Section56MapScreen> createState() => _Section56MapScreenState();
}

class _Section56MapScreenState extends State<Section56MapScreen> {
  // Coordenadas normalizadas de las 17 regiones + POIs (emojis)
  void _mostrarPoi(Poi poi) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Row(
          children: [
            Text(poi.emoji, style: const TextStyle(fontSize: 28)),
            const SizedBox(width: 8),
            Expanded(child: Text(poi.title)),
          ],
        ),
        content: Text(poi.howTo),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text('Cerrar'),
          ),
        ],
      ),
    );
  }

  // Mapa detallado de la región con los emojis colocados encima; al tocar uno se explica cómo conseguirlo
  Widget _buildMapaDetallado(Region region) {
    return AspectRatio(
      aspectRatio: region.imgWidth / region.imgHeight,
      child: LayoutBuilder(
        builder: (context, constraints) {
          const markerSize = 26.0;
          final width = constraints.maxWidth;
          final height = constraints.maxHeight;

          return Stack(
            children: [
              Positioned.fill(
                child: Image.asset(region.img!, fit: BoxFit.fill),
              ),
              ...region.pois.map(
                (poi) => Positioned(
                  left: poi.x * width - markerSize / 2,
                  top: poi.y * height - markerSize / 2,
                  width: markerSize,
                  height: markerSize,
                  child: Tooltip(
                    message: poi.title,
                    child: GestureDetector(
                      onTap: () => _mostrarPoi(poi),
                      child: Container(
                        alignment: Alignment.center,
                        decoration: BoxDecoration(
                          color: Colors.white.withValues(alpha: 0.8),
                          shape: BoxShape.circle,
                          boxShadow: const [
                            BoxShadow(color: Colors.black45, blurRadius: 3),
                          ],
                        ),
                        child: Text(
                          poi.emoji,
                          style: const TextStyle(fontSize: 15),
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ],
          );
        },
      ),
    );
  }

  void _mostrarDetalle(Region region) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Abriendo zona: ${region.name}...')),
    );

    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) {
        final emojis = region.pois.map((p) => p.emoji).toList();
        return Container(
          height: MediaQuery.of(context).size.height * 0.75, // Ocupa el 75%
          decoration: BoxDecoration(
            color: Theme.of(context).colorScheme.surface,
            borderRadius: const BorderRadius.vertical(top: Radius.circular(20)),
          ),
          child: Column(
            children: [
              // Barra de título con Badge numérico
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Expanded(
                      child: Text(
                        region.name,
                        style: const TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                    ),
                    Badge(
                      label: Text('${region.pois.length}'),
                      child: const Icon(
                        Icons.star,
                        size: 30,
                        color: Colors.amber,
                      ),
                    ),
                  ],
                ),
              ),
              const Divider(),

              // Contenedor con Desplazamiento
              Expanded(
                child: SingleChildScrollView(
                  padding: const EdgeInsets.all(16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      // Tarjeta con Imagen Local (Sub-mapa detallado con marcadores)
                      Card(
                        elevation: 4,
                        child: Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            children: [
                              const Text(
                                'Mapa Detallado',
                                style: TextStyle(
                                  fontWeight: FontWeight.bold,
                                  fontSize: 16,
                                ),
                              ),
                              const SizedBox(height: 4),
                              if (region.img != null) ...[
                                const Text(
                                  'Toca un emoji para ver cómo conseguirlo',
                                  style: TextStyle(
                                    fontSize: 12,
                                    color: Colors.grey,
                                  ),
                                ),
                                const SizedBox(height: 8),
                                _buildMapaDetallado(region),
                              ] else
                                const Text(
                                  'Mapa detallado no subido aún',
                                  style: TextStyle(
                                    color: Colors.grey,
                                    fontStyle: FontStyle.italic,
                                  ),
                                ),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(height: 16),

                      // Diseño de pesos proporcionales (Expanded)
                      Card(
                        elevation: 4,
                        color: Colors.green.shade50,
                        child: Padding(
                          padding: const EdgeInsets.all(16.0),
                          child: Row(
                            children: [
                              Expanded(
                                flex: 1,
                                child: Icon(
                                  Icons.travel_explore,
                                  size: 40,
                                  color: Colors.green.shade800,
                                ),
                              ),
                              Expanded(
                                flex: 3,
                                child: Text(
                                  'Explora esta zona para buscar corazones, hadas y secretos. En esta región hay:\n${emojis.join(" ")}',
                                  style: TextStyle(
                                    fontSize: 14,
                                    color: Colors.green.shade900,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(height: 16),

                      // Lista con cómo conseguir cada cosa
                      const Text(
                        'Cómo conseguir cada cosa',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 16,
                        ),
                      ),
                      ...region.pois.map(
                        (poi) => ListTile(
                          contentPadding: EdgeInsets.zero,
                          leading: Text(
                            poi.emoji,
                            style: const TextStyle(fontSize: 24),
                          ),
                          title: Text(poi.title),
                          subtitle: Text(poi.howTo),
                        ),
                      ),
                      const SizedBox(height: 16),

                      // Diálogo de Confirmación
                      ElevatedButton(
                        onPressed: () {
                          showDialog(
                            context: context,
                            builder: (context) => AlertDialog(
                              title: const Text('Confirmar Exploración'),
                              content: Text(
                                '¿Deseas marcar ${region.name} como explorado al 100%?',
                              ),
                              actions: [
                                TextButton(
                                  onPressed: () => Navigator.pop(context),
                                  child: const Text('Cancelar'),
                                ),
                                ElevatedButton(
                                  onPressed: () {
                                    completedRegionsNotifier.value = {
                                      ...completedRegionsNotifier.value,
                                      region.id,
                                    };
                                    Navigator.pop(context);
                                  },
                                  child: const Text('Aceptar'),
                                ),
                              ],
                            ),
                          );
                        },
                        child: const Text('Marcar como Completada'),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        // Indicador de Progreso Lineal
        const RubricDoc(
          title: 'Progreso y Textos',
          description: 'Uso de un LinearProgressIndicator determinado. Textos con diferentes pesos, colores y tamaños según jerarquía.',
        ),
        Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                'Progreso del Mundo',
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.w900,
                  color: Colors.green,
                ),
              ),
              const SizedBox(height: 8),
              ValueListenableBuilder<Set<String>>(
                valueListenable: completedRegionsNotifier,
                builder: (context, completed, child) {
                  final progress = completed.length / regions.length;
                  return Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      LinearProgressIndicator(value: progress, minHeight: 8),
                      const SizedBox(height: 4),
                      Text(
                        'Zonas completadas: ${completed.length}/${regions.length} (${(progress * 100).round()}%)',
                        style: TextStyle(
                          fontSize: 12,
                          fontStyle: FontStyle.italic,
                          color: Colors.grey.shade700,
                        ),
                      ),
                    ],
                  );
                },
              ),
            ],
          ),
        ),

        // Mapa Interactivo (Stack)
        const RubricDoc(
          title: 'Stack & InteractiveViewer',
          description: 'El contenedor Stack superpone áreas táctiles (Positioned) encima de la imagen. Escucha el global mapStyleNotifier para cambiar entre Pixel y Satélite.',
        ),
        Expanded(
          child: ValueListenableBuilder<String>(
            valueListenable: mapStyleNotifier,
            builder: (context, style, child) {
              final isPixel = style == 'Pixel';
              // Cada imagen tiene su propia relación de aspecto
              final aspectRatio = isPixel ? 1080 / 750 : 2400 / 1905;

              return InteractiveViewer(
                maxScale: isPixel ? 4.0 : 8.0,
                child: LayoutBuilder(
                  builder: (context, constraints) {
                    // Ajustamos la imagen al espacio disponible sin deformarla (ancho y alto)
                    double imageWidth = constraints.maxWidth;
                    double imageHeight = imageWidth / aspectRatio;
                    if (imageHeight > constraints.maxHeight) {
                      imageHeight = constraints.maxHeight;
                      imageWidth = imageHeight * aspectRatio;
                    }

                    return Center(
                      child: SizedBox(
                        width: imageWidth,
                        height: imageHeight,
                        child: ValueListenableBuilder<Set<String>>(
                          valueListenable: completedRegionsNotifier,
                          builder: (context, completed, child) {
                            return Stack(
                              children: [
                                Image.asset(
                                  isPixel
                                      ? '../shared_assets/map.jpg'
                                      : '../shared_assets/map_satellite.webp',
                                  width: imageWidth,
                                  height: imageHeight,
                                  fit: BoxFit.fill,
                                ),

                                // Las coordenadas de las zonas solo corresponden al mapa Pixel
                                if (isPixel)
                                  ...regions.map((reg) {
                                    final emojis = reg.pois.map((p) => p.emoji);
                                    final isCompleted = completed.contains(reg.id);

                                    return Positioned(
                                      left: reg.l * imageWidth,
                                      top: reg.t * imageHeight,
                                      width: reg.w * imageWidth,
                                      height: reg.h * imageHeight,
                                      child: Material(
                                        color: isCompleted
                                            ? Colors.green.withValues(
                                                alpha: 0.35,
                                              )
                                            : Colors.transparent,
                                        child: InkWell(
                                          onTap: () => _mostrarDetalle(reg),
                                          splashColor: Colors.amber.withValues(
                                            alpha: 0.4,
                                          ),
                                          child: Container(
                                            decoration: BoxDecoration(
                                              border: Border.all(
                                                color: Colors.white.withValues(
                                                  alpha: 0.2,
                                                ),
                                              ),
                                            ),
                                            // Mostrar los emojis esparcidos dentro de la caja de la zona
                                            child: Center(
                                              child: Wrap(
                                                alignment: WrapAlignment.center,
                                                spacing: 2,
                                                runSpacing: 2,
                                                children: [
                                                  if (isCompleted)
                                                    const Text(
                                                      '✅',
                                                      style: TextStyle(
                                                        fontSize: 10,
                                                      ),
                                                    ),
                                                  ...emojis.map(
                                                    (e) => Text(
                                                      e,
                                                      style: const TextStyle(
                                                        fontSize: 10,
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                        ),
                                      ),
                                    );
                                  }),
                              ],
                            );
                          },
                        ),
                      ),
                    );
                  },
                ),
              );
            },
          ),
        ),
      ],
    );
  }
}
