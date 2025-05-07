package com.erazero1.qrcodeapp.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.erazero1.qrcodeapp.domain.repository.GenerateQRCodeRepository
import qrcode.QRCode
import qrcode.color.Colors
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class GenerateQRCodeRepositoryImpl @Inject constructor(
    private val context: Context
) : GenerateQRCodeRepository {
    override fun generateQRCode(text: String): Bitmap {
        return QRCode.ofSquares()
            .withColor(Colors.BLACK)
            .withBackgroundColor(Colors.WHITE)
            .withSize(50)
            .build(text)
            .renderToBytes()
            .toBitmap()
    }

    override fun saveBitmapAsJpegToMediaStore(bitmap: Bitmap): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageInQ(context = context, imageBitmap = bitmap)
        } else {
            saveImageInLegacy(context = context, imageBitmap = bitmap)
        }
    }

    private fun saveImageInQ(
        context: Context,
        imageBitmap: Bitmap
    ): Uri {
        val fileName = "IMG_${System.currentTimeMillis()}.jpeg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val contentResolver = context.contentResolver
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        imageUri?.let { uri ->
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            contentResolver.update(uri, contentValues, null, null)
        }

        return imageUri ?: Uri.EMPTY
    }

    private fun saveImageInLegacy(
        context: Context,
        imageBitmap: Bitmap
    ): Uri {
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val fileName = "IMG_${System.currentTimeMillis()}.jpeg"
        val imageFile = File(imagesDir, fileName)

        return try {
            FileOutputStream(imageFile).use { fos ->
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Uri.EMPTY
            } else {
                FileProvider.getUriForFile(context, "com.erazero1.qrcodeapp.fileprovider", imageFile)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Uri.EMPTY
        }
    }
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}
