package com.shabelnikd.deeplapi.ui.core

import com.shabelnikd.deeplapi.ui.viewmodels.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainScreenViewModel(get()) }
}