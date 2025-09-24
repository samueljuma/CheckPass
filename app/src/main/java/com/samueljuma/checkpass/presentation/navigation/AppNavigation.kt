package com.samueljuma.checkpass.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samueljuma.checkpass.presentation.home.CameraScannerScreen
import com.samueljuma.checkpass.presentation.home.HomeScreen
import com.samueljuma.checkpass.presentation.home.ScannerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val viewModel: ScannerViewModel = koinViewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route
    ) {
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(viewModel, navController)
        }
        composable(route = AppScreens.CameraScannerScreen.route) {
            CameraScannerScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}