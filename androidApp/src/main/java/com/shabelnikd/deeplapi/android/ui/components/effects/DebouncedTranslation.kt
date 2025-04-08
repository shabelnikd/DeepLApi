package com.shabelnikd.deeplapi.android.ui.components.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class)
@Composable
fun DebouncedTranslation(textFlow: MutableStateFlow<String>, onTranslate: () -> Unit) {
    LaunchedEffect(textFlow) {
        textFlow
            .debounce(2000L)
            .onEach {
                onTranslate()
            }
            .launchIn(this)
    }
}