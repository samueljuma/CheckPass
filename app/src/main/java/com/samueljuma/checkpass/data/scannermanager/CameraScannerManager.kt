package com.samueljuma.checkpass.data.scannermanager

import android.content.Context
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.samueljuma.checkpass.domain.ScannerManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class CameraScannerManager(
    private val context: Context
) : ScannerManager {

    private val _scannedCodes = MutableSharedFlow<String>(extraBufferCapacity = 1)
    override val scannedCodes: SharedFlow<String> = _scannedCodes

    val previewView: PreviewView by lazy {
        PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    private var cameraController: LifecycleCameraController? = null
    private var lifecycleOwner: LifecycleOwner? = null

    fun attachLifecycleOwner(owner: LifecycleOwner) {
        lifecycleOwner = owner
    }

    override fun startScan() {
        val owner = lifecycleOwner ?: throw IllegalStateException(
            "LifecycleOwner not attached. Call attachLifecycleOwner() before startScan()."
        )

        val controller = LifecycleCameraController(context).apply {
            bindToLifecycle(owner)
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
            val barcodeScanner = BarcodeScanning.getClient(options)

            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                MlKitAnalyzer(
                    listOf(barcodeScanner),
                    COORDINATE_SYSTEM_VIEW_REFERENCED,
                    ContextCompat.getMainExecutor(context)
                ) { result ->
                    val barcodes = result?.getValue(barcodeScanner)
                    barcodes?.forEach { barcode ->
                        barcode.rawValue?.let { code ->
                            _scannedCodes.tryEmit(code)
                        }
                    }
                }
            )
        }

        previewView.controller = controller
        cameraController = controller
    }

    override fun stopScan() {
        cameraController?.unbind()
        cameraController = null
    }
}
