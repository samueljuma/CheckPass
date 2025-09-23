package com.samueljuma.checkpass.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.samueljuma.checkpass.presentation.home.CameraScannerScreen
import com.samueljuma.checkpass.presentation.home.HomeScreen
import com.samueljuma.checkpass.presentation.navigation.AppNavigation
import com.samueljuma.checkpass.presentation.theme.CheckPassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true // Set status bar color to white

        setContent {
            CheckPassTheme {
                AppNavigation()
            }
        }
    }
}