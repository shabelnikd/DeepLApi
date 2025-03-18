package com.shabelnikd.deeplapi.domain.core

import com.shabelnikd.deeplapi.domain.usecases.TranslateTextUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { TranslateTextUseCase(get()) }
}