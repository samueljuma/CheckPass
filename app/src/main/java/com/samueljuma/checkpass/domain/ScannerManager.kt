package com.samueljuma.checkpass.domain

import kotlinx.coroutines.flow.SharedFlow

interface ScannerManager {
    val scannedCodes: SharedFlow<String>
    fun startScan()
    fun stopScan()
}