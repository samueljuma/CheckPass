package com.samueljuma.checkpass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.samueljuma.checkpass.ui.home.HomeScreen
import com.samueljuma.checkpass.ui.theme.CheckPassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true // Set status bar color to white

        setContent {
            CheckPassTheme {
                HomeScreen()
            }
        }
    }
}