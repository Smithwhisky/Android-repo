package com.smithwhisky.foxysc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UiState(
    val portalUrl: String = "",
    val isScanning: Boolean = false,
    val progress: Float = 0f,
    val hitsCount: Int = 0,
    val checkedCount: Int = 0,
    val results: List<String> = emptyList()
)

class ScannerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private var job: Job? = null

    fun updatePortalUrl(url: String) {
        _uiState.value = _uiState.value.copy(portalUrl = url)
    }

    fun startScanning() {
        if (_uiState.value.isScanning) return

        job = viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = _uiState.value.copy(isScanning = true)
            _uiState.value = _uiState.value.copy(isScanning = false)
        }
    }

    fun stopScanning() {
        job?.cancel()
        _uiState.value = _uiState.value.copy(isScanning = false)
    }
}
