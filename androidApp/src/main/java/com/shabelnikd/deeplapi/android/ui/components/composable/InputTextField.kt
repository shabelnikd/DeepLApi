package com.shabelnikd.deeplapi.android.ui.components.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(
    inputText: String,
    label: @Composable () -> Unit,
    onValueChange: (String) -> Unit
) {
    val brush = remember { Brush.linearGradient(colors = listOf(Color.Red, Color.Blue)) }
    TextField(
        value = inputText,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = label,
        minLines = 10,
        textStyle = TextStyle(brush = brush),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)
    )
}
