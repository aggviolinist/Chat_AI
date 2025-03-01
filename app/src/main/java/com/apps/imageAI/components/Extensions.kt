package com.apps.imageAI.components

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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


enum class ButtonState { Pressed, Idle }


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Converts a date string to a formatted date string.
 * Expected input format: "EEE MMM dd HH:mm:ss zzz yyyy" (e.g., "Tue Feb 25 14:30:00 GMT 2025").
 * Output format: "dd MMM yyyy - HH:mm" (e.g., "25 Feb 2025 - 14:30").
 *
 * @return The formatted date string, or throws an IllegalArgumentException if parsing fails.
 * @throws IllegalArgumentException if the input string cannot be parsed.
 */
fun String.toFormattedDate(): String {
    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).apply {
        isLenient = false // Enforce strict parsing
    }
    val outputFormat = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())

    return try {
        val date = inputFormat.parse(this) ?: throw IllegalArgumentException("Invalid date string: $this")
        outputFormat.format(date)
    } catch (e: ParseException) {
        throw IllegalArgumentException("Failed to parse date string: $this", e)
    }
}

/**
 * Creates a bouncing click effect animation for a composable.
 * When pressed, the element scales down to 90% of its original size.
 *
 * @param onClick Function to execute when the element is clicked
 * @return Modifier with the bouncing click animation applied
 */
fun Modifier.bounceClick(onClick: () -> Unit = {}) = composed {
    // Track the current state of the button (Idle or Pressed)
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }

    // Animate scale based on button state
    val scale by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.Pressed) 0.90f else 1f,
        label = "bounceScale"
    )

    this
        // Apply scaling effect
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        // Handle click events
        .click {
            onClick()
        }
        // Handle touch input to update button state
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

/**
 * Creates a clickable modifier without visual indication (ripple effect).
 *
 * @param onClick Function to execute when the element is clicked
 * @return Modifier with click behavior but no visual indication
 */
fun Modifier.click(onClick: () -> Unit = {}) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}

/**
 * Extracts the filename from a Uri path, with an option to rename it.
 *
 * @param fileName Optional new filename (without extension)
 * @return The original or renamed filename with original extension
 */
fun Uri.getPathFileName(fileName: String? = ""): String {
    val pathSegments = this.path?.split("/")
    val originalName = pathSegments?.lastOrNull() ?: ""

    return if (fileName.isNullOrEmpty()) {
        originalName
    } else {
        val extension = originalName.split(".").getOrNull(1) ?: ""
        "$fileName.$extension"
    }
}

/**
 * Gets the file extension from a string.
 *
 * @return The file extension (characters after the last period)
 */
fun String.getExtension(): String {
    return substringAfterLast(".")
}

/**
 * Creates a temporary JPEG image file with a timestamp-based name.
 *
 * @return A newly created temporary File object
 */
fun Context.createImageFile(): File {
    // Create an image file name with current timestamp
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_${timeStamp}_"

    // Create the temporary file in the external cache directory
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg",        /* suffix */
        externalCacheDir /* directory */
    )
}

/**
 * Retrieves the actual file path from a content Uri.
 *
 * @param uri The Uri to get the path from
 * @return The file path as a String, or null if not found
 */
fun Context.getUriPath(uri: Uri): String? {
    var path: String? = null
    var cursor: Cursor? = null

    try {
        cursor = contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.DATA),
            null,
            null,
            null
        )

        cursor?.let {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(MediaStore.Images.Media.DATA)
                if (index >= 0) {
                    path = it.getString(index)
                }
            }
        }
    } finally {
        cursor?.close()
    }

    return path
}

/**
 * Decodes a bitmap from a file with efficient memory usage by sampling.
 *
 * @param reqWidth Requested width for the bitmap
 * @param reqHeight Requested height for the bitmap
 * @return A sampled bitmap that conserves memory
 */
fun File.decodeSampledBitmap(reqWidth: Int, reqHeight: Int): Bitmap {
    val path = this.absolutePath

    return BitmapFactory.Options().run {
        // First decode with inJustDecodeBounds=true to check dimensions
        inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, this)

        // Calculate appropriate inSampleSize
        inSampleSize = calculateInSampleSize(reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false
        BitmapFactory.decodeFile(path, this)
    }
}

/**
 * Calculates the optimal sample size for loading a bitmap efficiently.
 *
 * @param reqWidth Requested width for the bitmap
 * @param reqHeight Requested height for the bitmap
 * @return The power-of-two sample size that best matches requested dimensions
 */
fun BitmapFactory.Options.calculateInSampleSize(reqWidth: Int, reqHeight: Int): Int {
    val (height, width) = outHeight to outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

/**
 * Converts a Bitmap to a Base64 encoded string.
 *
 * @return Base64 string representation of the bitmap, or null if conversion fails
 */
fun Bitmap.toBase64(): String? {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val byteArray = baos.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

/**
 * Gets the actual filename from a content Uri using ContentResolver.
 *
 * @param contentResolver ContentResolver to query the Uri
 * @return The display name of the file
 */
fun Uri.getFileName(contentResolver: ContentResolver): String {
    var fileName = getPathFileName("")

    contentResolver.query(this, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            fileName = cursor.getString(nameIndex)
        }
    }

    return fileName
}