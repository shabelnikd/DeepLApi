package com.shabelnikd.deeplapi.data.core

import com.shabelnikd.deeplapi.data.datasource.deeplapi.TranslateApiService
import com.shabelnikd.deeplapi.data.repository.TranslationRepositoryImpl
import com.shabelnikd.deeplapi.domain.repository.TranslationRepository
import org.koin.dsl.module

val dataModule = module {
    single { TranslateApiService(get()) }
    single<TranslationRepository> { TranslationRepositoryImpl(get()) }

}