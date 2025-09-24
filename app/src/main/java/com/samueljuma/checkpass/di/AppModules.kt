package com.samueljuma.checkpass.di

import androidx.lifecycle.LifecycleOwner
import com.samueljuma.checkpass.data.BroadcastScannerManager
import com.samueljuma.checkpass.data.CameraScannerManager
import com.samueljuma.checkpass.data.ScannerManagerProvider
import com.samueljuma.checkpass.domain.ScannerManager
import com.samueljuma.checkpass.presentation.home.ScannerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<ScannerManager> { ScannerManagerProvider.provideScannerManager(androidContext()) }
    viewModel { ScannerViewModel(get ()) }

}