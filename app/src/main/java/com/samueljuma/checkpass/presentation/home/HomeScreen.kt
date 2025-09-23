package com.samueljuma.checkpass.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import com.samueljuma.checkpass.R
import com.samueljuma.checkpass.presentation.core.CameraPermissionHandler

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    var cameraClicked by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {},
        floatingActionButton = {
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
                                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                            }

                        },
                        cameraIconClicked = cameraClicked,
                        onCameraIconClicked = { cameraClicked = false }
                    )

                Image(
                    painter = painterResource(id = R.drawable.check_pass),
                    contentDescription = "Logo"
                )
            }
        }
    )
}