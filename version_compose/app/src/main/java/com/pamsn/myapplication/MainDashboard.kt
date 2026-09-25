package com.pamsn.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboard() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Minish Companion") },
                actions = {
                    Text("Modo Rúbrica")
                    Switch(checked = AppState.rubricMode.value, onCheckedChange = { AppState.rubricMode.value = it })
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(icon = { Icon(Icons.Default.Menu, null) }, label = { Text("Menú") }, selected = selectedTab == 0, onClick = { selectedTab = 0 })
                NavigationBarItem(icon = { Icon(Icons.Default.FilterList, null) }, label = { Text("Filtros") }, selected = selectedTab == 1, onClick = { selectedTab = 1 })
                NavigationBarItem(icon = { Icon(Icons.Default.Map, null) }, label = { Text("Mapa") }, selected = selectedTab == 2, onClick = { selectedTab = 2 })
                NavigationBarItem(icon = { Icon(Icons.Default.Backpack, null) }, label = { Text("Inventario") }, selected = selectedTab == 3, onClick = { selectedTab = 3 })
            }
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> MenuScreen(onNavToMap = { selectedTab = 2 })
                1 -> FiltersScreen()
                2 -> MapScreen()
                3 -> InventoryScreen()
            }
        }
    }
}
