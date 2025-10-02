package com.samueljuma.checkpass.di

import com.samueljuma.checkpass.data.repositories.PassengerRepositoryImpl
import com.samueljuma.checkpass.data.scannermanager.ScannerManagerProvider
import com.samueljuma.checkpass.domain.PassengerRepository
import com.samueljuma.checkpass.domain.ScannerManager
import com.samueljuma.checkpass.presentation.home.ScannerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<ScannerManager> { ScannerManagerProvider.provideScannerManager(androidContext()) }
    single<PassengerRepository> { PassengerRepositoryImpl(androidContext()) }
    viewModel { ScannerViewModel(get (), get()) }

}