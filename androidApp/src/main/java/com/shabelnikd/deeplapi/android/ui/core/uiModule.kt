package com.shabelnikd.deeplapi.android.ui.core

import com.shabelnikd.deeplapi.android.data.repository.CartoonRepository
import com.shabelnikd.deeplapi.android.viewmodels.CartoonViewModel
import com.shabelnikd.deeplapi.viewmodels.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainScreenViewModel(get()) }


    single { CartoonRepository(get()) }
    viewModel { CartoonViewModel(get()) }
}