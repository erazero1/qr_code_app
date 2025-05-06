package com.erazero1.qrcodeapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface ScanQRCodeRepository {
    fun startScanning() :Flow<String>
}