package com.erazero1.qrcodeapp.di

import com.erazero1.qrcodeapp.data.repository.GenerateQRCodeRepositoryImpl
import com.erazero1.qrcodeapp.data.repository.ScanQRCodeRepositoryImpl
import com.erazero1.qrcodeapp.domain.repository.GenerateQRCodeRepository
import com.erazero1.qrcodeapp.domain.repository.ScanQRCodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    @ViewModelScoped
    abstract fun bindScanQRCodeRepository(
        scanQRCodeRepositoryImpl: ScanQRCodeRepositoryImpl
    ): ScanQRCodeRepository

    @Binds
    @ViewModelScoped
    abstract fun bindGenerateQRCodeRepository(
        generateQRCodeRepositoryImpl: GenerateQRCodeRepositoryImpl
    ): GenerateQRCodeRepository



}