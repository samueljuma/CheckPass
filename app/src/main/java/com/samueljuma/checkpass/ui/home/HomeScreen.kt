package com.samueljuma.checkpass.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samueljuma.checkpass.R

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
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
                Image(
                    painter = painterResource(id = R.drawable.check_pass),
                    contentDescription = "Logo"
                )
            }
        }
    )
}