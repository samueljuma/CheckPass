package com.samueljuma.checkpass.presentation.core

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionHandler(
    onGranted: () -> Unit,
    cameraIconClicked: Boolean,
    resetCameraIconClick : () -> Unit
){
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var showRationaleDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (cameraPermissionState.status.isGranted) onGranted()

    LaunchedEffect(cameraIconClicked) {
        if (cameraIconClicked) {
            when {
                cameraPermissionState.status.isGranted -> Unit
                cameraPermissionState.status.shouldShowRationale -> showRationaleDialog = true
                else -> {
                    cameraPermissionState.launchPermissionRequest()

                    if (!cameraPermissionState.status.isGranted &&
                        !cameraPermissionState.status.shouldShowRationale
                    ) {
                        showSettingsDialog = true
                    }
                }
            }
            resetCameraIconClick()
        }
    }

    if (showRationaleDialog) {
        CustomAlertDialog(
            onCancel = { showRationaleDialog = false },
            onConfirm = {
                showRationaleDialog = false
                cameraPermissionState.launchPermissionRequest()
            },
            title = "Camera Permission Needed",
            message = "This app requires access to your camera to scan QR codes.",
            confirmButtonText = "Grant",
            dismissButtonText = "Cancel"
        )
    }

    if (showSettingsDialog && !cameraPermissionState.status.isGranted) {
        CustomAlertDialog(
            onCancel = { showSettingsDialog = false },
            onConfirm = {
                showSettingsDialog = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    .apply {
                        data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            },
            title = "CameraPermission Required",
            message = "This app requires access to your camera to scan QR codes. Please enable it in Settings.",
            confirmButtonText = "Open Settings",
            dismissButtonText = "Cancel"
        )
    }

}