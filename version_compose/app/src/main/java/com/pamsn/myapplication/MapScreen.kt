package com.pamsn.myapplication

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// Carga una imagen de assets (incluye /shared_assets); devuelve null si no existe
private fun loadAsset(context: Context, path: String): ImageBitmap? =
    try {
        context.assets.open(path).use { BitmapFactory.decodeStream(it)?.asImageBitmap() }
    } catch (e: Exception) {
        null
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen() {
    val context = LocalContext.current
    var selectedRegion by remember { mutableStateOf<Region?>(null) }
    var selectedPoi by remember { mutableStateOf<Poi?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val isPixel = AppState.mapStyle.value == "Pixel"
    val mapImage = remember(isPixel) {
        loadAsset(context, if (isPixel) "map.jpg" else "map_satellite.webp")
    }
    val completed = AppState.completedRegions.value

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Indicador de progreso real (zonas completadas / total)
            RubricDoc("LinearProgressIndicator", "Progreso determinado según las zonas marcadas como completadas.")
            val progress = completed.size.toFloat() / regionsList.size
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text("Progreso del Mundo", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF2E7D32))
                Spacer(Modifier.height(8.dp))
                LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(8.dp))
                Spacer(Modifier.height(4.dp))
                Text(
                    "Zonas completadas: ${completed.size}/${regionsList.size} (${(progress * 100).toInt()}%)",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            RubricDoc("Box + graphicsLayer (Zoom)", "Box superpone zonas táctiles sobre la imagen; pellizca para hacer zoom y arrastra para moverte.")
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(isPixel) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(1f, if (isPixel) 4f else 8f)
                            offset = if (scale == 1f) Offset.Zero else offset + pan
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (mapImage == null) {
                    Text("No se pudo cargar el mapa", color = Color.Gray)
                    return@BoxWithConstraints
                }
                // Ajustamos la imagen al espacio disponible sin deformarla (ancho y alto)
                val aspect = mapImage.width.toFloat() / mapImage.height
                var mapWidth = maxWidth
                var mapHeight = mapWidth / aspect
                if (mapHeight > maxHeight) {
                    mapHeight = maxHeight
                    mapWidth = mapHeight * aspect
                }

                Box(
                    modifier = Modifier
                        .size(mapWidth, mapHeight)
                        .graphicsLayer(scaleX = scale, scaleY = scale, translationX = offset.x, translationY = offset.y)
                ) {
                    Image(bitmap = mapImage, contentDescription = "Mapa de Hyrule", contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize())

                    // Las coordenadas de las zonas solo corresponden al mapa Pixel
                    if (isPixel) {
                        regionsList.forEach { reg ->
                            val isCompleted = completed.contains(reg.id)
                            Box(
                                modifier = Modifier
                                    .offset(x = mapWidth * reg.l, y = mapHeight * reg.t)
                                    .size(width = mapWidth * reg.w, height = mapHeight * reg.h)
                                    .background(if (isCompleted) Color(0x5500C853) else Color.Transparent)
                                    .border(1.dp, Color(0x33FFFFFF))
                                    .clickable {
                                        selectedRegion = reg
                                        scope.launch { snackbarHostState.showSnackbar("Abriendo zona: ${reg.name}...") }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = (if (isCompleted) "✅ " else "") + reg.pois.joinToString(" ") { it.emoji },
                                    fontSize = 7.sp,
                                    lineHeight = 9.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Hoja inferior con el detalle de la región
        selectedRegion?.let { region ->
            ModalBottomSheet(onDismissRequest = { selectedRegion = null }) {
                RegionDetail(
                    region = region,
                    onPoiClick = { selectedPoi = it },
                    onCompleteClick = { showDialog = true }
                )
            }
        }

        // Diálogo con cómo conseguir el elemento tocado
        selectedPoi?.let { poi ->
            AlertDialog(
                onDismissRequest = { selectedPoi = null },
                title = { Text("${poi.emoji} ${poi.title}") },
                text = { Text(poi.howTo) },
                confirmButton = { TextButton(onClick = { selectedPoi = null }) { Text("Cerrar") } }
            )
        }

        // Diálogo de confirmación
        val region = selectedRegion
        if (showDialog && region != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirmar Exploración") },
                text = { Text("¿Deseas marcar ${region.name} como explorado al 100%?") },
                confirmButton = {
                    Button(onClick = {
                        AppState.completedRegions.value = AppState.completedRegions.value + region.id
                        showDialog = false
                        selectedRegion = null
                    }) { Text("Aceptar") }
                },
                dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
            )
        }
    }
}

@Composable
private fun RegionDetail(region: Region, onPoiClick: (Poi) -> Unit, onCompleteClick: () -> Unit) {
    val context = LocalContext.current
    val detailImage = remember(region.id) { region.img?.let { loadAsset(context, it) } }

    Column(
        Modifier
            .fillMaxHeight(0.85f)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Título con Badge numérico
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(region.name, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.weight(1f))
            BadgedBox(badge = { Badge { Text("${region.pois.size}") } }) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(30.dp))
            }
        }
        HorizontalDivider(Modifier.padding(vertical = 8.dp))

        // Mapa detallado con los emojis encima
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Mapa Detallado", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                if (detailImage == null) {
                    Text("Mapa detallado no disponible", color = Color.Gray)
                } else {
                    Text("Toca un emoji para ver cómo conseguirlo", fontSize = 12.sp, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    BoxWithConstraints(
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(region.imgWidth.toFloat() / region.imgHeight)
                    ) {
                        val markerSize = 26.dp
                        Image(bitmap = detailImage, contentDescription = region.name, contentScale = ContentScale.FillBounds, modifier = Modifier.fillMaxSize())
                        region.pois.forEach { poi ->
                            Box(
                                modifier = Modifier
                                    .offset(x = maxWidth * poi.x - markerSize / 2, y = maxHeight * poi.y - markerSize / 2)
                                    .size(markerSize)
                                    .shadow(3.dp, CircleShape)
                                    .background(Color.White.copy(alpha = 0.85f), CircleShape)
                                    .clickable { onPoiClick(poi) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(poi.emoji, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        // Pesos proporcionales (Modifier.weight)
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.TravelExplore, null, tint = Color(0xFF2E7D32), modifier = Modifier.weight(1f).size(40.dp))
                Text(
                    "Explora esta zona para buscar corazones, hadas y secretos. En esta región hay:\n" +
                        region.pois.joinToString(" ") { it.emoji },
                    color = Color(0xFF1B5E20),
                    modifier = Modifier.weight(3f)
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        // Lista con cómo conseguir cada cosa
        Text("Cómo conseguir cada cosa", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        region.pois.forEach { poi ->
            ListItem(
                leadingContent = { Text(poi.emoji, fontSize = 24.sp) },
                headlineContent = { Text(poi.title) },
                supportingContent = { Text(poi.howTo) },
                modifier = Modifier.clickable { onPoiClick(poi) }
            )
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = onCompleteClick, modifier = Modifier.fillMaxWidth()) { Text("Marcar como Completada") }
        Spacer(Modifier.height(24.dp))
    }
}
