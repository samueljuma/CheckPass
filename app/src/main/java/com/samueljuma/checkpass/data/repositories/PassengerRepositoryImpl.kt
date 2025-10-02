package com.samueljuma.checkpass.data.repositories

import android.content.Context
import android.util.Log
import com.samueljuma.checkpass.data.models.Passenger
import com.samueljuma.checkpass.domain.PassengerRepository
import com.samueljuma.checkpass.utils.PassengerList
import kotlinx.serialization.json.Json

class PassengerRepositoryImpl(
    private val context: Context
): PassengerRepository {

    private val json = Json {
        ignoreUnknownKeys = true
    }
    override suspend fun fetchPassengers(): NetworkResult<PassengerList> {
        try{
            val jsonString = context.assets.open("passenger_list.json")
                .bufferedReader()
                .use { it.readText() }

            val passengers = json.decodeFromString<PassengerList>(jsonString)
            return NetworkResult.Success(passengers)

        }catch (e: Exception){
            return NetworkResult.Error(e.message.toString())
        }

    }
}