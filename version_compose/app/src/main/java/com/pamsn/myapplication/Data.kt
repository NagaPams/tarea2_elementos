package com.pamsn.myapplication

import androidx.compose.runtime.mutableStateOf

object AppState {
    val rubricMode = mutableStateOf(false)
    val mapStyle = mutableStateOf("Pixel")
    val completedRegions = mutableStateOf(setOf<String>())
}

data class Poi(
    val emoji: String,
    val title: String,
    val howTo: String,
    val x: Float,
    val y: Float
)

data class Region(
    val id: String,
    val name: String,
    val l: Float, val t: Float, val w: Float, val h: Float,
    val img: String? = null,
    val pois: List<Poi> = emptyList()
)

val regionsList = listOf(
    Region("mount_crenel", "Monte Gongol", 0.122f, 0.065f, 0.181f, 0.319f, pois = listOf(Poi("🗡️", "", "", 0f, 0f), Poi("💖", "", "", 0f, 0f), Poi("🧚", "", "", 0f, 0f))),
    Region("castor_wilds", "Pantano de Tabanta", 0.122f, 0.507f, 0.181f, 0.263f, pois = listOf(Poi("💖", "", "", 0f, 0f), Poi("📦", "", "", 0f, 0f))),
    Region("wind_ruins", "Ruinas del Viento", 0.122f, 0.781f, 0.181f, 0.152f, pois = listOf(Poi("🗡️", "", "", 0f, 0f))),
    Region("royal_valley", "Valle Real", 0.313f, 0.065f, 0.086f, 0.319f, pois = listOf(Poi("🗡️", "", "", 0f, 0f))),
    Region("trilby_highlands", "Colinas de Trilby", 0.313f, 0.396f, 0.086f, 0.264f, pois = listOf(Poi("🧚", "", "", 0f, 0f))),
    Region("western_wood", "Bosque del Oeste", 0.313f, 0.672f, 0.086f, 0.261f, img = "maps/Bosque_del_Oeste.webp", pois = listOf(Poi("💖", "", "", 0f, 0f))),
    Region("hyrule_castle", "Castillo de Hyrule", 0.408f, 0.065f, 0.182f, 0.208f, pois = listOf(Poi("🗡️", "", "", 0f, 0f))),
    Region("north_hyrule_field", "Pradera Norte", 0.408f, 0.285f, 0.182f, 0.181f, img = "maps/Pradera_Norte_TMC.webp", pois = listOf(Poi("💖", "", "", 0f, 0f))),
    Region("hyrule_town", "Ciudadela de Hyrule", 0.408f, 0.479f, 0.182f, 0.264f, img = "maps/Ciudadela_de_Hyrule_TMC.webp", pois = listOf(Poi("💖", "", "", 0f, 0f), Poi("📦", "", "", 0f, 0f))),
    Region("south_hyrule_field", "Pradera Sur", 0.408f, 0.755f, 0.182f, 0.179f, img = "maps/Pradera_Sur_TMC.webp", pois = listOf(Poi("💖", "", "", 0f, 0f))),
    Region("veil_falls", "Cascadas del Velo", 0.600f, 0.065f, 0.086f, 0.319f, pois = listOf(Poi("💖", "", "", 0f, 0f))),
    Region("cloud_tops", "Sobre las Nubes", 0.695f, 0.065f, 0.181f, 0.267f, pois = listOf(Poi("🗡️", "", "", 0f, 0f))),
    Region("lon_lon_ranch", "Rancho Lon Lon", 0.600f, 0.396f, 0.128f, 0.264f, img = "maps/Rancho_Lon_Lon_TMC.webp", pois = listOf(Poi("💖", "", "", 0f, 0f))),
    Region("lake_hylia", "Lago Hylia", 0.695f, 0.341f, 0.181f, 0.319f, pois = listOf(Poi("🗡️", "", "", 0f, 0f))),
    Region("eastern_hills", "Colinas del Este", 0.600f, 0.672f, 0.086f, 0.261f, img = "maps/Colina_del_Este.webp", pois = listOf(Poi("💖", "", "", 0f, 0f))),
    Region("minish_woods", "Bosque Minish", 0.695f, 0.672f, 0.181f, 0.261f, pois = listOf(Poi("🗡️", "", "", 0f, 0f), Poi("💖", "", "", 0f, 0f)))
)
