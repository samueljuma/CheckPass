package com.samueljuma.checkpass.presentation.home

sealed class ScannerEvent {
    object NavigateToCameraScanner: ScannerEvent()
    object NavigateBack: ScannerEvent()
    data class ShowToastMessage(val message: String): ScannerEvent()
}