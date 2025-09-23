package com.samueljuma.checkpass.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannerViewModel: ViewModel() {
    private val _state = MutableStateFlow(ScannerUiState())
    val state = _state.asStateFlow()
    private val _event = Channel<ScannerEvent>()
    val event = _event.receiveAsFlow()

    fun triggerNavigationToCameraScanner(){
        _state.update {
            it.copy(
                scannedQrCode = null
            )
        }

        viewModelScope.launch {
            _event.send(ScannerEvent.NavigateToCameraScanner)
        }
    }

    fun onQrCodeScanned(result: String){
        _state.update {
            it.copy(
                scannedQrCode = result
            )
        }

        viewModelScope.launch {
            _event.send(ScannerEvent.NavigateBack)
        }

    }
}