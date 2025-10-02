package com.samueljuma.checkpass.data.scannermanager

import android.content.Context
import com.samueljuma.checkpass.domain.ScannerManager
import com.samueljuma.checkpass.utils.isPDADevice

object ScannerManagerProvider {
    fun provideScannerManager(context: Context): ScannerManager {
        return if (isPDADevice()) {
            BroadcastScannerManager(context)
        } else {
            CameraScannerManager(context)
        }
    }
}