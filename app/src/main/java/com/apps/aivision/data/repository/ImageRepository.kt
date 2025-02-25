package com.apps.aivision.data.repository

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.apps.aivision.R
import com.apps.aivision.components.ApiKeyHelpers
import com.apps.aivision.components.AppLogger
import com.apps.aivision.data.model.ImageGenerationStatus
import com.apps.aivision.data.model.ImageRequest
import com.apps.aivision.data.model.StabilityImageRequest
import com.apps.aivision.data.source.remote.AIVisionService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONArray
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.util.Locale
import javax.inject.Inject


interface ImageRepository {
     fun generateImageWithDalle(request: ImageRequest): Flow<ImageGenerationStatus>

     fun generateImageWithStability(request: StabilityImageRequest): Flow<ImageGenerationStatus>

     fun generateTextFromImage(request:ImageRequest): Flow<String>

}

private const val TAG ="ImageRepositoryImpl"
class ImageRepositoryImpl @Inject constructor(@ApplicationContext val application: Context, private val aiVisionService: AIVisionService, private val apiKeyHelpers: ApiKeyHelpers,private val preferenceRepository: PreferenceRepository) :ImageRepository {

     override  fun generateImageWithDalle(request: ImageRequest): Flow<ImageGenerationStatus> = callbackFlow {

          var downloadId:Int?=null
          runCatching {
               val result =    aiVisionService.generateImages(request,"Bearer ${apiKeyHelpers.getApiKey()}")
               if (result.data!=null)
               {
                    if (result.data.isNotEmpty())
                    {
                         val image = result.data[0]
                         val randomName = "${System.currentTimeMillis()}_IMG"
                         val file = File(application.filesDir, "${randomName}.png")


                         trySend(ImageGenerationStatus.Generated(file.absolutePath))
                         trySend(ImageGenerationStatus.Downloading)
                         kotlin.runCatching {
                              val decodedBytes = Base64.decode(image.base64, Base64.DEFAULT)
                              val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                              val outputStream: OutputStream = FileOutputStream(file)
                              decodedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                              outputStream.flush()
                              outputStream.close()
                              trySend(ImageGenerationStatus.Completed)
                         }.onFailure {
                              trySend(ImageGenerationStatus.DownloadError("base64"))
                         }
                         close()


                    }else{
                         trySend(ImageGenerationStatus.GenerationError("Failure!:empty list"))
                         close()
                    }
               }else{
                    trySend(ImageGenerationStatus.GenerationError("Failure!"))
                    close()
               }

          }.onFailure {
               trySend(ImageGenerationStatus.GenerationError("${it.message}"))
               close(it)
          }


         /* awaitClose {
               downloadId?.let {
                    kDownloader.cancel(it)
               }
          }*/
     }.flowOn(Dispatchers.IO)

     override fun generateImageWithStability(request: StabilityImageRequest): Flow<ImageGenerationStatus> = callbackFlow {

          runCatching {

               request.prompts[0].text = getENText(request.prompts[0].text)
               val result =    aiVisionService.generateImagesWithStability("stable-diffusion-xl-1024-v1-0", body = request, authHeader = "Bearer ${apiKeyHelpers.getStabilityKey()}")
               if (result.artifacts!=null)
               {
                    if (result.artifacts.isNotEmpty())
                    {
                         val image = result.artifacts[0]
                         val randomName = "${System.currentTimeMillis()}_IMG"
                         val file = File(application.filesDir, "${randomName}.png")


                         trySend(ImageGenerationStatus.Generated(file.absolutePath))
                         trySend(ImageGenerationStatus.Downloading)
                         kotlin.runCatching {
                              val decodedBytes = Base64.decode(image.base64Img, Base64.DEFAULT)
                              val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                              val outputStream: OutputStream = FileOutputStream(file)
                              decodedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                              outputStream.flush()
                              outputStream.close()
                              trySend(ImageGenerationStatus.Completed)
                         }.onFailure {
                              trySend(ImageGenerationStatus.DownloadError("base64"))
                         }
                         close()

                    }else{
                         trySend(ImageGenerationStatus.GenerationError("Failure!:empty list"))
                         close()
                    }
               }else{
                    trySend(ImageGenerationStatus.GenerationError("Failure!"))
                    close()
               }

          }.onFailure {
               it.printStackTrace()
               trySend(ImageGenerationStatus.GenerationError("${it.message}"))
               close(it)
          }


          /*  awaitClose {
                downloadId?.let {
                    kDownloader.cancel(it)
                }
            }*/
     }.flowOn(Dispatchers.IO)

     override fun generateTextFromImage(request: ImageRequest): Flow<String> {
          TODO("Not yet implemented")
     }

    private suspend fun getENText(text: String):String
    {
         if (preferenceRepository.getCurrentLanguageCode().contentEquals("en"))
              return text

         var requestText = text
         var isTemplate = false
         when(text){
              application.getString(R.string.image_inp_1)->
              {    requestText = getStringByLocal(R.string.image_inp_1,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_2)->
              {    requestText = getStringByLocal(R.string.image_inp_2,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_3)->
              {    requestText = getStringByLocal(R.string.image_inp_3,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_4)->
              {    requestText = getStringByLocal(R.string.image_inp_4,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_5)->
              {    requestText = getStringByLocal(R.string.image_inp_5,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_6)->
              {    requestText = getStringByLocal(R.string.image_inp_6,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_7)->
              {    requestText = getStringByLocal(R.string.image_inp_7,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_8)->
              {    requestText = getStringByLocal(R.string.image_inp_8,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_9)->
              {    requestText = getStringByLocal(R.string.image_inp_9,"en")
                   isTemplate = true
              }
              application.getString(R.string.image_inp_10)->
              {    requestText = getStringByLocal(R.string.image_inp_10,"en")
                   isTemplate = true
              }
              else -> isTemplate = false
         }

         if (isTemplate.not()){
              // api call
              runCatching {
                   val result = aiVisionService.translateText(preferenceRepository.getCurrentLanguageCode(),"en",requestText)
                   if (result.isSuccessful)
                   {
                        AppLogger.logE(TAG,"Response...............")
                        val stringBuilder = StringBuilder()
                        val inputStream = result.body()?.byteStream() ?: throw Exception()
                        val reader = BufferedReader(InputStreamReader(inputStream))
                        while (true) {
                             val line = reader.readLine() ?: break
                             stringBuilder.append(line)
                        }
                        inputStream.close()
                        val json = stringBuilder.toString()
                        if (json.isNotEmpty())
                        {

                             try {

                                  val jsonArray = JSONArray(json)
                                  var str = ""
                                  for (i in 0 until jsonArray.getJSONArray(0).length()) {
                                       str += jsonArray.getJSONArray(0).getJSONArray(i)
                                            .getString(0)
                                  }

                                  AppLogger.logE(TAG,"Response.....:${requestText}")
                                  if(str.isNotEmpty())
                                  {
                                       requestText = str
                                  }

                             }catch (ex:Exception){ex.printStackTrace()}
                        }

                      //  AppLogger.logE(TAG,"result: ${result.body()!!.string()}")
                   }

              }.onFailure {
                   it.printStackTrace()
              }
         }

         return requestText
    }

     private fun getStringByLocal(id: Int, locale: String): String {
          val configuration = Configuration(application.resources.configuration)
          configuration.setLocale(Locale(locale))
          return application.createConfigurationContext(configuration).resources.getString(id)
     }
}