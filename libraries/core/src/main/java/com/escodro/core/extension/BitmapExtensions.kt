package com.escodro.core.extension

import android.graphics.Bitmap
import android.graphics.Matrix

/*import androidx.exifinterface.media.ExifInterface

fun Bitmap.scale(maxDimension: Int): Bitmap {
    val size = Size(width, height) scaleWith maxDimension
    return Bitmap.createScaledBitmap(this, size.width, size.height, true)
}

fun Bitmap.normalizeRotation(uri: Uri, contentResolver: ContentResolver): Bitmap? {
    return contentResolver.openInputStream(uri)?.use { input ->
        val ei = ExifInterface(input)

        when (ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotate(90f)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotate(180f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotate(270f)
            }
            else -> this
        }
    }
}*/

fun Bitmap.rotate(angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
