package com.samueljuma.checkpass.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samueljuma.checkpass.domain.ScannerManager
import com.samueljuma.checkpass.utils.isPDADevice
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val scannerManager: ScannerManager
): ViewModel() {
    private val _state = MutableStateFlow(ScannerUiState())
    val state = _state.asStateFlow()
    private val _event = Channel<ScannerEvent>()
    val event = _event.receiveAsFlow()

    init {
        if(isPDADevice()){
            startScan()
        }
        viewModelScope.launch {
            scannerManager.scannedCodes.collect { code ->
                onQrCodeScanned(code)
            }
        }
    }

    fun triggerNavigationToCameraScanner(){
        _state.update { it.copy(scannedQrCode = null) }
        viewModelScope.launch { _event.send(ScannerEvent.NavigateToCameraScanner) }
    }

    fun onQrCodeScanned(result: String){
        _state.update { it.copy(scannedQrCode = result) }
        viewModelScope.launch { _event.send(ScannerEvent.NavigateBack) }
    }


    fun startScan() = scannerManager.startScan()
    fun stopScan() = scannerManager.stopScan()

    override fun onCleared() {
        super.onCleared()
        stopScan()
    }
}