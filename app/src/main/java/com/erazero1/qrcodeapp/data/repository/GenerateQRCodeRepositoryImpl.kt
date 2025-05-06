package com.erazero1.qrcodeapp.data.repository

import android.content.ContentResolver
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
import java.io.OutputStream
import javax.inject.Inject

class GenerateQRCodeRepositoryImpl @Inject constructor(
    private val context: Context
) : GenerateQRCodeRepository {
    override fun generateQRCode(text: String): Bitmap {
        return QRCode.ofSquares()
            .withColor(Colors.BLACK)
            .withBackgroundColor(Colors.WHITE)
            .withSize(200)
            .build(text)
            .renderToBytes()
            .toBitmap()
    }

    override fun saveBitmapAsJpegToMediaStore(bitmap: Bitmap): Uri {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) return saveImageInQ(
            context = context,
            imageBitmap = bitmap
        )
        else saveImageInLegacy(context = context, imageBitmap = bitmap)
        return Uri.EMPTY
    }


    private fun saveImageInQ(
        context: Context,
        imageBitmap: Bitmap
    ): Uri {
        val fileName = "IMG_${System.currentTimeMillis()}.jpeg"
        var fos: OutputStream?
        var imageUri: Uri?

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
        val contentResolver = context.contentResolver

        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }

        fos?.use {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        contentResolver.update(imageUri!!, contentValues, null, null)

        return imageUri!!
    }

    private fun saveImageInLegacy(
        context: Context,
        imageBitmap: Bitmap
    ): Uri {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File(imagesDir, "IMG_${System.currentTimeMillis()}.jpeg")
        val fos = FileOutputStream(imageFile)
        fos.use { imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }

        return FileProvider.getUriForFile(context, "com.erazero1.qrcodeapp.fileprovider", imageFile)
    }
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}