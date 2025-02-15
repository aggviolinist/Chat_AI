package com.apps.aivision.components

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.ContextWrapper
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


enum class ButtonState { Pressed, Idle }


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun String.toFormattedDate(): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}


fun Modifier.bounceClick(onClick: () -> Unit = {}) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.90f else 1f,
        label = ""
    )

    this.graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .click {
            onClick()
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
}

fun Modifier.click(onClick: () -> Unit = {}) = composed {
    this
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                onClick()
            }
        )

}

fun Uri.getPathFileName(fileName: String? = ""): String {
    val arrayName = this.path?.split("/")
    val originalName = if (arrayName!=null) arrayName[arrayName.size-1] else ""
    return if (fileName.isNullOrEmpty()) {
        originalName
    } else {
        fileName + "." + originalName.split(".")[1]
    }

}

fun String.getExtension(): String {
    return substringAfterLast(".")
}


fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

fun Context.getUriPath(uri:Uri):String? {
    var path: String? = null /*from  w ww  . j av  a2s  .c o m*/
    var cursor: Cursor? = null

    try {
        cursor = getContentResolver()
            .query(uri, arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null)
        cursor?.let {
            cursor.moveToFirst()
            val index = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            if (index >=0)
                path = cursor.getString(index)
        }
    } finally {
        cursor?.close()
    }

    return path
}

fun File.decodeSampledBitmap(reqWidth:Int,reqHeight:Int): Bitmap
{
    val path = this.absolutePath
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, this)

        // Calculate inSampleSize
        inSampleSize = this.calculateInSampleSize( reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false

        BitmapFactory.decodeFile(path, this)
    }


}


fun BitmapFactory.Options.calculateInSampleSize(reqWidth:Int, reqHeight:Int):Int{

    val (height: Int, width: Int) = this.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

fun Bitmap.toBase64():String?{

    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}

fun Uri.getFileName(contentResolver: ContentResolver): String {
    var fileName = getPathFileName("")
    val cursor = contentResolver.query(this, null, null, null, null)
    cursor?.let {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        fileName = it.getString(nameIndex)
        it.close()
    }

return fileName
}
