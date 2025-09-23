package com.samueljuma.checkpass.presentation

import android.app.Application
import com.samueljuma.checkpass.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CheckPassApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CheckPassApplication)
            modules(appModules)
        }
    }
}