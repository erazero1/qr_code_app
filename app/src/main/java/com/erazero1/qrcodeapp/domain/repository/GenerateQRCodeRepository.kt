package com.erazero1.qrcodeapp.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface GenerateQRCodeRepository {
    fun generateQRCode(text: String): Bitmap
    fun saveBitmapAsJpegToMediaStore(bitmap: Bitmap): Uri
}