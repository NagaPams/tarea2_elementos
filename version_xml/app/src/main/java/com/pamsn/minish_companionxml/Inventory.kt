package com.pamsn.minish_companionxml

// Datos de la pestaña Inventario (mismos que version_flutter/lib/data/inventory.dart).
// Fuente: docs/minish-cap-mapa.md

data class InventoryItem(
    val category: String, // "Técnica" u "Objeto"
    val name: String,
    val howTo: String
)

data class KinstoneType(
    val shape: String,
    val fusions: Int // cuántas de las 100 fusiones usan esta forma
)

val inventoryItems = listOf(
    InventoryItem("Técnica", "Ataque giratorio", "Swiftblade (Escuela Swiftblade, Ciudadela) te la enseña al volver del Sepulcro del bosque. Es la única técnica obligatoria."),
    InventoryItem("Técnica", "Rompe-rocas", "Swiftblade (Ciudadela), cuando tengas la Espada blanca."),
    InventoryItem("Técnica", "Ataque rodando", "Grayblade (dojo del Monte Gongol, lado SE), cuando tengas la Espada blanca."),
    InventoryItem("Técnica", "Ataque con carrera", "Swiftblade (Ciudadela), cuando tengas las Botas de Pegaso."),
    InventoryItem("Técnica", "Rayo del peligro", "Waveblade (dojo en un árbol del Lago Hylia), cuando tengas las Aletas."),
    InventoryItem("Técnica", "Rayo de espada", "Grimblade (esquina inferior derecha del Jardín del Castillo), cuando tengas el Farol."),
    InventoryItem("Técnica", "Estocada descendente", "Swiftblade (Ciudadela), cuando tengas la Capa de Roc."),
    InventoryItem("Técnica", "Gran ataque giratorio", "Swiftblade I (dojo del Pantano de Tabanta), cuando tengas los otros 7 pergaminos."),
    InventoryItem("Objeto", "Nuez del habla", "Casa-barril de la Aldea minish. Sirve para entender a los minish."),
    InventoryItem("Objeto", "Tarro mágico", "Sepulcro del bosque (1.ª mazmorra), tras vencer al Madderpillar."),
    InventoryItem("Objeto", "Anillo de agarre", "Monte Gongol: te lo vende un Deku en una cueva que se abre con bomba."),
    InventoryItem("Objeto", "Bastón de Pacci", "Cueva de las llamas (2.ª mazmorra), tras vencer a los Chuchus espinosos."),
    InventoryItem("Objeto", "Espada blanca", "Melari la forja en su mina (Monte Gongol) tras conseguir el Elemento Tierra."),
    InventoryItem("Objeto", "Bolsa de bombas", "Belari te la regala en el Bosque minish tras el Sepulcro del bosque."),
    InventoryItem("Objeto", "Brazaletes de fuerza", "Dentro de la Fuente de la Ciudadela (tamaño minish; entra desde la casa del Dr. Left)."),
    InventoryItem("Objeto", "Champiñón despertador", "Casa de la bruja Syrup (NE del Bosque minish), por 60 rupias."),
    InventoryItem("Objeto", "Botas de Pegaso", "Zapatería de Rem (Ciudadela): despiértalo con el Champiñón despertador."),
    InventoryItem("Objeto", "Arco", "NO del Pantano de Tabanta: encógete, cruza en nenúfar y vence a 5 Mulldozers."),
    InventoryItem("Objeto", "Guantes topo", "Arco de los vientos (3.ª mazmorra), tras un muro con bomba después del Darknut."),
    InventoryItem("Objeto", "Ocarina del Viento", "Azotea del Arco de los vientos, tras vencer a Mazaal. Te lleva a los portales de viento."),
    InventoryItem("Objeto", "Aletas", "Biblioteca Real (Ciudadela): devuelve los 3 libros y Librari te las da."),
    InventoryItem("Objeto", "Farol", "Templo de las aguas (4.ª mazmorra), tras vencer al Chuchu azul gigante."),
    InventoryItem("Objeto", "Llave del cementerio", "Dampé te la da en su choza del Valle Real."),
    InventoryItem("Objeto", "Capa de Roc", "Palacio de los Vientos (5.ª mazmorra)."),
    InventoryItem("Objeto", "Bumerán", "Tienda de Stockwell (Ciudadela), por 300 rupias."),
    InventoryItem("Objeto", "Bumerán mágico", "Pradera Norte: abre los 4 árboles fusionando con Tingle y sus hermanos y pulsa los 4 interruptores."),
    InventoryItem("Objeto", "Espada de los Cuatro", "Santuario Elemental, al infundir los 4 elementos en la Espada blanca."),
    InventoryItem("Objeto", "Escudo espejo", "Biggoron (Veil Springs): dale tu Escudo pequeño; se recoge después de terminar el juego."),
)

// Kinstones agrupados por color (la clave es el encabezado de la sección)
val kinstonesByColor = linkedMapOf(
    "Verdes" to listOf(KinstoneType("Divisoria triangular", 17), KinstoneType("Divisoria cuadrada", 16), KinstoneType("Punta afilada", 16)),
    "Azules" to listOf(KinstoneType("Gota", 9), KinstoneType("En forma de L", 9)),
    "Rojas" to listOf(KinstoneType("Ondulada", 8), KinstoneType("Dentada", 9), KinstoneType("Sector (abanico)", 7)),
    "Doradas" to listOf(KinstoneType("Formas únicas (historia)", 9))
)

// Las 44 Piezas de Corazón, sacadas de los datos del mapa y ordenadas por número
data class HeartPiece(val number: Int, val poi: Poi, val region: Region)

val heartPieces: List<HeartPiece> = regionsList
    .flatMap { region -> region.pois.filter { it.emoji == "💖" }.map { poi -> region to poi } }
    .map { (region, poi) -> HeartPiece(poi.title.substringAfter("#").toInt(), poi, region) }
    .sortedBy { it.number }
