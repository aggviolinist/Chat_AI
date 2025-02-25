package com.apps.aivision.components

import android.content.ContentValues
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileInputStream
import java.util.Locale

class Utils {

    companion object{

        suspend fun copyFileIntoGallery(
            sourceUri: Uri,
            fileName: String? = null,
            context:Context
        ) = suspendCancellableCoroutine<File> {
            kotlin.runCatching {

                val name = sourceUri.getPathFileName(fileName)
                val nPath = "${System.currentTimeMillis()}.${name.getExtension()}"
                val inputStream =  FileInputStream(sourceUri.toString())



                val relativeLocation = "${Environment.DIRECTORY_PICTURES}/"
                val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, nPath)
                put(MediaStore.Images.Media.MIME_TYPE, "image/*")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    relativeLocation
                )
            }
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                uri?.let { destUri ->
                    resolver.openOutputStream(destUri)?.use { outputStream ->
                        inputStream.use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                } ?: run {
                }
            }.onFailure {
                it.printStackTrace()
            }

        }

        suspend fun saveImageToGallery(bitmap:Bitmap,context: Context){
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"IMG_${System.currentTimeMillis()}.jpg")
            if (file.exists()){
                file.delete()
            }
            val destinationUri = Uri.fromFile(file)
            val outputStream = context.contentResolver.openOutputStream(destinationUri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
            outputStream.close()
            outputStream.flush()
            copyFileIntoGallery(Uri.parse(file.absolutePath),context =context)
        }
        fun saveBitmapToExternalDir(bitmap: Bitmap,context: Context):String{

            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"IMG_${System.currentTimeMillis()}.jpg")
            if (file.exists()){
                file.delete()
            }
            val path = file.absolutePath
            val destinationUri = Uri.fromFile(file)
            val outputStream = context.contentResolver.openOutputStream(destinationUri)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
            outputStream.close()
            outputStream.flush()
            return path
        }
        fun isConnectedToNetwork(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }


        fun resizeBitmap(image: Bitmap, maxHeight: Int, maxWidth: Int): Bitmap {



            if (maxHeight > 0 && maxWidth > 0) {

                val sourceWidth: Int = image.width
                val sourceHeight: Int = image.height

                var targetWidth = maxWidth
                var targetHeight = maxHeight

                val sourceRatio = sourceWidth.toFloat() / sourceHeight.toFloat()
                val targetRatio = maxWidth.toFloat() / maxHeight.toFloat()

                if (targetRatio > sourceRatio) {
                    targetWidth = (maxHeight.toFloat() * sourceRatio).toInt()
                } else {
                    targetHeight = (maxWidth.toFloat() / sourceRatio).toInt()
                }

                return Bitmap.createScaledBitmap(
                    image, targetWidth, targetHeight, true
                )

            } else {
                throw RuntimeException()
            }
        }

        fun changeLanguage(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)

        }

    }
}