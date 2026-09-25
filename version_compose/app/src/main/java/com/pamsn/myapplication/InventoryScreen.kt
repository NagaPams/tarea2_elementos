package com.pamsn.myapplication

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InventoryScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Técnicas", "Corazones", "Kinstones")

    Column(modifier = Modifier.fillMaxSize()) {
        RubricDoc("Tabs", "Navegación horizontal deslizable.")
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(title) })
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            when (selectedTab) {
                0 -> TechList()
                1 -> HeartsGrid()
                2 -> KinstonesList()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TechList() {
    val initialTechs = listOf("Ataque giratorio", "Rompe-rocas", "Estocada descendente", "Poción azul", "Escudo espejo")
    val techs = remember { mutableStateListOf(*initialTechs.toTypedArray()) }

    RubricDoc("LazyColumn Swipe-to-Dismiss", "Desliza para dominar.")
    LazyColumn {
        items(techs, key = { it }) { tech ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { 
                    if (it == SwipeToDismissBoxValue.EndToStart || it == SwipeToDismissBoxValue.StartToEnd) {
                        techs.remove(tech)
                        true
                    } else false
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    Box(Modifier.fillMaxSize().background(Color.Green).padding(horizontal = 20.dp), contentAlignment = Alignment.CenterEnd) {
                        Icon(Icons.Default.Check, null, tint = Color.White)
                    }
                },
                content = {
                    ListItem(
                        headlineContent = { Text(tech) },
                        supportingContent = { Text("Desliza para dominar") },
                        leadingContent = { Icon(Icons.Default.MenuBook, null) }
                    )
                }
            )
            Divider()
        }
    }
}

@Composable
fun HeartsGrid() {
    val heartRegions = List(44) { "mount_crenel" }.toMutableList()
    heartRegions[0] = "hyrule_town"
    heartRegions[1] = "hyrule_town"
    val completed = AppState.completedRegions.value

    RubricDoc("LazyVerticalGrid", "Las piezas rojas están obtenidas según la zona completada en el mapa.")
    LazyVerticalGrid(columns = GridCells.Fixed(4), modifier = Modifier.fillMaxSize()) {
        items(44) { index ->
            val isObtained = completed.contains(heartRegions[index])
            Card(
                modifier = Modifier.padding(4.dp).aspectRatio(1f),
                colors = CardDefaults.cardColors(containerColor = if (isObtained) Color(0xFFFFEBEE) else Color.LightGray)
            ) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Icon(if (isObtained) Icons.Default.Favorite else Icons.Default.FavoriteBorder, null, tint = if (isObtained) Color.Red else Color.Gray)
                    Text("#${index + 1}", color = if (isObtained) Color.Red else Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun KinstonesList() {
    val items = listOf("ENCABEZADO: Verdes", "Triangular", "Cuadrada", "ENCABEZADO: Azules", "Gota", "L")
    LazyColumn {
        items(items) { item ->
            if (item.startsWith("ENCABEZADO:")) {
                Box(Modifier.fillMaxWidth().background(Color(0xFFFFF8E1)).padding(8.dp)) {
                    Text(item.replace("ENCABEZADO: ", ""), color = Color(0xFFFF8F00))
                }
            } else {
                ListItem(headlineContent = { Text(item) }, leadingContent = { Icon(Icons.Default.Star, null, tint = Color.Yellow) })
                Divider()
            }
        }
    }
}
