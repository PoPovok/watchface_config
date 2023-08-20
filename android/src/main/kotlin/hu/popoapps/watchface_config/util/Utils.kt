package hu.popoapps.watchface_config.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

fun Drawable.toBase64(): String{
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}