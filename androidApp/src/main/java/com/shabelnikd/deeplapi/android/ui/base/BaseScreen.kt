package com.shabelnikd.deeplapi.android.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel


val LocalViewModel = staticCompositionLocalOf<ViewModel?> { null }

@Composable
fun <VM : ViewModel> BaseScreen(
    viewModel: VM,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalViewModel provides viewModel) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SnackbarHost(hostState = snackBarHostState)
            content()
        }
    }
}