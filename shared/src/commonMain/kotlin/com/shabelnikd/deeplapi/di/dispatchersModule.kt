package com.shabelnikd.deeplapi.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {
    single(named("IODispatcher")) { Dispatchers.IO}
    single { Dispatchers.Main }
    single { Dispatchers.Default }
    single { Dispatchers.Unconfined }
}