package com.samueljuma.checkpass.presentation.home

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.samueljuma.checkpass.utils.CollectOneTimeEvent

@Composable
fun CameraScannerScreen(
    navController: NavController,
    onResult: (String) -> Unit,
    viewModel: ScannerViewModel
){
    val context = LocalContext.current

    CollectOneTimeEvent(viewModel.event) {
        when (it) {
            is ScannerEvent.NavigateBack -> {
                navController.popBackStack()
            }
            else -> {}
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val cameraController = LifecycleCameraController(ctx).apply {
                bindToLifecycle(context as LifecycleOwner)
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                    .build()
                val barcodeScanner = BarcodeScanning.getClient(options)

                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(ctx),
                    MlKitAnalyzer(
                        listOf(barcodeScanner),
                        COORDINATE_SYSTEM_VIEW_REFERENCED,
                        ContextCompat.getMainExecutor(ctx)
                    ) { result ->
                        val barcodes = result?.getValue(barcodeScanner)
                        barcodes?.forEach { barcode ->
                            barcode.rawValue?.let { code ->
                                onResult(code) // Pass the result to the callback
                            }
                        }
                    }
                )
            }

            previewView.controller = cameraController
            previewView

        }
    )
}