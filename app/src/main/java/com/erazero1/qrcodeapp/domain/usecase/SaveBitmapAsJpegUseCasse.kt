package com.erazero1.qrcodeapp.domain.usecase

import android.graphics.Bitmap
import com.erazero1.qrcodeapp.domain.repository.GenerateQRCodeRepository
import javax.inject.Inject

class SaveBitmapAsJpegUseCasse @Inject constructor(
    private val repository: GenerateQRCodeRepository
) {
    operator fun invoke(bitmap: Bitmap) = repository.saveBitmapAsJpegToMediaStore(bitmap = bitmap)
}