package com.samueljuma.checkpass.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.samueljuma.checkpass.R
import com.samueljuma.checkpass.presentation.core.CameraPermissionHandler
import com.samueljuma.checkpass.presentation.navigation.AppScreens
import com.samueljuma.checkpass.utils.CollectOneTimeEvent
import com.samueljuma.checkpass.utils.isPDADevice

@Composable
fun HomeScreen(
    viewModel: ScannerViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    var cameraClicked by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectOneTimeEvent(viewModel.event) {
        when(it){
            is ScannerEvent.NavigateToCameraScanner -> {
                navController.navigate(AppScreens.CameraScannerScreen.route)
            }
            else -> {}
        }
    }
    Scaffold(
        topBar = {},
        floatingActionButton = {
            if(!isPDADevice()){
                FloatingActionButton(
                    onClick = { cameraClicked = true },
                    modifier = Modifier.padding(16.dp),
                    containerColor = colorResource(R.color.green)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.camera_ic),
                        contentDescription = "Camera Icon",
                        tint = Color.White
                    )
                }
            }

        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                    CameraPermissionHandler(
                        onGranted = {
                            if(cameraClicked){
                                viewModel.triggerNavigationToCameraScanner()
                            }
                        },
                        cameraIconClicked = cameraClicked,
                        onCameraIconClicked = { cameraClicked = false }
                    )

                Image(
                    painter = painterResource(id = R.drawable.check_pass),
                    contentDescription = "Logo"
                )

                state.scannedQrCode?.let {
                    Text(
                        text = "Scanned Code $it",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                if(isPDADevice()){
                    Spacer(modifier = Modifier.height(32.dp))
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Info Icon",
                    )
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Use Scan Button on your device to scan QR Codes",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }
        }
    )
}