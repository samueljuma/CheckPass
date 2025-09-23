package com.samueljuma.checkpass.di

import com.samueljuma.checkpass.presentation.home.ScannerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { ScannerViewModel() }
}