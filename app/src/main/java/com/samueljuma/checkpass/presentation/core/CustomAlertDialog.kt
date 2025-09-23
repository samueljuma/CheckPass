package com.samueljuma.checkpass.presentation.core

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CustomAlertDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    message: String,
    confirmButtonText: String,
    dismissButtonText: String
){
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        ) },
        text = { Text(
            text = message,
            textAlign = TextAlign.Center
        ) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(dismissButtonText)
            }
        }
    )
}