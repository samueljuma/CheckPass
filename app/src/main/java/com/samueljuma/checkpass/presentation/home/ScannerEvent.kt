package com.samueljuma.checkpass.presentation.home

sealed class ScannerEvent {
    object NavigateToCameraScanner: ScannerEvent()
    data class QrCodeScanned(val result: String): ScannerEvent()
    object NavigateBack: ScannerEvent()
}