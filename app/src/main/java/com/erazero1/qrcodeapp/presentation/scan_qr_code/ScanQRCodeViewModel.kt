package com.erazero1.qrcodeapp.presentation.scan_qr_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erazero1.qrcodeapp.domain.usecase.ScanQRCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanQRCodeViewModel @Inject constructor(
    private val scanQRCodeUseCase: ScanQRCodeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScanQRCodeState())
    val uiState = _uiState.asStateFlow()

    fun startScanning() {
        viewModelScope.launch {
            scanQRCodeUseCase().collect{ details ->
                if (!details.isNullOrBlank()) {
                    _uiState.update { state ->
                        state.copy(
                            details = details
                        )
                    }
                }
            }
        }
    }

}