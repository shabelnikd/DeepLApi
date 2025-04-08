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
fun ReadOnlyTextField(label: @Composable () -> Unit, value: String) {
    val brush = remember { Brush.linearGradient(colors = listOf(Color.Red, Color.Magenta)) }
    TextField(
        value = value,
        onValueChange = {},
        label = label,
        minLines = 10,
        textStyle = TextStyle(brush = brush),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        readOnly = true
    )
}