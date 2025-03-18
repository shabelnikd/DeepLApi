package com.shabelnikd.deeplapi

import androidx.compose.ui.window.ComposeUIViewController
import com.shabelnikd.deeplapi.di.appModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(appModule)
    }
    App()
}