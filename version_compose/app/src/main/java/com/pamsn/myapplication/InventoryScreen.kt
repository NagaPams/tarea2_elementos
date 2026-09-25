package com.pamsn.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Detalle que se muestra en el diálogo al tocar un elemento
private data class Detail(val emoji: String, val title: String, val body: String)

// Color de cada grupo de Kinstones
private val kinstoneColors = mapOf(
    "Verdes" to Color(0xFF4CAF50),
    "Azules" to Color(0xFF2196F3),
    "Rojas" to Color(0xFFF44336),
    "Doradas" to Color(0xFFFFC107)
)

@Composable
fun InventoryScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var detail by remember { mutableStateOf<Detail?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val tabs = listOf(
        "Técnicas y objetos" to Icons.AutoMirrored.Filled.MenuBook,
        "Corazones" to Icons.Default.Favorite,
        "Kinstones" to Icons.Default.Category
    )

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            RubricDoc("Tabs", "Navegación horizontal entre vistas: Lista, Cuadrícula y Lista con encabezados.")
            PrimaryTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, (title, icon) ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) },
                        icon = { Icon(icon, contentDescription = null) }
                    )
                }
            }
            when (selectedTab) {
                0 -> ItemsList(snackbarHostState, onDetail = { detail = it })
                1 -> HeartsGrid(onDetail = { detail = it })
                2 -> KinstonesList()
            }
        }
    }

    detail?.let { d ->
        AlertDialog(
            onDismissRequest = { detail = null },
            title = { Text("${d.emoji} ${d.title}") },
            text = { Text(d.body) },
            confirmButton = { TextButton(onClick = { detail = null }) { Text("Cerrar") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemsList(snackbarHostState: SnackbarHostState, onDetail: (Detail) -> Unit) {
    // Lista de +15 elementos (8 técnicas + 20 objetos)
    val items = remember { mutableStateListOf(*inventoryItems.toTypedArray()) }
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    RubricDoc(
        "LazyColumn + SwipeToDismissBox + PullToRefreshBox",
        "Desliza para marcar como dominado (con Deshacer). Arrastra hacia abajo para recargar. Estado vacío si no quedan elementos."
    )
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                delay(1000)
                items.clear()
                items.addAll(inventoryItems)
                isRefreshing = false
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            if (items.isEmpty()) {
                // Estado vacío (dentro del LazyColumn para que se pueda arrastrar y recargar)
                item {
                    Column(
                        Modifier.fillMaxWidth().padding(top = 120.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.EmojiEvents, null, tint = Color.Gray, modifier = Modifier.size(80.dp))
                        Spacer(Modifier.height(16.dp))
                        Text("¡Lo dominaste todo!", fontSize = 18.sp, color = Color.Gray)
                        Text("Arrastra hacia abajo para ver la lista otra vez", color = Color.Gray, textAlign = TextAlign.Center)
                    }
                }
            }
            items(items, key = { it.name }) { item ->
                val isTech = item.category == "Técnica"
                val dismissState = rememberSwipeToDismissBoxState()
                SwipeToDismissBox(
                    state = dismissState,
                    onDismiss = {
                        val index = items.indexOf(item)
                        items.remove(item)
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "${item.name} dominado",
                                actionLabel = "Deshacer",
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                items.add(index.coerceAtMost(items.size), item)
                            }
                        }
                    },
                    backgroundContent = {
                        Box(
                            Modifier.fillMaxSize().background(Color(0xFF43A047)).padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(Icons.Default.Check, null, tint = Color.White)
                        }
                    }
                ) {
                    ListItem(
                        headlineContent = { Text(item.name) },
                        supportingContent = { Text(item.category) },
                        leadingContent = {
                            Icon(if (isTech) Icons.AutoMirrored.Filled.MenuBook else Icons.Default.Backpack, null)
                        },
                        modifier = Modifier.clickable {
                            onDetail(Detail(if (isTech) "📜" else "🎒", item.name, item.howTo))
                        }
                    )
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun HeartsGrid(onDetail: (Detail) -> Unit) {
    val completed = AppState.completedRegions.value
    val obtained = heartPieces.count { it.region.id in completed }
    val scheme = MaterialTheme.colorScheme

    RubricDoc("LazyVerticalGrid", "Las 44 Piezas de Corazón. Se marcan como obtenidas al completar su región en el Mapa.")
    Text(
        "Piezas obtenidas: $obtained/${heartPieces.size}",
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        textAlign = TextAlign.Center
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(heartPieces, key = { it.number }) { heart ->
            val isObtained = heart.region.id in completed
            Card(
                modifier = Modifier.padding(4.dp).aspectRatio(1f),
                colors = CardDefaults.cardColors(
                    containerColor = if (isObtained) scheme.errorContainer else scheme.surfaceVariant
                ),
                onClick = {
                    onDetail(
                        Detail(
                            "💖",
                            "Pieza de Corazón #${heart.number}",
                            heart.region.name + (if (isObtained) " (región completada)" else "") + "\n\n" + heart.poi.howTo
                        )
                    )
                }
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        if (isObtained) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        null,
                        tint = if (isObtained) Color.Red else Color.Gray
                    )
                    Text("#${heart.number}")
                }
            }
        }
    }
}

@Composable
private fun KinstonesList() {
    val scheme = MaterialTheme.colorScheme

    RubricDoc("LazyColumn con encabezados", "Renderiza distintos elementos según su tipo: encabezado de color o forma de Kinstone.")
    LazyColumn(Modifier.fillMaxSize()) {
        kinstonesByColor.forEach { (group, kinstones) ->
            item(key = group) {
                Text(
                    "$group (${kinstones.sumOf { it.fusions }} fusiones)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = scheme.onSecondaryContainer,
                    modifier = Modifier.fillMaxWidth().background(scheme.secondaryContainer).padding(8.dp)
                )
            }
            items(kinstones, key = { "$group-${it.shape}" }) { kinstone ->
                ListItem(
                    headlineContent = { Text(kinstone.shape) },
                    leadingContent = {
                        Box(Modifier.size(24.dp).background(kinstoneColors.getValue(group), CircleShape))
                    },
                    trailingContent = { Text("${kinstone.fusions} fusiones") }
                )
                HorizontalDivider()
            }
        }
    }
}
