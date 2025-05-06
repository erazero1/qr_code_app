package com.erazero1.qrcodeapp.domain.usecase

import com.erazero1.qrcodeapp.domain.repository.ScanQRCodeRepository
import javax.inject.Inject

class ScanQRCodeUseCase @Inject constructor (
    private val repository: ScanQRCodeRepository
) {
    operator fun invoke() = repository.startScanning()
}