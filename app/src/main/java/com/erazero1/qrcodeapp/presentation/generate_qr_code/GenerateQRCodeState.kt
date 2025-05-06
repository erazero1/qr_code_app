package com.erazero1.qrcodeapp.presentation.generate_qr_code

import android.graphics.Bitmap
import android.net.Uri

data class GenerateQRCodeState (
    val qrBitmap: Bitmap? = null,
    val image: Uri? = null
)