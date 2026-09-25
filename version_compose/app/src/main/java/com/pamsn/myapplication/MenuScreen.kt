package com.pamsn.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(onNavToMap: () -> Unit) {
    val scrollState = rememberScrollState()
    var showSecret by remember { mutableStateOf(false) }
    var showCredits by remember { mutableStateOf(false) }
    var isSyncing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(scrollState)) {
            Text("Acciones del Mapa", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))

            RubricDoc("ElevatedButton", "Botón primario.")
            ElevatedButton(onClick = { onNavToMap() }, modifier = Modifier.fillMaxWidth()) { Text("Entrar al Mapa Interactivo") }
            
            Spacer(Modifier.height(16.dp))
            RubricDoc("OutlinedButton", "Botón con contorno.")
            OutlinedButton(onClick = { scope.launch { snackbarHostState.showSnackbar("El registro está en la pestaña de Inventario") } }, modifier = Modifier.fillMaxWidth()) { Text("Ver Registro de Ítems") }
            
            Spacer(Modifier.height(16.dp))
            RubricDoc("TextButton", "Botón sin fondo.")
            TextButton(onClick = { showCredits = true }) { Text("Leer créditos de la app") }
            
            Spacer(Modifier.height(16.dp))
            RubricDoc("Botones con Icono", "ElevatedButton.icon y IconButton")
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { showSecret = true }) { Icon(Icons.Default.MenuBook, null); Spacer(Modifier.width(8.dp)); Text("Notas Secretas") }
                IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Ajustes abiertos") } }) { Icon(Icons.Default.Settings, null) }
            }
            
            Spacer(Modifier.height(16.dp))
            RubricDoc("FloatingActionButton", "FAB normal y extendido")
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                FloatingActionButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Marcador añadido") } }) { Icon(Icons.Default.LocationOn, null) }
                ExtendedFloatingActionButton(onClick = { 
                    isSyncing = true
                    scope.launch { delay(2000); isSyncing = false }
                }, icon = { Icon(Icons.Default.Sync, null) }, text = { Text("Sincronizar") })
            }
        }

        if (showCredits) {
            AlertDialog(
                onDismissRequest = { showCredits = false },
                title = { Text("Créditos") },
                text = { Text("Zelda Map Companion\nDesarrollado en Jetpack Compose.") },
                confirmButton = { TextButton(onClick = { showCredits = false }) { Text("Cerrar") } }
            )
        }

        if (showSecret) {
            ModalBottomSheet(onDismissRequest = { showSecret = false }) {
                Box(modifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp)) {
                    Text("Rumor: Hay un muro misterioso en las Colinas del Este.", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        if (isSyncing) {
            AlertDialog(
                onDismissRequest = {},
                text = { Row { CircularProgressIndicator(); Spacer(Modifier.width(16.dp)); Text("Sincronizando...") } },
                confirmButton = {}
            )
        }
    }
}
