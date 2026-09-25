import 'package:flutter_test/flutter_test.dart';

import 'package:catalogo_ui/main.dart';

void main() {
  testWidgets('La app arranca en la pantalla de bienvenida', (
    WidgetTester tester,
  ) async {
    await tester.pumpWidget(const MinishApp());

    expect(find.text('Minish Companion'), findsOneWidget);
    expect(find.text('Iniciar Sesión'), findsOneWidget);

    await tester.tap(find.text('Continuar como invitado'));
    await tester.pumpAndSettle();

    expect(find.text('Acciones del Mapa'), findsOneWidget);
  });

  testWidgets('El inventario muestra técnicas, las 44 piezas y los Kinstones', (
    WidgetTester tester,
  ) async {
    await tester.pumpWidget(const MinishApp());
    await tester.tap(find.text('Continuar como invitado'));
    await tester.pumpAndSettle();
    await tester.tap(find.text('Inventario'));
    await tester.pumpAndSettle();

    expect(find.text('Ataque giratorio'), findsOneWidget);

    await tester.tap(find.text('Corazones'));
    await tester.pumpAndSettle();
    expect(find.text('Piezas obtenidas: 0/44'), findsOneWidget);

    await tester.tap(find.text('Kinstones'));
    await tester.pumpAndSettle();
    expect(find.text('Verdes (49 fusiones)'), findsOneWidget);
  });
}
