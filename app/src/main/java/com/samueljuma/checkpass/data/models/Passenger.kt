package com.samueljuma.checkpass.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Passenger(
    val ticket_number: String,
    val name: String,
    val phone_number: String,
    val id_number: String,
    val seat_number: String,
    val onboarded: Boolean,
)
