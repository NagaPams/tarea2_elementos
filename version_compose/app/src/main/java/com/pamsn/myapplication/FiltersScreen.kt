package com.pamsn.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FiltersScreen() {
    var showBosses by remember { mutableStateOf(true) }
    var proximity by remember { mutableStateOf(false) }
    var zoom by remember { mutableStateOf(1f) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Filtros del Mapa", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        
        RubricDoc("Checkbox", "Rastrear Kinstones")
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = showBosses, onCheckedChange = { showBosses = it })
            Text("Rastrear Fragmentos de Kinstone")
        }
        Divider(Modifier.padding(vertical = 8.dp))
        
        RubricDoc("Radio", "Estilo de mapa (Global)")
        Text("Estilo de Mapa:")
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            RadioButton(selected = AppState.mapStyle.value == "Pixel", onClick = { AppState.mapStyle.value = "Pixel" })
            Text("Pixel Art")
            Spacer(Modifier.width(16.dp))
            RadioButton(selected = AppState.mapStyle.value == "Satelite", onClick = { AppState.mapStyle.value = "Satelite" })
            Text("Satélite")
        }
        Divider(Modifier.padding(vertical = 8.dp))
        
        RubricDoc("Switch", "Sensor")
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text("Sensor de Corazones", modifier = Modifier.weight(1f))
            Switch(checked = proximity, onCheckedChange = { proximity = it })
        }
        Divider(Modifier.padding(vertical = 8.dp))
        
        RubricDoc("Slider", "Progreso")
        Text("Días transcurridos: ${zoom.toInt()}")
        Slider(value = zoom, onValueChange = { zoom = it }, valueRange = 1f..100f)
    }
}
