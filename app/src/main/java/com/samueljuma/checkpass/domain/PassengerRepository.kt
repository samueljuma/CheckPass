package com.samueljuma.checkpass.domain

import com.samueljuma.checkpass.data.repositories.NetworkResult
import com.samueljuma.checkpass.utils.PassengerList

interface PassengerRepository {
    suspend fun fetchPassengers(): NetworkResult<PassengerList>
}