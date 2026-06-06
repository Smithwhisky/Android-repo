package com.smithwhisky.foxysc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val scannerViewModel: ScannerViewModel = viewModel()
                ScannerScreen(scannerViewModel)
            }
        }
    }
}

@Composable
fun ScannerScreen(viewModel: ScannerViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = state.portalUrl,
            onValueChange = { viewModel.updatePortalUrl(it) },
            label = { Text("Portal URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.startScanning() },
            enabled = !state.isScanning
        ) {
            Text("بدء المسح")
        }

        if (state.isScanning) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Text("Hits: ${state.hitsCount} | Checked: ${state.checkedCount}")
    }
}
