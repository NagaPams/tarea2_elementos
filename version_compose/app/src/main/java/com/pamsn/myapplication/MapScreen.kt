package com.pamsn.myapplication

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current
    var selectedRegion by remember { mutableStateOf<Region?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val mapImage = remember(AppState.mapStyle.value) {
        val filename = if (AppState.mapStyle.value == "Pixel") "map.jpg" else "map_satellite.png"
        try {
            BitmapFactory.decodeStream(context.assets.open(filename)).asImageBitmap()
        } catch (e: Exception) { null }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            RubricDoc("LinearProgressIndicator", "Progreso.")
            LinearProgressIndicator(progress = { 0.15f }, modifier = Modifier.fillMaxWidth().padding(16.dp))

            RubricDoc("Modifier.graphicsLayer (Zoom)", "Interactive map.")
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, 4f)
                            offset += pan
                        }
                    }
            ) {
                if (mapImage != null) {
                    Canvas(
                        modifier = Modifier.fillMaxSize().graphicsLayer(scaleX = scale, scaleY = scale, translationX = offset.x, translationY = offset.y)
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = canvasWidth * (mapImage.height.toFloat() / mapImage.width.toFloat())
                        
                        drawImage(
                            image = mapImage,
                            dstSize = androidx.compose.ui.unit.IntSize(canvasWidth.toInt(), canvasHeight.toInt())
                        )
                    }
                    
                    Box(modifier = Modifier.fillMaxSize().graphicsLayer(scaleX = scale, scaleY = scale, translationX = offset.x, translationY = offset.y)) {
                        val dummyWidth = 400.dp 
                        // Simulate regions visually using Box
                        if (AppState.mapStyle.value == "Pixel") {
                            regionsList.forEach { reg ->
                                val isCompleted = AppState.completedRegions.value.contains(reg.id)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(reg.w)
                                        .fillMaxHeight(reg.h)
                                        .offset(x = 100.dp, y = 100.dp) // placeholder relative offsets
                                        .background(if (isCompleted) Color(0x5500FF00) else Color.Transparent)
                                        .border(1.dp, Color(0x33FFFFFF))
                                        .clickable {
                                            selectedRegion = reg
                                            scope.launch { snackbarHostState.showSnackbar("Abriendo ${reg.name}") }
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (selectedRegion != null) {
            ModalBottomSheet(onDismissRequest = { selectedRegion = null }) {
                Column(Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(selectedRegion!!.name, style = MaterialTheme.typography.headlineMedium)
                        Badge { Text("${selectedRegion!!.pois.size}") }
                    }
                    Divider(Modifier.padding(vertical = 8.dp))
                    Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Mapa detallado aquí (Imagen WebP)") }
                    }
                    Spacer(Modifier.height(16.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TravelExplore, null, modifier = Modifier.size(40.dp))
                            Spacer(Modifier.width(16.dp))
                            Text("Explora esta zona para buscar corazones, kinstones y secretos.")
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) { Text("Marcar como Completada") }
                }
            }
        }

        if (showDialog && selectedRegion != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmar") },
                text = { Text("¿Marcar ${selectedRegion!!.name} como explorado al 100%?") },
                confirmButton = { 
                    Button(onClick = { 
                        AppState.completedRegions.value = AppState.completedRegions.value + selectedRegion!!.id
                        showDialog = false
                        selectedRegion = null
                    }) { Text("Aceptar") }
                },
                dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
            )
        }
    }
}
