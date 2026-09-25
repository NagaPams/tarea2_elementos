// Datos del mapa de The Minish Cap.
// Fuente: docs/minish-cap-mapa.md (Zelda Wiki / StrategyWiki).
//
// Coordenadas:
// - Region.l/t/w/h: rectángulo normalizado (0–1) de la región dentro de ../shared_assets/map.jpg.
// - Poi.x/y: centro normalizado (0–1) del marcador dentro del mapa detallado de la región.
//   Son aproximadas (estimadas a ojo sobre cada imagen); ajústalas si algún marcador queda desfasado.

class Poi {
  final String emoji;
  final String title;
  final String howTo;
  final double x;
  final double y;

  const Poi(this.emoji, this.title, this.howTo, this.x, this.y);
}

class Region {
  final String id;
  final String name;
  final double l, t, w, h;
  final String? img;
  final double imgWidth, imgHeight;
  final List<Poi> pois;

  const Region({
    required this.id,
    required this.name,
    required this.l,
    required this.t,
    required this.w,
    required this.h,
    this.img,
    this.imgWidth = 1,
    this.imgHeight = 1,
    this.pois = const [],
  });
}

/// Leyenda de los emojis usados en los mapas.
const Map<String, String> poiLegend = {
  '💖': 'Pieza de Corazón',
  '🧚': 'Hada / Gran Hada',
  '🗡️': 'Mazmorra',
  '🌀': 'Portal de viento',
  '🍶': 'Botella vacía',
};

const List<Region> regions = [
  Region(
    id: 'mount_crenel',
    name: 'Monte Gongol',
    l: 0.122, t: 0.065, w: 0.181, h: 0.319,
    img: '../shared_assets/maps/Mount_Crenel.webp', imgWidth: 1000, imgHeight: 1660,
    pois: [
      Poi('🗡️', 'Cueva de las llamas', '2.ª mazmorra. Pasa por la Mina de Melari (tamaño minish) cerca de la cima. Objeto: Bastón de Pacci. Jefe: Gleerok.', 0.47, 0.17),
      Poi('🌀', 'Portal de viento', 'Junto al portal minish y la Mina de Melari. Tócalo para activarlo.', 0.60, 0.31),
      Poi('🧚', 'Gran Hada Efímera', 'Esquina SE del Muro de Crenel: pon una bomba en la pared. Tira una bomba al estanque y responde con la verdad → Bolsa de bombas más grande.', 0.24, 0.45),
      Poi('🧚', 'Fuente de hadas', 'Entre rocas en la base del Muro de Crenel; ábrela con una bomba. Tiene una Pieza de Corazón en medio.', 0.12, 0.58),
      Poi('💖', 'Pieza de Corazón #6', 'Al este del pie del Muro de Crenel: pon una bomba entre dos rocas.', 0.26, 0.58),
      Poi('💖', 'Pieza de Corazón #7', 'Dentro de la Cueva de las llamas: bomba en el muro sur después de la sala con 4 trampas y una vagoneta volcada.', 0.50, 0.21),
      Poi('💖', 'Pieza de Corazón #12', 'Dojo de Grayblade: escala las paredes del lado SE del monte.', 0.88, 0.57),
      Poi('💖', 'Pieza de Corazón #18', 'Con los Guantes topo, excava la cueva secreta al oeste de la cueva del Ermitaño (esquina NO del Muro de Crenel).', 0.05, 0.13),
      Poi('💖', 'Pieza de Corazón #40', 'Fusiona un Kinstone con Melari: crece una judía en la cima con la pieza y 160 rupias.', 0.40, 0.05),
    ],
  ),
  Region(
    id: 'mount_crenel_base',
    name: 'Base del Monte Gongol',
    l: 0.122, t: 0.395, w: 0.181, h: 0.103,
    img: '../shared_assets/maps/Mount_Crenel.webp', imgWidth: 1000, imgHeight: 1660,
    pois: [
      Poi('💖', 'Pieza de Corazón #5', 'Esquina NO de la base: ve al norte y pon una bomba entre dos árboles.', 0.05, 0.64),
      Poi('🧚', 'Fuente de hadas', 'Al NE de la base, entre dos árboles; ábrela con una bomba.', 0.88, 0.64),
    ],
  ),
  Region(
    id: 'castor_wilds',
    name: 'Pantano de Tabanta',
    l: 0.122, t: 0.507, w: 0.181, h: 0.263,
    img: '../shared_assets/maps/Region_Tabanta_TMC.webp', imgWidth: 1000, imgHeight: 938,
    pois: [
      Poi('🌀', 'Portal de viento', 'En la parte sur, al este de las Estatuas misteriosas.', 0.55, 0.91),
      Poi('💖', 'Pieza de Corazón #15', 'Dentro del dojo de Swiftblade I.', 0.05, 0.73),
      Poi('💖', 'Pieza de Corazón #26', 'Con las Aletas, nada hasta la cueva de la esquina NE.', 0.93, 0.08),
      Poi('💖', 'Pieza de Corazón #27', 'Fusiona con Zill o con un Picori de la Aldea minish para que aparezca un nenúfar. En tamaño minish llévalo al sur, ve a la esquina SE y nada al NE hasta una cuevita.', 0.95, 0.80),
    ],
  ),
  Region(
    id: 'wind_ruins',
    name: 'Ruinas del Viento',
    l: 0.122, t: 0.781, w: 0.181, h: 0.152,
    img: '../shared_assets/maps/TMC_Wind_Ruins.png', imgWidth: 1000, imgHeight: 524,
    pois: [
      Poi('🗡️', 'Arco de los vientos', '3.ª mazmorra, en el NE de las ruinas. Objeto: Guantes topo. Jefe: Mazaal. Recompensa: Ocarina del Viento.', 0.87, 0.05),
      Poi('💖', 'Pieza de Corazón #16', 'Usa el 2.º portal minish, ve al oeste y baja por la enredadera central de las tres hasta una cuevita.', 0.07, 0.25),
      Poi('💖', 'Pieza de Corazón #17', 'Dentro del Arco de los vientos: en tamaño minish (zona este), cae por el agujero junto al Armos y pasa a la sala del este.', 0.92, 0.10),
    ],
  ),
  Region(
    id: 'royal_valley',
    name: 'Valle Real',
    l: 0.313, t: 0.065, w: 0.086, h: 0.319,
    img: '../shared_assets/maps/Valle_Real_TMC.webp', imgWidth: 285, imgHeight: 599,
    pois: [
      Poi('🗡️', 'Mausoleo real', 'Mini-mazmorra en el cementerio. Pide la Llave del cementerio a Dampé y cruza el bosque laberinto (← ← ← ↑ ↑ ↑ ↑). Gustaf te da un Kinstone dorado.', 0.50, 0.06),
      Poi('🧚', 'Gran Hada Libélula', 'Cueva a la derecha de las escaleras de la entrada; ábrela con una bomba. Responde bien sus 5 preguntas → Carcaj más grande.', 0.76, 0.66),
      Poi('💖', 'Pieza de Corazón #30', 'Empuja la lápida del NO y usa las baldosas brillantes para mover un bloque grande.', 0.17, 0.12),
    ],
  ),
  Region(
    id: 'trilby_highlands',
    name: 'Colinas de Trilby',
    l: 0.313, t: 0.396, w: 0.086, h: 0.264,
    img: '../shared_assets/maps/Meseta_Beele.webp', imgWidth: 480, imgHeight: 960,
    pois: [
      Poi('🍶', 'Botella vacía #1', 'Baja por la escalera cercana a la Ciudadela: un Deku te la vende por 20 rupias. Es la única botella obligatoria.', 0.86, 0.72),
      Poi('🧚', 'Fuente de hadas', 'Al sur de la entrada desde la Ciudadela: pon una bomba entre dos piedras.', 0.90, 0.46),
      Poi('🧚', 'Fuente de hadas (Candy)', 'Tras el Arco de los vientos, fusiona un Kinstone azul con Candy (Posada): aparece un bajío; excava la cueva con los Guantes topo.', 0.10, 0.62),
    ],
  ),
  Region(
    id: 'western_wood',
    name: 'Bosque del Oeste',
    l: 0.313, t: 0.672, w: 0.086, h: 0.261,
    img: '../shared_assets/maps/Bosque_del_Oeste.webp', imgWidth: 480, imgHeight: 1008,
    pois: [
      Poi('💖', 'Pieza de Corazón #39', 'Fusiona con el Picori junto al portal de viento del Lago Hylia: se quitan las espinas de este árbol.', 0.12, 0.57),
    ],
  ),
  Region(
    id: 'hyrule_castle',
    name: 'Castillo de Hyrule',
    l: 0.408, t: 0.065, w: 0.182, h: 0.208,
    img: '../shared_assets/maps/Castillo_Hyrule_TMC.webp', imgWidth: 600, imgHeight: 418,
    pois: [
      Poi('🗡️', 'Castillo de Hyrule tenebroso', 'Mazmorra final, tras conseguir la Espada de los Cuatro. Jefe: Vaati.', 0.50, 0.04),
      Poi('🧚', 'Fuente de hadas', 'Una fusión verde aleatoria vacía la fuente de la izquierda y revela una escalera.', 0.43, 0.43),
      Poi('💖', 'Pieza de Corazón #13', 'Dentro del dojo de Grimblade (esquina inferior derecha del jardín).', 0.92, 0.53),
      Poi('💖', 'Pieza de Corazón #44', 'Una fusión aleatoria (Kinstone rojo) vacía la otra fuente y revela una escalera.', 0.56, 0.43),
    ],
  ),
  Region(
    id: 'north_hyrule_field',
    name: 'Pradera Norte',
    l: 0.408, t: 0.285, w: 0.182, h: 0.181,
    img: '../shared_assets/maps/Pradera_Norte_TMC.webp', imgWidth: 699, imgHeight: 600,
    pois: [
      Poi('🧚', 'Fuente de hadas', 'Una fusión azul aleatoria abre un árbol con la fuente dentro.', 0.73, 0.40),
      Poi('💖', 'Pieza de Corazón #9', 'Destruye con una bomba el bloque agrietado de la esquina NO.', 0.04, 0.05),
    ],
  ),
  Region(
    id: 'hyrule_town',
    name: 'Ciudadela de Hyrule',
    l: 0.408, t: 0.479, w: 0.182, h: 0.264,
    img: '../shared_assets/maps/Ciudadela_de_Hyrule_TMC.webp', imgWidth: 629, imgHeight: 600,
    pois: [
      Poi('🌀', 'Portal de viento', 'Junto al muro oeste de la Escuela Funday.', 0.62, 0.08),
      Poi('💖', 'Pieza de Corazón #10', 'Voltea con el Bastón de Pacci el portal minish de la casa de Romio, sube por la enredadera cerca de la Posada y rodéala hasta la parte trasera.', 0.96, 0.40),
      Poi('💖', 'Pieza de Corazón #19', 'Completa los Simuladores de Simon (abren tras el Arco de los vientos).', 0.34, 0.56),
      Poi('💖', 'Pieza de Corazón #20', 'Con las Aletas, nada dentro de la Fuente.', 0.35, 0.41),
      Poi('💖', 'Pieza de Corazón #29', 'Supera el nivel final del minijuego de cucos de Anju.', 0.07, 0.13),
      Poi('💖', 'Pieza de Corazón #32', 'Con la Capa de Roc, salta dentro de la campana.', 0.50, 0.28),
      Poi('💖', 'Pieza de Corazón #35', 'Reúne 130 figuras y habla con Herb: te deja entrar a la Casa de la música.', 0.34, 0.50),
      Poi('💖', 'Pieza de Corazón #36', 'Usa el portal minish de la Escuela Funday y sigue el camino minish del patio; empuja la roca grande con las baldosas brillantes.', 0.90, 0.14),
    ],
  ),
  Region(
    id: 'south_hyrule_field',
    name: 'Pradera Sur',
    l: 0.408, t: 0.755, w: 0.182, h: 0.179,
    img: '../shared_assets/maps/Pradera_Sur_TMC.webp', imgWidth: 800, imgHeight: 545,
    pois: [
      Poi('🌀', 'Portal de viento', 'Al norte de la casa de Link.', 0.70, 0.43),
      Poi('🧚', 'Fuente de hadas', 'Tras hierba alta junto al río, en el oeste; abre el muro agrietado con una bomba.', 0.28, 0.24),
      Poi('💖', 'Pieza de Corazón #28', 'Golpea con las Botas el tocón de la esquina SO: aparece un portal minish. Encógete y nada al norte hasta una cuevita.', 0.12, 0.72),
      Poi('💖', 'Pieza de Corazón #37', 'Fusiona con el Hurdy-Gurdy Man (Ciudadela): se abre este árbol de la esquina SE.', 0.92, 0.78),
    ],
  ),
  Region(
    id: 'veil_falls',
    name: 'Cascadas del Velo',
    l: 0.600, t: 0.065, w: 0.086, h: 0.319,
    img: '../shared_assets/maps/Catarata_Xera.webp', imgWidth: 240, imgHeight: 600,
    pois: [
      Poi('🌀', 'Portal de viento', 'Cerca de la parte alta de las cascadas, debajo de Biggoron.', 0.50, 0.36),
      Poi('💖', 'Pieza de Corazón #8', 'Usa el Bastón de Pacci en el agujero del borde norte del Rancho Lon Lon.', 0.85, 0.95),
      Poi('💖', 'Pieza de Corazón #21', 'Entra desde la esquina NE de la Pradera Norte y nada hacia el este.', 0.32, 0.66),
      Poi('💖', 'Pieza de Corazón #42', 'Fusiona con Gale (Sobre las nubes): se abre una cascada con la pieza dentro.', 0.10, 0.20),
      Poi('💖', 'Pieza de Corazón #43', 'Fusiona con el Picori de la casa más al este de la Aldea minish: aparece un bajío que lleva a una cueva secreta.', 0.48, 0.69),
    ],
  ),
  Region(
    id: 'cloud_tops',
    name: 'Sobre las Nubes',
    l: 0.695, t: 0.065, w: 0.181, h: 0.267,
    img: '../shared_assets/maps/Sobre_las_nubes_TMC.webp', imgWidth: 1000, imgHeight: 2993,
    pois: [
      Poi('🗡️', 'Palacio de los Vientos', '5.ª mazmorra. Fusiona Kinstones dorados con las 5 Nubes misteriosas para que aparezca el tornado y habla con Siroc. Objeto: Capa de Roc. Jefe: Gyorg.', 0.45, 0.05),
      Poi('🌀', 'Portal de viento', 'Frente a la Casa de la Tribu del Viento.', 0.48, 0.15),
      Poi('💖', 'Pieza de Corazón #31', 'Dentro del Palacio de los Vientos: esquina NO del 4.º piso; empuja un bloque, salta el hueco con la Capa de Roc y cruza la puerta del norte.', 0.55, 0.05),
    ],
  ),
  Region(
    id: 'lon_lon_ranch',
    name: 'Rancho Lon Lon',
    l: 0.600, t: 0.396, w: 0.128, h: 0.264,
    img: '../shared_assets/maps/Rancho_Lon_Lon_TMC.webp', imgWidth: 720, imgHeight: 960,
    pois: [
      Poi('🍶', 'Botella vacía #4', 'Final de la Cueva Goron: fusiona con Eenie y con los 5 Muros misteriosos para reunir a los 6 Gorons excavadores.', 0.19, 0.88),
      Poi('💖', 'Pieza de Corazón #14', 'Golpea un árbol con las Botas: aparece un portal minish que da a un camino minish cercano.', 0.40, 0.38),
      Poi('💖', 'Pieza de Corazón #34', 'Salta con la Capa de Roc a la orilla norte del Lago Hylia, excava la cueva secreta y sal por la izquierda: sales aquí.', 0.78, 0.09),
    ],
  ),
  Region(
    id: 'lake_hylia',
    name: 'Lago Hylia',
    l: 0.695, t: 0.341, w: 0.181, h: 0.319,
    img: '../shared_assets/maps/Lago_Hylia_TMC.webp', imgWidth: 480, imgHeight: 600,
    pois: [
      Poi('🗡️', 'Templo de las aguas', '4.ª mazmorra, dentro de un bloque de hielo en el lago (necesitas las Aletas). Objeto: Farol. Jefe: Octorok gigante.', 0.37, 0.41),
      Poi('🌀', 'Portal de viento', 'Al oeste del Templo de las aguas. Ya está activo al conseguir la Ocarina.', 0.21, 0.46),
      Poi('🍶', 'Botella vacía #3', 'En la tienda de Stockwell (Ciudadela), llega en tamaño minish detrás del mostrador por la comida de perro. Dásela a Fifi aquí, en la Casa de Stockwell.', 0.37, 0.04),
      Poi('💖', 'Pieza de Corazón #22', 'Bucea en el estanque pequeño junto a la Casa de Stockwell.', 0.30, 0.05),
      Poi('💖', 'Pieza de Corazón #23', 'Nada hasta el borde sur del lago.', 0.60, 0.95),
      Poi('💖', 'Pieza de Corazón #24', 'Dentro del dojo de Waveblade (en un árbol del lago).', 0.63, 0.45),
      Poi('💖', 'Pieza de Corazón #33', 'Con la Capa de Roc, salta entre los dos islotes del norte del lago.', 0.55, 0.25),
      Poi('💖', 'Pieza de Corazón #41', 'Fusiona con el Picori del borde este, salta a la orilla norte, excava la cueva secreta y sal por la derecha hasta la judía trepadora.', 0.63, 0.09),
    ],
  ),
  Region(
    id: 'eastern_hills',
    name: 'Colinas del Este',
    l: 0.600, t: 0.672, w: 0.086, h: 0.261,
    img: '../shared_assets/maps/Colina_del_Este.webp', imgWidth: 480, imgHeight: 1008,
    pois: [
      Poi('🍶', 'Botella vacía #2', 'Fusiona con Smith (casa de Link): aparece un cofre al sur de la granja de Eenie y Meenie.', 0.25, 0.37),
      Poi('💖', 'Pieza de Corazón #38', 'Fusiona con el Picori de la esquina inferior izquierda: crece una judía trepadora en el norte.', 0.12, 0.18),
    ],
  ),
  Region(
    id: 'minish_woods',
    name: 'Bosque Minish',
    l: 0.695, t: 0.672, w: 0.181, h: 0.261,
    img: '../shared_assets/maps/Bosques_Minish.webp', imgWidth: 600, imgHeight: 600,
    pois: [
      Poi('🗡️', 'Sepulcro del bosque', '1.ª mazmorra, al norte de la Aldea minish (se entra por la Abadía de Festari). Objeto: Tarro mágico. Jefe: Chuchu verde gigante.', 0.45, 0.60),
      Poi('🌀', 'Portal de viento', 'Al noroeste de la Aldea minish.', 0.29, 0.67),
      Poi('🧚', 'Gran Hada Mariposa', 'Desde la esquina NE de las Colinas del Este, usa el Bastón de Pacci en el agujero y sube; está dentro de un árbol solitario. Tírale todas tus rupias → Cartera más grande.', 0.52, 0.42),
      Poi('💖', 'Pieza de Corazón #1', 'Justo al sur del Sepulcro del bosque.', 0.45, 0.75),
      Poi('💖', 'Pieza de Corazón #2', 'En el muelle noreste de la Aldea minish (tamaño minish).', 0.52, 0.83),
      Poi('💖', 'Pieza de Corazón #3', 'Dentro del Sepulcro del bosque: usa el Tarro mágico en el lado este del muro sur de la sala del Madderpillar.', 0.40, 0.57),
      Poi('💖', 'Pieza de Corazón #4', 'Dentro del Sepulcro del bosque: al revelar el portal azul, vuelve a la entrada y úsalo.', 0.50, 0.57),
      Poi('💖', 'Pieza de Corazón #11', 'Tras ver a Rem dormido, cruza el Rancho Lon Lon hacia el NO del bosque; está al SO, junto a un estanque.', 0.12, 0.34),
      Poi('💖', 'Pieza de Corazón #25', 'Usa el portal minish al oeste de la aldea, ve al NO hasta tres cuevitas y entra en la de la izquierda.', 0.08, 0.53),
    ],
  ),
];
