package com.samueljuma.checkpass.presentation.home

import com.samueljuma.checkpass.data.models.Passenger
import com.samueljuma.checkpass.utils.PassengerList

data class ScannerUiState(
    val scannedQrCode: String? = null,
    val passengers: PassengerList = emptyList(),
    val passengerToCheckIn: Passenger? = null,
    val loading: Boolean = false
)