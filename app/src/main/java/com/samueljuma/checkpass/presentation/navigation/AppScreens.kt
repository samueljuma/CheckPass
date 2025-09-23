package com.samueljuma.checkpass.presentation.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen: AppScreens("home_screen")
    object CameraScannerScreen: AppScreens("camera_scanner_screen")
}