package com.erazero1.qrcodeapp.domain.usecase

import com.erazero1.qrcodeapp.domain.repository.GenerateQRCodeRepository
import javax.inject.Inject

class GenerateQRCodeUseCase @Inject constructor(
    private val repository: GenerateQRCodeRepository
) {
    operator fun invoke(text: String) = repository.generateQRCode(text = text)
}