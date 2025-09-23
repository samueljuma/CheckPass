package com.samueljuma.checkpass.utils

import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

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

@Composable
fun <T> CollectOneTimeEvent(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: suspend (T) -> Unit
){
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(
        key1 = key1,
        key2 = key2,
        key3 = lifecycleOwner
    ) {
        lifecycleOwner.repeatOnLifecycle( Lifecycle.State.STARTED){
            withContext( Dispatchers.Main.immediate){
                flow.collectLatest(onEvent)
            }
        }
    }
}