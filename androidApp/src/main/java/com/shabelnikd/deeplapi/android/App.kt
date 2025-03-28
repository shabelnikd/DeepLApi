package com.shabelnikd.deeplapi.android

import android.app.Application
import com.shabelnikd.deeplapi.android.ui.core.uiModule
import com.shabelnikd.deeplapi.di.appModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
//                uiModule, domainModule, dataModule, ktorModule, dispatchersModule
                *appModule.toTypedArray(), uiModule
            )
        }
    }
}