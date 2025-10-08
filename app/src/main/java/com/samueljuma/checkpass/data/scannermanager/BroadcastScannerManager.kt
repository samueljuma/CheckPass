package com.samueljuma.checkpass.data.scannermanager

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.content.ContextCompat
import com.samueljuma.checkpass.domain.ScannerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class BroadcastScannerManager(
    private val context: Context
): ScannerManager {

    companion object {
        const val SCAN_ACTION = "scan.rcv.message"
    }

    private val _scannedCodes = MutableSharedFlow<String>()
    override val scannedCodes: SharedFlow<String> = _scannedCodes.asSharedFlow()

    private val scanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent ?: return

            val barcode = intent.getByteArrayExtra("barocode")
            val barcodeLen = intent.getIntExtra("length", 0)
            val barcodeStr = barcode?.let { String(it, 0, barcodeLen) }.orEmpty()

            if (barcodeStr.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    _scannedCodes.emit(barcodeStr)
                }
            }
        }
    }


    override fun startScan() {
        val filter = IntentFilter(SCAN_ACTION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(scanReceiver, filter, Context.RECEIVER_EXPORTED)
        } else {
            ContextCompat.registerReceiver(
                context,
                scanReceiver,
                filter,
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
    }

    override fun stopScan() {
        context.unregisterReceiver(scanReceiver)
    }
}



