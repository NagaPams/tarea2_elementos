# The Legend of Zelda: The Minish Cap: datos del mapa

Referencia para el mapa interactivo de **Minish Companion**. Contiene las regiones de Hyrule, sus conexiones, mazmorras, puntos de interés y todos los coleccionables con ubicación: 44 Piezas de Corazón, 100 fusiones de Kinstones, portales de viento, fuentes de hadas, botellas, pergaminos, monstruos dorados, etc.

> **Fuentes (scraping del 25 sep 2026):** [Zelda Wiki](https://zeldawiki.wiki) (páginas de ubicaciones, mazmorras, *Kinstone Fusion*, *Piece of Heart*, *Wind Crest*, *Minish Portal*, *Tiger Scroll*, *Great Fairy Fountain*, *Fairy Fountain*, *Bottle*, *Heart Container*, *The Minish Cap Translations*) y [StrategyWiki](https://strategywiki.org/wiki/The_Legend_of_Zelda:_The_Minish_Cap). El contenido de ambas wikis está bajo licencia CC BY-SA, así que si publicas la app conviene citarlas.
>
> **Nombres en español:** los que llevan ✔ son los oficiales de la versión española del juego (según Zelda Wiki). Los demás son traducciones libres, y el nombre original en inglés va entre paréntesis para que puedas buscarlo.

---

## 1. Estructura del mapa

Hyrule en *The Minish Cap* se divide en una cuadrícula de **17 regiones exteriores**, más *Sobre las nubes* (Cloud Tops) en el cielo. `flutter/assets/map.jpg` (1080×750 px) sigue exactamente esa cuadrícula:

```
┌──────────────┬────────┬──────────────┬────────┬──────────────┐
│ Monte Crenel │ Valle  │  Castillo /  │Cascadas│ Sobre las    │
│              │ Real   │  Jardín      │del Velo│ nubes        │
│              │        ├──────────────┤        ├──────────────┤
├──────────────┤        │ Pradera      ├────────┴───┐ Lago     │
│ Base Crenel  ├────────┤ Norte        │ Rancho     │ Hylia    │
├──────────────┤ Colinas├──────────────┤ Lon Lon    │          │
│ Pantano de   │ de     │ Ciudadela    │            │          │
│ Castor       │ Trilby │ de Hyrule    │            │          │
│              ├────────┤              ├────────┬───┴──────────┤
├──────────────┤ Bosque ├──────────────┤Colinas │ Bosque       │
│ Ruinas del   │ Occid. │ Pradera Sur  │del Este│ minish       │
│ Viento       │        │ (casa Link)  │        │              │
└──────────────┴────────┴──────────────┴────────┴──────────────┘
```

### 1.1 Coordenadas de cada región en `map.jpg`

Las medí detectando los bordes oscuros de la imagen. Uso dos formatos:
- **px**: `x1, y1, x2, y2`, en píxeles de la imagen de 1080×750.
- **Normalizado**: `left, top, width, height`, en fracciones de 0 a 1. Multiplícalos por el tamaño con que se dibuje la imagen para colocar zonas táctiles (`Positioned`) aunque la imagen se escale.

| id | Región | px (x1, y1, x2, y2) | normalizado (l, t, w, h) |
|---|---|---|---|
| `mount_crenel` | Monte Gongol ✔ (Mount Crenel) | 132, 49, 328, 288 | 0.122, 0.065, 0.181, 0.319 |
| `mount_crenel_base` | Base del Monte Gongol (Mount Crenel's Base) | 132, 296, 328, 373 | 0.122, 0.395, 0.181, 0.103 |
| `castor_wilds` | Región Tabanta ✔ (Castor Wilds) | 132, 380, 328, 577 | 0.122, 0.507, 0.181, 0.263 |
| `wind_ruins` | Ruinas del Viento (Wind Ruins) | 132, 586, 328, 700 | 0.122, 0.781, 0.181, 0.152 |
| `royal_valley` | Valle Real (Royal Valley) | 338, 49, 431, 288 | 0.313, 0.065, 0.086, 0.319 |
| `trilby_highlands` | Colinas de Trilby (Trilby Highlands) | 338, 297, 431, 495 | 0.313, 0.396, 0.086, 0.264 |
| `western_wood` | Bosque del Oeste (Western Wood) | 338, 504, 431, 700 | 0.313, 0.672, 0.086, 0.261 |
| `hyrule_castle` | Jardín del Castillo de Hyrule (Hyrule Castle Garden) | 441, 49, 638, 205 | 0.408, 0.065, 0.182, 0.208 |
| `north_hyrule_field` | Pradera Norte de Hyrule (North Hyrule Field) | 441, 214, 638, 350 | 0.408, 0.285, 0.182, 0.181 |
| `hyrule_town` | Ciudadela de Hyrule ✔ (Hyrule Town) | 441, 359, 638, 557 | 0.408, 0.479, 0.182, 0.264 |
| `south_hyrule_field` | Pradera Sur de Hyrule (South Hyrule Field) | 441, 566, 638, 700 | 0.408, 0.755, 0.182, 0.179 |
| `veil_falls` | Cascadas del Velo (Veil Falls) | 648, 49, 741, 288 | 0.600, 0.065, 0.086, 0.319 |
| `cloud_tops` | Sobre las nubes ✔ (Cloud Tops) | 751, 49, 947, 249 | 0.695, 0.065, 0.181, 0.267 |
| `lon_lon_ranch` | Rancho Lon Lon (Lon Lon Ranch) | 648, 297, 786, 495 | 0.600, 0.396, 0.128, 0.264 |
| `lake_hylia` | Lago Hylia ✔ (Lake Hylia) | 751, 256, 947, 495 * | 0.695, 0.341, 0.181, 0.319 * |
| `eastern_hills` | Colinas del Este (Eastern Hills) | 648, 504, 741, 700 | 0.600, 0.672, 0.086, 0.261 |
| `minish_woods` | Bosque minish ✔ (Minish Woods) | 751, 504, 947, 700 | 0.695, 0.672, 0.181, 0.261 |

\* El Lago Hylia tiene forma de **L**: entre y=256 y y=296 empieza en x=751, y por debajo de y=296 empieza en x=791, porque ahí el Rancho Lon Lon se mete a la derecha. Si usas rectángulos, pon el rancho encima del lago o parte el lago en dos: `751,256,947,296` + `791,296,947,495`.

*Veil Springs* (manantial en la cima de las Cascadas del Velo) y las zonas subterráneas o interiores no tienen casilla propia en la imagen. Las conté dentro de su región padre.

> Nota sobre tu app: en `main.dart` el `DropdownButton` usa "Monte Crenel" y "Pantano de Castor". Los nombres oficiales en español son **Monte Gongol** y **Región Tabanta**. Tú decides cuáles usar.

### 1.2 Conexiones entre regiones (grafo de navegación)

| Región | Conecta con |
|---|---|
| Monte Crenel | Base del Monte Crenel (S) |
| Base del Monte Crenel | Monte Crenel (N), Colinas de Trilby (SE) |
| Pantano de Castor | Ruinas del Viento (S), Bosque del Oeste (E) |
| Ruinas del Viento | Pantano de Castor (N) |
| Valle Real | Pradera Norte (SE), Colinas de Trilby (S) |
| Colinas de Trilby | Ciudadela (E), Base del Monte Crenel (O), Pradera Norte (NE), Valle Real (N), Bosque del Oeste (S) |
| Bosque del Oeste | Pantano de Castor (O), Pradera Sur (E), Colinas de Trilby (N) |
| Jardín del Castillo | Pradera Norte (S) |
| Pradera Norte | Jardín del Castillo (N), Ciudadela (S), Rancho Lon Lon (SE), Valle Real (NO), Colinas de Trilby (SO), Cascadas del Velo (NE) |
| Ciudadela de Hyrule | Rancho Lon Lon (E), Pradera Norte (N), Pradera Sur (S), Colinas de Trilby (O) |
| Pradera Sur | Colinas del Este (E), Ciudadela (N), Bosque del Oeste (O) |
| Cascadas del Velo | Rancho Lon Lon (S), Pradera Norte (SO), Veil Springs (N) |
| Veil Springs | Cascadas del Velo (S), Sobre las nubes (arriba, por un tornado) |
| Sobre las nubes | Veil Springs (abajo) |
| Rancho Lon Lon | Colinas del Este (S), Ciudadela (O), Lago Hylia (E), Pradera Norte (NO), Cascadas del Velo (N) |
| Lago Hylia | Rancho Lon Lon (O), Bosque minish (S) |
| Colinas del Este | Rancho Lon Lon (N), Bosque minish (E), Pradera Sur (O) |
| Bosque minish | Colinas del Este (O), Lago Hylia (N) |

Atajo extra: al fusionar un Kinstone con **Strato**, aparece un portal al oeste de la casa de Link (Pradera Sur) que lleva a la **Casa de la Tribu del Viento**.

---

## 2. Mazmorras

| # | Mazmorra | Región | Objeto | Minijefe(s) | Jefe | Recompensa |
|---|---|---|---|---|---|---|
| 1 | Sepulcro del bosque ✔ (Deepwood Shrine) | Bosque minish, al norte de la Aldea minish (se entra por la Abadía de Festari) | Tarro mágico (Gust Jar) | Mulldozers rojos, Madderpillar | Chuchu verde gigante | Elemento Tierra + Contenedor de corazón |
| 2 | Cueva de las llamas ✔ (Cave of Flames) | Monte Crenel, cerca de la cima (se entra por la Mina de Melari) | Bastón de Pacci | 8 Chuchus espinosos | Gleerok | Elemento Fuego + Contenedor |
| 3 | Arco de los vientos ✔ (Fortress of Winds) | Ruinas del Viento, zona noreste | Guantes topo (Mole Mitts) | Darknut plateado/rojo, 2 Wizzrobes | Mazaal | Ocarina del Viento + Contenedor |
| 4 | Templo de las aguas ✔ (Temple of Droplets) | Lago Hylia, templo minish dentro de un bloque de hielo en el centro del lago (hacen falta las Aletas) | Farol (Flame Lantern) | Chuchu azul gigante, 2 Madderpillars | Octorok gigante | Elemento Agua + Contenedor |
| — | Mausoleo real ✔ (Royal Crypt, mini-mazmorra) | Valle Real, cementerio (Llave del cementerio de Dampé) | — | — | — | Kinstone dorado del rey Gustaf (abre las Cascadas del Velo) |
| 5 | Palacio de los Vientos ✔ (Palace of Winds) | Sobre las nubes (tornado de las 5 Nubes misteriosas) | Capa de Roc | Darknut rojo | Gyorg (pareja) | Elemento Viento + Contenedor |
| 6 | Castillo de Hyrule tenebroso ✔ (Dark Hyrule Castle) | Castillo de Hyrule | — | Darknut rojo, Caballero negro | Vaati (Renacido, Transfigurado e Ira de Vaati) | Final del juego |

**Santuario Elemental** (Elemental Sanctuary): se entra por la *Puerta Minish* del Patio del Palacio, en el Castillo de Hyrule. Allí se infunden los elementos en la espada: Espada de Smith → Espada Picori rota → Espada blanca → Espada blanca (2 y 3 elementos) → Espada de los Cuatro.

### Cofres de las mazmorras (resumen)

- **Sepulcro del bosque:** Mapa (Big Chest, sala oeste-centro de B1), Tarro mágico (NO de B1, tras el Madderpillar), Brújula (NE de B2), Gran llave (NO de B2), 3 llaves pequeñas, 50 conchas en 4 cofres, 20 rupias.
- **Cueva de las llamas:** Brújula (NE de 1F), Mapa (SE de B1), Bastón de Pacci (NE de B1), Gran llave (SE de B2), 5 Kinstones, 50 y 100 rupias.
- **Arco de los vientos:** 5 Kinstones, Brújula (sala con 2 Eyegores), Mapa (plataforma central del 2F), Guantes topo y 100 rupias (tras un muro con bomba después del Darknut), 80 conchas (tras los Wizzrobes), Gran llave (extremo SE del 2F, bajando por el agujero derecho del 3F).

---

## 3. Regiones en detalle

### 3.1 Monte Crenel: Monte Gongol ✔ (`mount_crenel`)
Zona montañosa y escarpada del **noroeste**. Viven aquí los **minish de la montaña** (el herrero Melari y sus 7 aprendices), la **Gran Hada Efímera** (Great Mayfly Fairy), el **Ermitaño de Crenel** y el maestro **Grayblade**.
- Se sube con **judías trepadoras** (las verdes necesitan *Agua mineral del Monte Crenel*), con el **Anillo de agarre** (Grip Ring, lo vende un Deku en una cueva que se abre con bomba) y con **corrientes de aire** usando a Ezlo.
- **Muro de Crenel** (Crenel Wall): pared enorme en el NO, solo escalable con el Anillo de agarre. Caen rocas. En su esquina SE está la Gran Hada (tras un muro con bomba). En la esquina NO está la **Cueva del Ermitaño** y, al lado, una cueva de Guantes topo con un **Muro misterioso**.
- **Mina de Melari** (cima, tamaño minish): forja la Espada blanca. Es el paso a la **Cueva de las llamas**.
- Fueron tierra de los Goron en el pasado.
- **Enemigos:** Escarabajo negro, Tektites azul/rojo, Rocas, Deku, Tektite dorado, Chuchus (verde, rojo, espinoso), Helmasaur, Keese, Moldorm, Peahat, Mulldozer rojo, Chispa, Escarabajo espinoso.

### 3.2 Base del Monte Crenel (`mount_crenel_base`)
Oeste de Hyrule. Es el **único sitio con Agua mineral del Monte Crenel**, que se guarda en una botella. Detrás de un muro destructible hay un Deku que dice dónde conseguir una Botella vacía. Hay una Fuente de hadas en el NE, entre dos árboles.
- **Enemigos:** Rocas, Deku, Pinchos, Helmasaur, Keese, Chuchu rojo, Mulldozer rojo, Pesto rojo, Tektite rojo, Pesto pequeño.

### 3.3 Pantano de Castor: Región Tabanta ✔ (`castor_wilds`)
Pantano grande del **oeste**. El agua funciona como arenas movedizas: para cruzarla sin hundirte hacen falta las **Botas de Pegaso**.
- **3 Kinstones dorados** (en una cueva central custodiada por un Darknut, en una cueva al norte y en otra al SE) que se fusionan con las **3 Estatuas misteriosas** del extremo SO para romper la roca que bloquea las **Ruinas del Viento**.
- **Arco**: en el NO. Encógete, cruza un charco sobre un nenúfar y entra a un agujero con 5 Mulldozers. Sirve para destruir las **Estatuas Eyegore**.
- **Portal de viento** en la parte sur.
- Un Deku en el norte vende 30 flechas por 30 rupias, y su fusión abre un árbol en el Bosque minish.
- **Dojo de Swiftblade I** y **dojo de Scarblade** (detrás de una cascada).
- Cofre con Kinstone protegido por un Eyegore (zona centro-oeste). Muro de Guantes topo al NE: 50 conchas + Kinstone.
- **Enemigos:** Mulldozer azul, Deku, Estatua Eyegore, Rope dorado, Like Like, Peahat, Rupee Like rojo, Rope, Escarabajo tijera, Darknut plateado.

### 3.4 Ruinas del Viento (`wind_ruins`)
Suroeste. Son las antiguas moradas de la **Tribu del Viento**, y en el NE está el **Arco de los vientos**.
- Muro con bomba junto a la entrada: cofre con Kinstone.
- **Armos** (estatuas hechas por los minish con un interruptor dentro). Uno bloquea el camino y hay que desactivarlo desde dentro en tamaño minish.
- **3 portales minish** con forma de tronco. Enredaderas finas que se escalan en tamaño minish, y una de ellas lleva a una **Pieza de Corazón**.
- Judía plantada (crece con una fusión): Carcaj grande.
- Cofres: 50 rupias y 50 conchas.
- **Enemigos:** Armos, Octorok dorado, Leever, Tektite rojo, Rope, Escarabajo espinoso.

### 3.5 Valle Real (`royal_valley`)
Noroeste. Es oscuro y con niebla, y hace falta el **Farol**. Está lleno de Takkuri y Ghini.
- **Gran Hada Libélula** (Great Dragonfly Fairy): en una cueva con bomba a la derecha de las escaleras de la entrada. Hace 5 preguntas y da un **Carcaj más grande**.
- **Bosque laberinto** (como el Bosque Perdido). Si bajas en cualquier punto vuelves al inicio.
  - Ruta de los carteles: **↑ ← ← ↑ → ↑**
  - Ruta de Dampé (cofre con 200 conchas, o 100 rupias si ya tienes todas las figuras): **← ← ← ↑ ↑ ↑ ↑**
- **Choza de Dampé** ✔ (NE): da la **Llave del cementerio**.
- **Cementerio → Mausoleo real** (tumba del rey Gustaf).
- **Gina** (Ghini amistosa, fusiona Kinstones) bajo la lápida de la esquina superior derecha. **Spekter** también está aquí.
- **Enemigos:** Ghini, Takkuri.

### 3.6 Colinas de Trilby (`trilby_highlands`)
Oeste, colinas.
- Escalera cerca de la Ciudadela a una cueva donde un Deku vende una **Botella vacía (20 rupias)**. Es la única botella obligatoria.
- **Fuente de hadas** al sur de la entrada desde la Ciudadela (bomba entre dos piedras).
- Cerca de la entrada al Monte Crenel, un puente y una pared de tierra (Guantes topo): 2 cofres con Kinstones, un **Muro misterioso**, **Knuckle** (te dice cuántas fusiones te faltan) y un portal minish a casa de un Picori.
- Estanque del centro-sur: se seca con una fusión y deja una cueva con 75 rupias.
- **Enemigos:** Escarabajo negro, Octorok azul/rojo, Deku, Keese, Moldorm, Moblin de lanza, Keaton amarillo.

### 3.7 Bosque del Oeste (`western_wood`)
Oeste. Aquí está la **Casa de Percy** (el poeta; la bloquea un árbol caído que se levanta con una fusión, y dentro vive una Moblin). Hay un Picori cuya fusión hace crecer una judía. Varios **árboles caídos** se levantan con distintas fusiones (rupias, conchas), y un **árbol con espinas** guarda una Pieza de Corazón.
- **Enemigos:** Octorok azul, Moblin arquero, Cuervo, Octorok dorado, Moblin de lanza, Keaton amarillo.

### 3.8 Castillo de Hyrule y su jardín (`hyrule_castle`)
Norte. Dentro de las murallas hay guardias (vasallos) patrullando.
- **Sala del trono** ✔ (Rey Daltus, Ministro Potho, Zelda).
- **Patio del Palacio** (sótano 1): aquí está la **Puerta Minish**, que lleva al **Santuario Elemental**.
- **Dojo de Grimblade** (esquina inferior derecha del jardín): enseña el Rayo de espada.
- Dos **fuentes** cerca de la entrada que se secan con fusiones: una **Fuente de hadas** y una **Pieza de Corazón**.
- Aparece un **Rope dorado** tras una fusión.
- **Enemigos:** Escarabajo negro, Rope dorado.

### 3.9 Pradera Norte de Hyrule (`north_hyrule_field`)
Es el nudo que conecta 6 regiones.
- **4 árboles** que se abren al fusionar con **Tingle y sus hermanos** (Tingle, Knuckle, Ankle, David Jr.). Cada uno tiene un interruptor, y con los 4 pulsados baja una escalera al **Bumerán mágico**.
- Cascada que se abre al fusionar con Waveblade: **dojo de Greatblade**.
- Árbol con **Fuente de hadas** (fusión aleatoria). Bloque agrietado en el NO: Pieza de Corazón.
- **Enemigos:** Deku, Cuervo, Keese, Octorok rojo, Moblin de lanza, Keaton amarillo.

### 3.10 Ciudadela de Hyrule ✔ (`hyrule_town`)
El centro de Hyrule y el único asentamiento hyliano. Alcalde: **Hagen**.

| Lugar | Dónde | Qué hay |
|---|---|---|
| Plaza (Town Square) | Centro | Puestos (Brocco, Pina). Si limpias el polvo con el Tarro llegan **Beedle** (Picolitas) y el **Mercader Goron** (Kinstones) |
| Fuente | NO de la plaza | Mazmorra minish: **Brazaletes de fuerza**. Se entra desde la Casa del Dr. Left |
| Biblioteca Real de Hyrule | Esquina NO | Abre después del Arco de los vientos. **Librari** te da las **Aletas** si devuelves 3 libros |
| Casa del Dr. Left | Borde oeste, junto a la escalera | Libro "Leyenda de los Picori" |
| Carpintería | Borde oeste (con noria) | Portal minish |
| Galería de figuras (Carlov) | Esquina SO, entrada tras unos arbustos | Figuras a cambio de conchas (136 en total) |
| Escuela Swiftblade | SO, cruzando el río desde la Galería | Técnicas de espada |
| Casa del Forastero (Strato) | Oeste, salida a Trilby | Su fusión crea el portal a la Tribu del Viento |
| Portal de viento | Muro oeste de la Escuela Funday | — |
| Escuela Funday | Este | Estatua de Potho que tapa una escalera al subsuelo. En el patio (minish), roca de la Espada de los Cuatro con 3 Kinstones rojos + Pieza de Corazón |
| Casa del Alcalde Hagen | Este, al sur de la Escuela | Máscaras en la pared (Botas = rupias), portal minish y escalera al subsuelo |
| Posada Happy Hearth | Este | Dormir te da un Kinstone (50/200/… rupias según la habitación). Aquí están los Oráculos Din, Nayru y Farore |
| Café de Mamá (+ vigas) | — | Hurdy-Gurdy Man. Puente minish a la tienda de Stockwell |
| Tienda de Stockwell (+ vigas) | Sur | Escudo, flechas, etc. Detrás del mostrador (minish), la **Botella con comida de perro** |
| Panadería (+ vigas) | Plaza | Pasteles con Kinstones. En las vigas, 4 minish y 100 rupias |
| Zapatería de Rem | Plaza | **Botas de Pegaso** (despiértalo con el Champiñón despertador) |
| Simuladores de Simon | Plaza | Minijuego de combate: Pieza de Corazón |
| Cofres de Borlov | Plaza | Minijuego de apostar a un cofre |
| Casa de la música (Herb) | Oeste de la plaza | Abre con 130 figuras: Pieza de Corazón + 3×200 rupias |
| Gallinero de Anju | — | Minijuego de cucos: Pieza de Corazón |
| Correos ✔ | — | Boletín del Espadachín (tras fusionar con el Cartero) |
| Casas de Romio y Julietta | SE | Unidas por una tabla en tamaño minish |
| Pozo de la Ciudadela | SE, sobre las casas de Romio y Julietta | Baja al **Subsuelo de Hyrule** (hacen falta las Aletas) |
| Casas vacías | — | Para los Oráculos, que dan los **Amuletos** de Din, Nayru y Farore |
| Campanario | — | Con la Capa de Roc: Pieza de Corazón |

**Subsuelo de Hyrule** (Hyrule Underground): túneles bajo la Ciudadela con 4 entradas (el pozo, una cueva de Guantes topo, la escalera de la Escuela Funday y la escalera del jardín del Alcalde). Tiene muchos cofres con rupias.

- **Enemigos:** Escarabajo negro, Mulldozer azul/rojo, Keese, Pesto rojo, Escarabajo tijera, Sluggula, Trampa.

### 3.11 Pradera Sur de Hyrule (`south_hyrule_field`)
Zona de inicio del juego.
- **Tu casa / Casa de Link** ✔: vive con su abuelo **Smith** (herrero). Puedes dormir para recuperar corazones y fusionar con Smith.
- **Fuente de hadas** tras un muro agrietado en el oeste (junto al río, detrás de hierba alta).
- **Tingle** en un saliente al este.
- **Portal de viento** al norte de la casa de Link.
- Portal a la Tribu del Viento, al oeste de la casa (tras fusionar con Strato).
- Estanque del oeste: se seca con una fusión y lleva a una cueva con 75 rupias.
- **Enemigos:** Escarabajo negro, Zarzas, Octorok rojo.

### 3.12 Cascadas del Velo y Veil Springs (`veil_falls`)
Noreste: acantilados alrededor de una gran cascada. Hay que nadar (Aletas). Para avanzar se fusiona el Kinstone dorado de **Gustaf** con la **losa de piedra** ("Source of the Flow") que hay al final del puente.
- **Dojo de Splitblade** (cueva del sur, se abre con una fusión).
- **Veil Springs** (arriba): tornado hacia **Sobre las nubes**. **Biggoron** despierta cuando se une el 6.º Goron y convierte el Escudo pequeño en el **Escudo espejo**.
- **Portal de viento**: cerca de la parte alta, debajo de Biggoron.
- **Enemigos:** Chuchu azul, Leever azul, Rupee Like azul/rojo, Tektite dorado, Helmasaur, Keese, Peahat, Octorok rojo, Fuego fatuo rojo, Chuchu roca, Chispa, Escarabajo espinoso.

### 3.13 Sobre las nubes ✔ (`cloud_tops`)
Está encima de Veil Springs y solo pueden caminar por ella los "puros de corazón". Hay que reunir **Kinstones dorados** y fusionarlos con **5 Nubes misteriosas** para activar 5 molinetes, y así aparece el tornado al **Palacio de los Vientos**. Las nubes duras se rompen con los Guantes topo.
- **Casa de la Tribu del Viento** (Siroc, el líder; Gale, Hailey, Gregal, Flurris, Strata, Caprice).
- **Portal de viento** frente a la Casa de la Tribu del Viento.
- **Enemigos:** Piraña de nube, Lakitu.

### 3.14 Rancho Lon Lon (`lon_lon_ranch`)
Este. Aquí viven **Malon y Talon** con sus vacas. La **llave de repuesto** está en una vasija dentro de la casa (tamaño minish), y con ella tienes paso al Lago Hylia.
- Árbol que se golpea con las Botas: portal minish + camino minish (Pieza de Corazón).
- Cueva con 50 rupias y **Ankle** (fusiona).
- **Elsie** (una vaca) fusiona si estás en tamaño minish.
- **Cueva Goron** (esquina SO): la excavan los **Gorons excavadores** (fusión con Eenie + 5 Muros misteriosos). Al final hay una **Botella vacía** y 200 rupias.
- Estanque que se seca con la fusión del Alcalde Hagen: **Cartera grande**.
- **Enemigos:** Acro-Bandit, Pesto azul, Moldworm, Octorok rojo.

### 3.15 Lago Hylia ✔ (`lake_hylia`)
Este. El río baja desde las Cascadas del Velo.
- **Templo de las aguas** ✔ (en el centro, en un bloque de hielo).
- **Casa de Stockwell** (norte, allí vive su perro **Fifi**) y **Cabaña del Alcalde Hagen** (SE, libro "Historia de las Máscaras").
- **Dojo de Waveblade** (dentro de un árbol).
- **Portal de viento** al oeste del Templo de las aguas. Es el que ya está activado al conseguir la Ocarina.
- Casa minish de **Librari** en el centro del lago (al final del juego). Se llega por una grieta junto al portal de viento, y te da un **Contenedor de corazón**.
- Judía trepadora en la orilla norte (Capa de Roc + cueva secreta).
- **Enemigos:** Octorok azul/rojo, Pinchos, Chuchu verde, Like Like, Moldorm, Peahat, Pesto rojo, Sluggula, Moblin de lanza, Trampa de pinchos.

### 3.16 Colinas del Este (`eastern_hills`)
Este. Aquí está la **granja de Eenie y Meenie** (los dos fusionan).
- Cueva con bomba en la zona central: 20 conchas.
- Un Picori en la esquina inferior izquierda: su fusión hace crecer una **judía** (Pieza de Corazón + rupias + conchas).
- Cueva oculta con un **Muro misterioso**.
- Agujero en la esquina NE (Bastón de Pacci): sube al Bosque minish, donde está la **Gran Hada Mariposa**.
- **Enemigos:** Acro-Bandit, Octorok rojo, Rope dorado, Keese, Peahat, Escarabajo espinoso, Escarabajo negro, Rupee Like rojo. Aparecen más enemigos según avanza la historia.

### 3.17 Bosque minish ✔ (`minish_woods`)
Sureste.
- **Aldea minish** (capital de los minish): el anciano **Gentari** y **Festari** (en su **Abadía**, con techo de cristal azul, al norte de la aldea). Hace falta la **Nuez del habla** (Jabber Nut) para entender a los minish.
- **Sepulcro del bosque** ✔, justo al norte de la aldea.
- **Casa de Belari** (bombas y, tras una fusión, **bombas remotas**).
- **Casa de la bruja** ✔ (Syrup, en el NE): vende el **Champiñón despertador** (60 rupias) y la Poción azul.
- **Gran Hada Mariposa** (en el norte, dentro de un árbol solitario): da la **Cartera más grande** si le tiras todas tus rupias.
- **Portal de viento** al NO de la Aldea minish.
- Agua profunda en la mitad norte (hacen falta las Aletas).
- **Enemigos:** Mulldozer azul, Deku, Octorok dorado, Chuchu verde, Rupee Like verde, Like Like, Octorok rojo, Pesto rojo, Sluggula.

---

## 4. Portales de viento (Wind Crests): puntos de teletransporte

Se activan tocando una pequeña lápida con una clave de sol. Con la **Ocarina del Viento** (Arco de los vientos), Zeffa te lleva a cualquiera que ya hayas descubierto. Hay **8**:

| # | Región | Ubicación |
|---|---|---|
| 1 | Monte Crenel | Junto al portal minish y la Mina de Melari |
| 2 | Pantano de Castor | Sección sur, al este de las Estatuas misteriosas |
| 3 | Pradera Sur | Al norte de la casa de Link |
| 4 | Ciudadela de Hyrule | Junto al muro oeste de la Escuela Funday |
| 5 | Lago Hylia | Al oeste del Templo de las aguas (ya activo) |
| 6 | Bosque minish | Al noroeste de la Aldea minish |
| 7 | Cascadas del Velo | Cerca de la parte alta, debajo de Biggoron |
| 8 | Sobre las nubes | Frente a la Casa de la Tribu del Viento |

---

## 5. Piezas de Corazón (44)

Cuatro piezas forman un contenedor. Con 44 piezas se consiguen 11 contenedores extra. Máximo: **20 corazones**, que salen de 3 iniciales + 11 por piezas + 6 contenedores completos (5 jefes + Librari).

| # | Región | Cómo conseguirla |
|---|---|---|
| 1 | Bosque minish | Justo al sur del Sepulcro del bosque |
| 2 | Aldea minish | Muelle noreste de la aldea |
| 3 | Sepulcro del bosque | Tarro mágico en el lado este del muro sur de la sala del Madderpillar |
| 4 | Sepulcro del bosque | Tras revelar el portal azul, vuelve a la entrada y úsalo |
| 5 | Monte Crenel | Esquina NO de la Base del Monte Crenel: ve al norte y pon una bomba entre dos árboles |
| 6 | Monte Crenel | Al este del pie del Muro de Crenel: bomba entre dos rocas |
| 7 | Cueva de las llamas | Bomba en el muro sur después de la sala con 4 trampas y una vagoneta volcada |
| 8 | Cascadas del Velo | Usa el Bastón de Pacci en un agujero del borde norte del Rancho Lon Lon |
| 9 | Pradera Norte | Bloque agrietado con bomba en la esquina NO |
| 10 | Ciudadela | Voltea con el Bastón de Pacci un portal en la casa de Romio, sube por la enredadera cerca de la Posada y rodea hasta la parte trasera |
| 11 | Bosque minish | Tras ver a Rem dormido, cruza el Rancho Lon Lon hacia el NO del bosque: está al SO, junto a un estanque |
| 12 | Monte Crenel | Dentro del dojo de Grayblade |
| 13 | Jardín del Castillo | Dentro del dojo de Grimblade |
| 14 | Rancho Lon Lon | Golpea un árbol con las Botas para revelar un portal minish y usa el camino minish |
| 15 | Pantano de Castor | Dentro del dojo de Swiftblade I |
| 16 | Ruinas del Viento | 2.º portal minish, ve al oeste y baja por la enredadera central de tres hasta una cuevita |
| 17 | Arco de los vientos | En tamaño minish (zona este), cae por el agujero cerca del Armos y pasa a la sala del este |
| 18 | Monte Crenel | Excava la cueva secreta al oeste de la cueva del Ermitaño |
| 19 | Ciudadela | Completa los Simuladores de Simon |
| 20 | Ciudadela | Con las Aletas, nada dentro de la Fuente |
| 21 | Cascadas del Velo | Entra desde la esquina NE de la Pradera Norte y nada al este |
| 22 | Lago Hylia | Bucea en el pequeño estanque junto a la Casa de Stockwell |
| 23 | Lago Hylia | Nada hasta el borde sur del lago |
| 24 | Lago Hylia | Dentro del dojo de Waveblade |
| 25 | Bosque minish | Portal minish al oeste de la aldea, ve al NO a tres cuevitas y entra en la de la izquierda |
| 26 | Pantano de Castor | Nada a la cueva de la esquina NE |
| 27 | Pantano de Castor | Fusiona con Zill o con un Picori de la aldea para que aparezca un nenúfar. En tamaño minish, llévalo al sur, ve a la esquina SE y nada al NE hasta una cuevita |
| 28 | Pradera Sur | Botas en la esquina SO: portal minish, luego nada al norte hasta una cuevita |
| 29 | Ciudadela | Nivel final del minijuego de cucos de Anju |
| 30 | Valle Real | Empuja la lápida del NO y usa las baldosas brillantes para mover un bloque grande |
| 31 | Palacio de los Vientos | Esquina NO del 4.º piso: empuja un bloque y salta el hueco con la Capa de Roc hacia la puerta del norte |
| 32 | Ciudadela | Salta dentro de la campana con la Capa de Roc |
| 33 | Lago Hylia | Con la Capa de Roc, salta entre dos islotes al norte del lago |
| 34 | Rancho Lon Lon | Salta con la Capa a la orilla norte del Lago Hylia, excava la cueva secreta y sal por la izquierda al rancho |
| 35 | Ciudadela | Reúne 130 figuras y habla con Herb (Casa de la música) |
| 36 | Ciudadela | Portal minish en la Escuela Funday, camino minish del patio: empuja la roca grande con las baldosas brillantes |
| 37 | Pradera Sur | Árbol de la esquina SE, se abre al fusionar con el Hurdy-Gurdy Man |
| 38 | Colinas del Este | Judía trepadora tras fusionar con el Picori cercano |
| 39 | Bosque del Oeste | Árbol que se abre al fusionar con el Picori del portal de viento del Lago Hylia |
| 40 | Monte Crenel | Judía trepadora en la cima tras fusionar con Melari |
| 41 | Lago Hylia | Fusiona con el Picori del borde este, salta a la orilla norte, excava la cueva y sal por la derecha hasta la judía |
| 42 | Cascadas del Velo | Cascada que se abre al fusionar con Gale |
| 43 | Cascadas del Velo | Bajío que aparece al fusionar con el Picori de la casa más al este de la Aldea minish: cueva secreta |
| 44 | Jardín del Castillo | Una fusión aleatoria vacía la fuente del NE |

Por región: Lago Hylia 5 · Ciudadela 7 · Monte Crenel 5 · Cascadas del Velo 4 · Bosque minish 3 · Pantano de Castor 3 · Rancho Lon Lon 2 · Pradera Sur 2 · Jardín del Castillo 2 · Sepulcro del bosque 2 · Aldea minish 1 · Pradera Norte 1 · Ruinas del Viento 1 · Valle Real 1 · Colinas del Este 1 · Bosque del Oeste 1 · Cueva de las llamas 1 · Arco de los vientos 1 · Palacio de los Vientos 1.

---

## 6. Hadas

### 6.1 Grandes Hadas (3), que dan mejoras

| Gran Hada | Región | Acceso | Prueba | Recompensa |
|---|---|---|---|---|
| Gran Hada Efímera (Mayfly) | Monte Crenel, Muro de Crenel (extremo derecho) | Bomba en la pared | Tira una bomba al estanque y responde con honestidad (¿bomba dorada, plateada o ninguna?) | Bolsa de bombas más grande |
| Gran Hada Mariposa (Butterfly) | Bosque minish (se llega desde la esquina NE de las Colinas del Este con el Bastón de Pacci) | Árbol solitario | Tira **todas** tus rupias | Cartera más grande |
| Gran Hada Libélula (Dragonfly) | Valle Real, a la derecha de las escaleras de la entrada | Bomba | 5 preguntas sobre tu aventura. Si fallas una, pierdes las flechas | Carcaj más grande |

### 6.2 Fuentes de hadas normales (7), para curarse o embotellar hadas
1. **Pradera Sur**: tras hierba alta junto al río (bomba).
2. **Colinas de Trilby**: entre dos rocas en el nivel inferior (bomba).
3. **Base del Monte Crenel**: NE, entre dos árboles (bomba).
4. **Monte Crenel**: entre rocas en la base del Muro de Crenel (bomba). Tiene una Pieza de Corazón en medio.
5. **Pradera Norte**: dentro de un árbol (fusión verde aleatoria).
6. **Jardín del Castillo**: bajo la fuente de la izquierda (fusión verde aleatoria).
7. **Colinas de Trilby**: cueva de Guantes topo que aparece tras un bajío (fusión azul con Candy, después del Arco de los vientos).

---

## 7. Botellas vacías (4)

| # | Región | Cómo |
|---|---|---|
| 1 | Colinas de Trilby | Te la vende un Deku en una cueva (20 rupias). Obligatoria |
| 2 | Colinas del Este | Cofre al sur de la granja de Eenie y Meenie (tras fusionar con Smith) |
| 3 | Ciudadela → Lago Hylia | Tienda de Stockwell (detrás del mostrador en tamaño minish), darle la comida a Fifi en la Casa de Stockwell |
| 4 | Rancho Lon Lon | Final de la Cueva Goron (fusión con Eenie + 5 Muros misteriosos) |

---

## 8. Muros misteriosos y Cueva Goron

Hay **5 Muros misteriosos** en cuevas secretas: Colinas del Este, Bosque minish, Monte Crenel (junto a la cueva del Ermitaño), Colinas de Trilby y Lago Hylia. Cada fusión con uno, más la de **Eenie**, añade un Goron excavador en la **Cueva Goron** del Rancho Lon Lon. Con 4 Gorons: 200 rupias. Con los 6: **Botella vacía**. El 6.º Goron (fusión en la etapa 6) despierta a **Biggoron** en Veil Springs.

---

## 9. Judías trepadoras (Beanstalks)

Crecen solo con la magia de una fusión:

| Región | Fusión con | Recompensa arriba |
|---|---|---|
| Colinas del Este | Picori (esquina inferior izquierda) | Pieza de Corazón + 200 rupias + 200 conchas |
| Monte Crenel (cima) | Melari | Pieza de Corazón + 160 rupias |
| Bosque del Oeste | Picori | 320 rupias + Kinstone rojo |
| Ruinas del Viento | Picori | Carcaj grande |
| Lago Hylia (orilla norte) | Picori (borde este del lago) | Pieza de Corazón + 200 rupias + 200 conchas |

---

## 10. Monstruos dorados

Aparecen tras ciertas fusiones. Son rápidos, aguantan mucho y **sueltan muchas rupias**.

| Monstruo | Regiones |
|---|---|
| Octorok dorado | Bosque minish, Ruinas del Viento, Bosque del Oeste |
| Rope dorado | Pantano de Castor, Colinas del Este, Jardín del Castillo |
| Tektite dorado | Monte Crenel (×2), Cascadas del Velo |

**Mariposas de la alegría** (Joy Butterflies, mejoran habilidades):
- **Ruinas del Viento**: disparas flechas más rápido (fusión con Din).
- **Pantano de Castor**: excavas más rápido (fusión con Farore).
- **Valle Real**: nadas más rápido (fusión con Nayru).

---

## 11. Maestros de espada (Blade Brothers) y Pergaminos del tigre ✔

| Maestro | Ubicación del dojo | Técnica | Requisito |
|---|---|---|---|
| Swiftblade | Ciudadela (Escuela Swiftblade) | Ataque giratorio | Volver del Sepulcro del bosque |
| Swiftblade | Ciudadela | Rompe-rocas | Espada blanca |
| Swiftblade | Ciudadela | Ataque con carrera | Botas de Pegaso |
| Swiftblade | Ciudadela | Estocada descendente | Capa de Roc |
| Grayblade | Monte Crenel (escalando el lado SE) | Ataque rodando | Espada blanca |
| Waveblade | Lago Hylia (árbol) | Rayo del peligro | Aletas |
| Grimblade | Jardín del Castillo (esquina inferior derecha) | Rayo de espada | Farol |
| Swiftblade I | Pantano de Castor | Gran ataque giratorio | Los otros 7 pergaminos |
| Scarblade | Pantano de Castor (tras una cascada, fusión con Grayblade) | Carga más rápida del giro | — |
| Splitblade | Cascadas del Velo (cueva sur, fusión con Grimblade) | Carga más rápida del desdoble | — |
| Greatblade | Pradera Norte (tras una cascada, fusión con Waveblade) | Gran giro más largo | — |

---

## 12. Portales minish (tipos)

- **Tocón** (Stump): el más común en el exterior.
- **Vasija** (Pot): en la Ciudadela. Tiene que estar boca abajo, y a veces hay que voltearla con el Bastón de Pacci.
- **Roca**: en las zonas montañosas del NO (Monte Crenel).
- **De mazmorra**: solo dentro de mazmorras.
- **Templo de las aguas**: la entrada misma del templo es un portal.

Una vez en tamaño minish, **solo puedes volver a tamaño normal en otro portal**.

---

## 13. Fusiones de Kinstones (100)

- **Total:** 100 fusiones, de las que **18 son aleatorias** (con cualquier personaje de la lista de abajo o con cualquier minish) y **9 son doradas y obligatorias** para la historia.
- En el mapa del juego, cada fusión marca un icono: **puerta o flecha** (zona nueva), **persona** (alguien tiene algo nuevo), **pergamino** (cofre nuevo), **huevo** (monstruo dorado o mariposa), **brote** (judía trepadora) y un icono especial para el portal de la Tribu del Viento.
- **Formas de Kinstone:** Verde (divisoria triangular / cuadrada / punta afilada), Azul (gota / en L), Roja (ondulada / dentada / sector), Dorada (formas únicas).

**Etapas del juego** (determinan qué fusiones están disponibles):
1. Desde que obtienes la Bolsa de Kinstones hasta que entras por primera vez al Bosque del Oeste y Vaati suplanta al rey.
2. Hasta conseguir la Ocarina del Viento (Mazaal).
3. Hasta conseguir el Elemento Agua y ver a Gustaf en el Lago Hylia.
4. Hasta recibir el Kinstone dorado de Gustaf en el Mausoleo real.
5. Hasta conseguir el Elemento Viento (Gyorg).
6. Hasta el final del juego.

**Personajes con fusiones aleatorias:** Anju, Anton, Baris, Berry, Bindle, Brent, Breve, Brocco, Chai, Ermitaño de Crenel, Cuco (tejado del gallinero de Anju), Pollito (tejado de la Escuela Swiftblade), Dottie, Doyle, Elsie, Epona, Erik, Festari, Gregal, Growler, Harrison, Herb, Jasmine, Jim, Joel, Jotari, Julietta, June, Keeley, Rey Daltus, Klaus, Leila, Lolly, Mack, Malon, Marshall, Ministro Potho, Mutoh, Pina, Purry, Rolf, Romio, Satchel, Scratcher, Sheila, Sturgeon, Talon y Verona, además de cualquier minish (del bosque, de la biblioteca, de la montaña o de la ciudad).

| Personaje | Dónde está | Kinstone | Etapa | Resultado (dónde aparece en el mapa) |
|---|---|---|---|---|
| Hurdy-Gurdy Man | Ciudadela | Azul gota | 1 | Árbol en la **Pradera Sur** con Pieza de Corazón |
| Alcalde Hagen | Casa del Alcalde | Roja ondulada | 1 | Estanque vaciado en el **Rancho Lon Lon**: cofre con Cartera grande |
| Minish de la montaña | Mina de Melari (comedor SE) | Verde triangular | 1 | Tektite dorado en el **Monte Crenel** |
| Strato | Casa del Forastero | Roja dentada | 1 | Portal en la **Pradera Sur** a la Casa de la Tribu del Viento |
| Aleatoria | — | Azul gota | 1 | Árbol en la **Pradera Norte** con Fuente de hadas |
| Aleatoria | — | Verde triangular | 1 | Estanque del **Jardín del Castillo** vaciado: Fuente de hadas |
| Aleatoria | — | Verde punta | 1 | Estanque de la **Pradera Sur** vaciado: cueva con 75 rupias |
| Aleatoria | — | Verde punta | 1 | Octorok dorado en el **Bosque minish** |
| Aleatoria | — | Verde punta | 1 | Rope dorado en las **Colinas del Este** |
| Aleatoria | — | Verde triangular | 1 | Rope dorado en el **Jardín del Castillo** |
| Aleatoria | — | Verde cuadrada | 1 | Grieta en el **Bosque minish** (junto a la aldea): cofre con Kinstone azul |
| Aleatoria | — | Verde triangular | 1 | Cofre en el camino minish de la **Escuela Funday**: Kinstone rojo |
| Aleatoria | — | Verde punta | 1 | Cofre en el camino minish del **Rancho Lon Lon**: Kinstone rojo |
| Aleatoria | — | Verde triangular | 1 | Cofre en el camino minish a la **Aldea minish**: 200 rupias |
| Aleatoria | — | Verde triangular | 1 | Cofre en el **Bosque minish**: Kinstone rojo |
| Aleatoria | — | Verde cuadrada | 1 | Cofre en el **Bosque minish**: Kinstone rojo |
| Aleatoria | — | Verde cuadrada | 1 | Cofre en el **Bosque minish**: 200 rupias |
| Aleatoria | — | Verde cuadrada | 1 | Cofre en el **Bosque minish**: Kinstone azul |
| Aleatoria | — | Verde cuadrada | 1 | Cofre en el **Rancho Lon Lon**: 200 rupias |
| Aleatoria | — | Verde triangular | 1 | Cofre en la **Pradera Norte**: 200 conchas |
| Aleatoria | — | Verde punta | 1 | Cofre en la **Pradera Sur**: 200 conchas |
| Aleatoria | — | Roja sector | 1 | Fuente del **Jardín del Castillo** vaciada: Pieza de Corazón |
| Ankle | Rancho Lon Lon | Verde triangular | 2 | Árbol en la **Pradera Norte**: Kinstone rojo + interruptor (4/4 → Bumerán mágico) |
| Deku | Pantano de Castor | Azul L | 2 | Árbol en el **Bosque minish** con un Deku que vende Kinstones |
| Candy | Posada Happy Hearth | Azul L | 2 | Bajío en las **Colinas de Trilby**: cueva secreta (Fuente de hadas) |
| David Jr. | Lago Hylia | Verde punta | 2 | Árbol en la **Pradera Norte**: 200 conchas + interruptor |
| Eenie | Colinas del Este | Azul L | 2 | El Goron del **Rancho Lon Lon** abre la Cueva Goron |
| Fifi | Casa de Stockwell | Verde punta | 2 | Cofre en el camino minish a la **Cabaña del Alcalde** (Lago Hylia): Kinstone azul |
| Picori | Base del Monte Crenel | Verde cuadrada | 2 | Cofre en la **Base del Monte Crenel**: 200 rupias |
| Goron excavador | Rancho Lon Lon | Azul gota | 2 | Llega el Mercader Goron a la **Ciudadela** |
| Knuckle | Colinas de Trilby (sobre la cueva de Guantes topo) | Verde cuadrada | 2 | Árbol en la **Pradera Norte**: Kinstone rojo + interruptor |
| Meenie | Colinas del Este | Verde cuadrada | 2 | Cofre en el **Muro de Crenel**: Kinstone azul |
| Melari | Mina de Melari | Roja sector | 2 | Judía en el **Monte Crenel**: Pieza de Corazón + 160 rupias |
| Minish de la montaña | Mina de Melari | Verde cuadrada | 2 | Cofre en el camino minish lluvioso del **Monte Crenel**: Kinstone azul |
| Minish de la montaña | Mina de Melari | Verde triangular | 2 | Cofre en el **Bosque minish**: Kinstone azul |
| Minish de la montaña | Mina de Melari | Verde punta | 2 | Cofre en el camino minish junto a las aguas termales del **Monte Crenel**: Kinstone azul |
| Estatua misteriosa (izq.) | Pantano de Castor | **Dorada** | 2 | Daña la roca (3/3 → acceso a las **Ruinas del Viento**) |
| Estatua misteriosa (centro) | Pantano de Castor | **Dorada** | 2 | Ídem |
| Estatua misteriosa (der.) | Pantano de Castor | **Dorada** | 2 | Ídem |
| Muro misterioso | Colinas del Este | Azul L | 2 | +1 Goron en la **Cueva Goron** |
| Muro misterioso | Bosque minish | Azul gota | 2 | +1 Goron |
| Muro misterioso | Monte Crenel | Azul gota | 2 | +1 Goron |
| Muro misterioso | Colinas de Trilby | Azul L | 2 | +1 Goron |
| Percy | Colinas de Trilby | Roja ondulada | 2 | Se levanta un árbol en el **Bosque del Oeste**: acceso a la Casa de Percy |
| Cartero | Ciudadela | Azul L | 2 | Marcy vende el Boletín del Espadachín en **Correos** |
| Smith | Pradera Sur | Roja sector | 2 | Cofre en las **Colinas del Este**: Botella vacía |
| Smith | Pradera Sur | Verde punta | 2 | Cofre en las **Colinas de Trilby**: Kinstone rojo |
| Tingle | Pradera Sur | Verde punta | 2 | Árbol en la **Pradera Norte**: Kinstone rojo + interruptor |
| Bremor | Ciudadela | Roja dentada | 3 | Mutoh construye una 2.ª casa vacía en la **Ciudadela** |
| Deku | Bosque minish | Verde punta | 3 | Grieta en el **Pantano de Castor**: Kinstone rojo |
| Deku | Bosque minish | Verde triangular | 3 | Octorok dorado en el **Bosque del Oeste** |
| David Jr. | Lago Hylia | Verde cuadrada | 3 | Cofre en el camino minish a la **Mina de Melari**: 200 conchas |
| Farore | Ciudadela | Roja ondulada | 3 | Llega Gorman para alquilar la casa vacía |
| Picori | Colinas del Este | Azul gota | 3 | Judía: Pieza de Corazón + 200 rupias + 200 conchas |
| Picori | Jardín del Castillo | Verde cuadrada | 3 | Árbol en el **Bosque del Oeste**: 400 rupias enterradas (Guantes topo) |
| Picori | Lago Hylia | Azul L | 3 | Judía en el **Lago Hylia**: Pieza de Corazón + 200 rupias + 200 conchas |
| Picori | Lago Hylia | Roja dentada | 3 | Árbol en el **Bosque del Oeste**: Pieza de Corazón |
| Picori (o Zill) | Aldea minish (o Ciudadela) | Verde cuadrada | 3 | Nenúfar en el **Pantano de Castor** |
| Picori (o Zill) | Aldea minish (o Ciudadela) | Verde punta | 3 | Nenúfar en el **Pantano de Castor** |
| Picori (o Zill) | Aldea minish (o Ciudadela) | Verde triangular | 3 | Nenúfar en el **Pantano de Castor** |
| Picori | Pradera Norte | Verde punta | 3 | Cofre en las **Ruinas del Viento**: 200 conchas |
| Picori | Pradera Sur | Roja sector | 3 | Syrup (Casa de la bruja) hace la Poción roja |
| Picori | Colinas de Trilby | Verde triangular | 3 | Estanque de **Trilby** vaciado: cueva con 75 rupias |
| Picori | Bosque del Oeste | Azul L | 3 | Judía: 320 rupias + Kinstone rojo |
| Picori | Ruinas del Viento | Roja ondulada | 3 | Judía: Carcaj grande |
| Grayblade | Monte Crenel | Roja dentada | 3 | Cascada en el **Pantano de Castor**: dojo de Scarblade |
| Librari | Ciudadela | Verde punta | 3 | Octorok dorado en las **Ruinas del Viento** |
| Mamá | Ciudadela | Verde triangular | 3 | Bajío en el **Lago Hylia**: cueva secreta |
| Tingle | Pradera Sur | Roja dentada | 3 | Tektite dorado en el **Monte Crenel** |
| Belari | Bosque minish | Roja dentada | 4 | Cofre en las **Ruinas del Viento**: Bolsa de bombas grande |
| Gentari | Aldea minish | Roja ondulada | 4 | Belari inventa las Bombas remotas |
| Spekter | Valle Real | Azul gota | 4 | Borlov añade una dificultad más difícil a su minijuego de cofres |
| Spekter | Valle Real | Azul L | 4 | Spookter se va: acceso al gallinero de Anju |
| Flurris | Casa de la Tribu del Viento | Verde cuadrada | 5 | Árbol en el **Bosque del Oeste**: 300 rupias enterradas |
| Flurris | Casa de la Tribu del Viento | Verde cuadrada | 5 | Rope dorado en el **Pantano de Castor** |
| Caprice | Casa de la Tribu del Viento | Verde punta | 5 | Cofre en las **Cascadas del Velo**: Kinstone azul |
| Dampé | Valle Real | Azul gota | 5 | Se abre una lápida (Gina) + cofre con 100 conchas |
| Dampé | Valle Real | Verde triangular | 5 | Grieta en las **Ruinas del Viento**: Kinstone rojo |
| Din | Ciudadela | Roja ondulada | 5 | Mariposa de la alegría en las **Ruinas del Viento** (flechas más rápidas) |
| Farore | Ciudadela | Roja dentada | 5 | Mariposa en el **Pantano de Castor** (excavar más rápido) |
| Gale | Sobre las nubes | Roja sector | 5 | Cascada en las **Cascadas del Velo**: Pieza de Corazón |
| Gina | Valle Real | Verde triangular | 5 | Árbol en el **Bosque del Oeste**: 100 conchas |
| Gina | Valle Real | Verde triangular | 5 | Cascada en la **Ciudadela**: 200 conchas |
| Grimblade | Jardín del Castillo | Roja ondulada | 5 | Cascada en las **Cascadas del Velo**: dojo de Splitblade |
| Hailey | Sobre las nubes | Verde cuadrada | 5 | Tektite dorado en las **Cascadas del Velo** |
| Nube misteriosa (NO) | Sobre las nubes | **Dorada** | 5 | Molinete (5/5 → tornado al Palacio de los Vientos) |
| Nube misteriosa (centro) | Sobre las nubes | **Dorada** | 5 | Ídem |
| Nube misteriosa (SO) | Sobre las nubes | **Dorada** | 5 | Ídem |
| Nube misteriosa (SE) | Sobre las nubes | **Dorada** | 5 | Ídem |
| Nube misteriosa (NE) | Sobre las nubes | **Dorada** | 5 | Ídem |
| Muro misterioso | Lago Hylia | Azul gota | 5 | +1 Goron en la **Cueva Goron** |
| Nayru | Ciudadela | Roja sector | 5 | Mariposa en el **Valle Real** (nadar más rápido) |
| Siroc | Casa de la Tribu del Viento | Verde triangular | 5 | Cofre en el **Valle Real**: Kinstone rojo |
| Siroc | Casa de la Tribu del Viento | Verde cuadrada | 5 | Cofre en el **Valle Real**: Kinstone rojo |
| Origen del Flujo (losa de piedra) | Cascadas del Velo | **Dorada** (de Gustaf) | 5 | Se abre la losa: cuevas hacia **Veil Springs** |
| Tina | Ciudadela | Verde punta | 5 | Cofre en las **Colinas de Trilby**: Kinstone rojo |
| Waveblade | Lago Hylia | Roja sector | 5 | Cascada en la **Pradera Norte**: dojo de Greatblade |
| Picori | Aldea minish | Roja ondulada | 6 | Bajío en las **Cascadas del Velo**: cueva con Pieza de Corazón |
| Picori | Aldea minish | Roja dentada | 6 | Grieta junto al portal de viento del **Lago Hylia**: casa de Librari (Contenedor de corazón) |
| Goron excavador | Cueva Goron | Roja dentada | 6 | Biggoron despierta en **Veil Springs** |

Los hermanos Tingle llevan la cuenta. Con las 100 fusiones completas, la Bolsa de Kinstones se convierte en el **Trofeo de Tingle**.

---

## 14. Otros coleccionables

- **Conchas misteriosas** (Mysterious Shells): están en cofres, arbustos y rocas, y se canjean en la Galería de figuras. Máximo 999. Stockwell las vende más adelante (30 conchas por 200 rupias), y la Picolita verde aumenta las probabilidades de encontrarlas. Con las **136 figuras** completas obtienes la **Medalla de Carlov**.
- **Amuletos** (Charms) de Din, Nayru y Farore: los dan los Oráculos cuando se mudan a las casas vacías de la Ciudadela.
- **Picolitas** (6 colores): las vende Beedle en la plaza de la Ciudadela.
- **Libros de la biblioteca** (3, pregunta a Maggie quién los tiene): "Bestiario de Hyrule" (casa de Julietta, Ciudadela, en tamaño minish por la chimenea de la casa de Romio), "Leyenda de los Picori" (vigas de la casa del Dr. Left, hacen falta los Brazaletes de fuerza) e "Historia de las Máscaras" (Cabaña del Alcalde, Lago Hylia). Al devolverlos, Librari te da las **Aletas**.

---

## 15. Objetos principales y dónde se consiguen (para marcar en el mapa)

| Objeto | Dónde |
|---|---|
| Espada de Smith | Casa de Link (Pradera Sur) |
| Bolsa de Kinstones | Ciudadela (Hurdy-Gurdy Man, al inicio) |
| Nuez del habla | Casa-barril de la Aldea minish |
| Tarro mágico | Sepulcro del bosque |
| Botella vacía #1 | Colinas de Trilby (Deku) |
| Anillo de agarre | Monte Crenel (Deku, cueva con bomba) |
| Bastón de Pacci | Cueva de las llamas |
| Espada blanca | Mina de Melari (Monte Crenel) |
| Bolsa de bombas (10) | Belari, Bosque minish (tras el Sepulcro del bosque) |
| Brazaletes de fuerza | Fuente de la Ciudadela |
| Champiñón despertador | Casa de la bruja (Bosque minish) |
| Botas de Pegaso | Zapatería de Rem (Ciudadela) |
| Arco | Pantano de Castor (NO, tamaño minish) |
| Guantes topo | Arco de los vientos |
| Ocarina del Viento | Arco de los vientos (azotea) |
| Aletas | Biblioteca Real (Librari, Ciudadela) |
| Farol | Templo de las aguas |
| Llave del cementerio | Choza de Dampé (Valle Real) |
| Capa de Roc | Palacio de los Vientos |
| Bumerán | Tienda de Stockwell, Ciudadela (300 rupias) |
| Bumerán mágico | Pradera Norte (4 interruptores de los árboles de Tingle) |
| Espada de los Cuatro | Santuario Elemental |
| Escudo espejo | Biggoron (Veil Springs), después de terminar el juego |
| Cartera grande | Rancho Lon Lon (fusión con el Alcalde) / Gran Hada Mariposa |
| Bolsa de bombas grande (30 / 50 / 99) | Tienda de Stockwell (600 rupias, no en la versión PAL) / Gran Hada Efímera / cofre en las Ruinas del Viento (fusión con Belari) |
| Carcaj grande | Gran Hada Libélula / judía de las Ruinas del Viento |

---

## 16. Nombres oficiales en español (verificados)

| Inglés | Español oficial |
|---|---|
| Hyrule Town | Ciudadela de Hyrule |
| Mount Crenel | Monte Gongol |
| Castor Wilds | Región Tabanta / Región inexplorada de Tabanta |
| Cloud Tops | Sobre las nubes |
| Minish Woods | Bosque minish |
| Lake Hylia | Lago Hylia |
| Deepwood Shrine | Sepulcro del bosque |
| Cave of Flames | Cueva de las llamas |
| Fortress of Winds | Arco de los vientos |
| Temple of Droplets | Templo de las aguas |
| Royal Crypt | Mausoleo real |
| Palace of Winds | Palacio de los Vientos |
| Dark Hyrule Castle | Castillo de Hyrule tenebroso |
| Syrup's Hut | Casa de la bruja / Tienda de magia de Sirope |
| Dampé's Shack | Choza de Dampé |
| Link's House | Tu casa / Casa de Link |
| Great Fairy Fountain | Fuente de la gran hada |
| Throne Room | Salón del trono |
| Post Office | Correos |
| Vacant House | Casa vacía |
| Piece of Heart | Pieza de corazón |
| Heart Container | Contenedor de corazón |
| Tiger Scroll | Pergamino del tigre |
| Golden Octorok | Octorok dorado |
