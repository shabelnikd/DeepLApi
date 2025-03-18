package com.shabelnikd.deeplapi.android

import android.app.Application
import com.shabelnikd.deeplapi.ui.core.uiModule
import com.shabelnikd.deeplapi.data.core.dataModule
import com.shabelnikd.deeplapi.data.core.ktorModule
import com.shabelnikd.deeplapi.di.appModule
import com.shabelnikd.deeplapi.domain.core.domainModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                dataModule, domainModule, ktorModule, uiModule
            )
        }
    }
}