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
fun DebouncedDetectLanguage(textFlow: MutableStateFlow<String>, onDetect: () -> Unit) {
    LaunchedEffect(textFlow) {
        textFlow.debounce(200L).onEach {
            onDetect()
        }
            .launchIn(this)

    }
}

