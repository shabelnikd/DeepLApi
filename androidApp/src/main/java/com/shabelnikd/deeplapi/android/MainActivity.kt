package com.shabelnikd.deeplapi.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shabelnikd.deeplapi.data.core.dataModule
import com.shabelnikd.deeplapi.data.core.ktorModule
import com.shabelnikd.deeplapi.domain.core.domainModule
import com.shabelnikd.deeplapi.ui.core.uiModule
import com.shabelnikd.deeplapi.ui.view.MainScreen
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Preview
@Composable
fun preview() {
    startKoin {
        modules(
            dataModule, domainModule, ktorModule, uiModule
        )
    }
    MainScreen()
}