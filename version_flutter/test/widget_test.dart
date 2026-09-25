import 'package:flutter_test/flutter_test.dart';

import 'package:catalogo_ui/main.dart';

void main() {
  testWidgets('La app arranca en la pantalla de bienvenida', (WidgetTester tester) async {
    await tester.pumpWidget(const MinishApp());

    expect(find.text('Minish Companion'), findsOneWidget);
    expect(find.text('Iniciar Sesión'), findsOneWidget);

    await tester.tap(find.text('Continuar como invitado'));
    await tester.pumpAndSettle();

    expect(find.text('Acciones del Mapa'), findsOneWidget);
  });
}
