package com.pamsn.minish_companionxml

import androidx.lifecycle.MutableLiveData

object AppState {
    val rubricMode = MutableLiveData(false)
    val mapStyle = MutableLiveData("Pixel")
    val completedRegions = MutableLiveData(mutableSetOf<String>())
}

data class Poi(val emoji: String)
data class Region(val id: String, val name: String, val l: Float, val t: Float, val w: Float, val h: Float, val pois: List<Poi> = emptyList())

val regionsList = listOf(
    Region("mount_crenel", "Monte Gongol", 0.122f, 0.065f, 0.181f, 0.319f, listOf(Poi("🗡️"), Poi("💖"))),
    Region("hyrule_town", "Ciudadela de Hyrule", 0.408f, 0.479f, 0.182f, 0.264f, listOf(Poi("💖"), Poi("📦"))),
    Region("minish_woods", "Bosque Minish", 0.695f, 0.672f, 0.181f, 0.261f, listOf(Poi("🗡️")))
)
