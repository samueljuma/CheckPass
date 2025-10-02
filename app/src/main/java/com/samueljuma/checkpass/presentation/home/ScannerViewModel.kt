package com.samueljuma.checkpass.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samueljuma.checkpass.data.repositories.NetworkResult
import com.samueljuma.checkpass.domain.PassengerRepository
import com.samueljuma.checkpass.domain.ScannerManager
import com.samueljuma.checkpass.utils.findPassengerByTicketNumber
import com.samueljuma.checkpass.utils.isPDADevice
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannerViewModel(
    private val scannerManager: ScannerManager,
    private val passengerRepository: PassengerRepository
): ViewModel() {
    private val _state = MutableStateFlow(ScannerUiState())
    val state = _state.asStateFlow()
    private val _event = Channel<ScannerEvent>()
    val event = _event.receiveAsFlow()

    init {
        viewModelScope.launch {

            fetchPassengers()

            if (isPDADevice()) {
                startScan()
            }

            scannerManager.scannedCodes.collect { code ->
                onQrCodeScanned(code)
            }
        }

    }

    fun triggerNavigationToCameraScanner(){
        _state.update { it.copy(scannedQrCode = null) }
        viewModelScope.launch { _event.send(ScannerEvent.NavigateToCameraScanner) }
    }

    fun onQrCodeScanned(code: String){
        val state = _state
        val passengerToCheckIn = state.value.passengers.findPassengerByTicketNumber(code)
        state.update {
            it.copy(
                scannedQrCode = code,
                passengerToCheckIn = passengerToCheckIn
            )
        }
        if(!isPDADevice()){
            viewModelScope.launch { _event.send(ScannerEvent.NavigateBack) }
        }
    }

    fun startScan() = scannerManager.startScan()
    fun stopScan() = scannerManager.stopScan()

    suspend fun fetchPassengers(){
        val result = passengerRepository.fetchPassengers()
        when(result){
            is NetworkResult.Success -> {
                _state.update { it.copy(passengers = result.data) }
            }
            is NetworkResult.Error -> {
                _event.send(ScannerEvent.ShowToastMessage(result.message))
            }
        }

    }

    fun onPassCheckInAction(action: PassCheckInAction){
        when(action){
            is PassCheckInAction.CheckInPassenger -> {}
            is PassCheckInAction.DismissCheckInDialog -> {
                _state.update { it.copy(
                    passengerToCheckIn = null,
                    scannedQrCode = null
                ) }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        stopScan()
    }
}