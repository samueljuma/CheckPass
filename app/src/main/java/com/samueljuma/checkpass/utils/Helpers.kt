package com.samueljuma.checkpass.utils

import android.os.Build
import android.util.Log

private val knownPDAModels = listOf("PDA", "A920", "A910", "S920") // etc.

fun isPDADevice(): Boolean {
    val model = Build.MODEL.uppercase()
    val manufacturer = Build.MANUFACTURER.uppercase()

    Log.d("PDAModel", model)
    Log.d("PDAManufacturer", manufacturer)
    Log.d("PDADevice", Build.DEVICE.toString())

    return manufacturer.contains("PAX") ||
            knownPDAModels.any { model.contains(it) }

}