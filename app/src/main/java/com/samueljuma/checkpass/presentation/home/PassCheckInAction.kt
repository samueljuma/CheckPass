package com.samueljuma.checkpass.presentation.home

import com.samueljuma.checkpass.data.models.Passenger

sealed class PassCheckInAction {
    data class CheckInPassenger(val passenger: Passenger) : PassCheckInAction()
    object DismissCheckInDialog : PassCheckInAction()
}