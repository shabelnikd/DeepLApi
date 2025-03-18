package com.shabelnikd.deeplapi.di

import com.shabelnikd.deeplapi.data.core.dataModule
import com.shabelnikd.deeplapi.data.core.ktorModule
import com.shabelnikd.deeplapi.domain.core.domainModule

val appModule = listOf(
    dataModule,
    ktorModule,
    domainModule,
)