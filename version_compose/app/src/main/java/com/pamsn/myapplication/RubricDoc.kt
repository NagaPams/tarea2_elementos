package com.pamsn.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RubricDoc(title: String, description: String) {
    if (AppState.rubricMode.value) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF3E0))
                .padding(8.dp)
        ) {
            Text(text = "RÚBRICA: $title", fontWeight = FontWeight.Bold, color = Color(0xFFE65100), fontSize = 12.sp)
            Text(text = description, color = Color(0xFFE65100), fontSize = 12.sp)
        }
    }
}
