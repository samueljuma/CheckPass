package com.samueljuma.checkpass.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
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