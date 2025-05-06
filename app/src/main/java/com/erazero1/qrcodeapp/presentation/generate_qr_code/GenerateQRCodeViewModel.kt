package com.erazero1.qrcodeapp.presentation.generate_qr_code

import androidx.lifecycle.ViewModel
import com.erazero1.qrcodeapp.domain.usecase.GenerateQRCodeUseCase
import com.erazero1.qrcodeapp.domain.usecase.SaveBitmapAsJpegUseCasse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GenerateQRCodeViewModel @Inject constructor(
    private val generateQRCodeUseCase: GenerateQRCodeUseCase,
    private val saveBitmapAsJpegUseCase: SaveBitmapAsJpegUseCasse
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenerateQRCodeState())
    val uiState = _uiState.asStateFlow()
    fun generateQRCode(text: String) {
        val qrBitmap = generateQRCodeUseCase(text = text)
        _uiState.update {
            it.copy(
                qrBitmap = qrBitmap
            )
        }
    }

    fun updateImage() {
        val updatedImage = saveBitmapAsJpegUseCase(
            bitmap = uiState.value.qrBitmap!!
        )
        _uiState.update {
            it.copy(
                image = updatedImage
            )
        }
    }
}