package com.pamsn.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class LoginMode { Welcome, Login, Register }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onNavigateToDashboard: () -> Unit) {
    var mode by remember { mutableStateOf(LoginMode.Welcome) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var obscurePass by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (mode == LoginMode.Welcome) "Bienvenido" else if (mode == LoginMode.Login) "Iniciar Sesión" else "Registro") },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Modo Rúbrica", fontSize = 12.sp)
                        Switch(checked = AppState.rubricMode.value, onCheckedChange = { AppState.rubricMode.value = it })
                    }
                },
                navigationIcon = {
                    if (mode != LoginMode.Welcome) {
                        IconButton(onClick = { mode = LoginMode.Welcome }) { Icon(Icons.Default.ArrowBack, "Back") }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(24.dp).fillMaxSize(),
            verticalArrangement = if (mode == LoginMode.Welcome) Arrangement.Center else Arrangement.Top
        ) {
            when (mode) {
                LoginMode.Welcome -> {
                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(80.dp).align(Alignment.CenterHorizontally), tint = Color(0xFF2E7D32))
                    Spacer(Modifier.height(16.dp))
                    Text("Minish Companion", fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(48.dp))
                    Button(onClick = { mode = LoginMode.Login }, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("Iniciar Sesión") }
                    Spacer(Modifier.height(16.dp))
                    OutlinedButton(onClick = { mode = LoginMode.Register }, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("Registrarse") }
                    Spacer(Modifier.height(24.dp))
                    TextButton(onClick = onNavigateToDashboard, modifier = Modifier.fillMaxWidth()) { Text("Continuar como invitado", color = Color.Gray) }
                }
                LoginMode.Login -> {
                    RubricDoc("TextField (Email)", "Solo pide correo en login.")
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo electrónico") }, leadingIcon = { Icon(Icons.Default.Email, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(16.dp))
                    RubricDoc("TextField (Contraseña)", "Campo oculto para password.")
                    OutlinedTextField(
                        value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = { IconButton(onClick = { obscurePass = !obscurePass }) { Icon(if (obscurePass) Icons.Default.Visibility else Icons.Default.VisibilityOff, null) } },
                        visualTransformation = if (obscurePass) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(32.dp))
                    Button(onClick = onNavigateToDashboard, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("Entrar") }
                }
                LoginMode.Register -> {
                    RubricDoc("TextFields (Registro)", "Formulario completo.")
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, leadingIcon = { Icon(Icons.Default.Email, null) }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, leadingIcon = { Icon(Icons.Default.Lock, null) }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    RubricDoc("TextField (Teléfono)", "Teclado numérico.")
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") }, leadingIcon = { Icon(Icons.Default.Phone, null) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = region, onValueChange = { region = it }, label = { Text("Región") }, leadingIcon = { Icon(Icons.Default.Place, null) }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    RubricDoc("TextField (Multilínea)", "maxLines > 1.")
                    OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Biografía") }, minLines = 3, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(24.dp))
                    Button(onClick = onNavigateToDashboard, modifier = Modifier.fillMaxWidth().height(50.dp)) { Text("Crear cuenta") }
                }
            }
        }
    }
}
