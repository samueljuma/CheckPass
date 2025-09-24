package com.samueljuma.checkpass.presentation.home

import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.samueljuma.checkpass.data.CameraScannerManager
import com.samueljuma.checkpass.domain.ScannerManager
import com.samueljuma.checkpass.utils.CollectOneTimeEvent
import org.koin.compose.getKoin

@Composable
fun CameraScannerScreen(
    navController: NavController,
    viewModel: ScannerViewModel
){
    val lifecycleOwner = LocalLifecycleOwner.current

    CollectOneTimeEvent(viewModel.event) {
        when (it) {
            is ScannerEvent.NavigateBack -> {
                navController.popBackStack()
            }
            else -> {}
        }
    }

    val scannerManager = getKoin().get<ScannerManager>() as CameraScannerManager

    // Start scanning when the screen appears, stop when leaving
    DisposableEffect(lifecycleOwner) {
        scannerManager.attachLifecycleOwner(lifecycleOwner)
        viewModel.startScan()

        onDispose {
            viewModel.stopScan()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { scannerManager.previewView }
    )

}