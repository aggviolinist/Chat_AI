package com.apps.aivision.data.source.remote

import com.apps.aivision.data.model.AIMessageResponse
import com.apps.aivision.data.model.AsticaVisionRequest
import com.apps.aivision.data.model.AsticaVisionResponse
import com.apps.aivision.data.model.GPTRequestParam
import com.apps.aivision.data.model.ImageGenerationResponse
import com.apps.aivision.data.model.ImageRequest
import com.apps.aivision.data.model.ModerationRequest
import com.apps.aivision.data.model.ModerationResponse
import com.apps.aivision.data.model.StabilityImageGenerationResponse
import com.apps.aivision.data.model.StabilityImageRequest
import com.apps.aivision.data.model.VisionRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

interface AIVisionService {


    @POST("chat/completions")
    @Streaming
    fun textCompletionsWithStream(@Body body: GPTRequestParam, @Header("Authorization") authHeader: String): Call<ResponseBody>

    @POST("chat/completions")
    suspend fun askAIAssistant(@Body body: GPTRequestParam
                               , @Header("Authorization") authHeader: String): AIMessageResponse

    @POST("moderations")
    fun inputModerations(@Body request: ModerationRequest, @Header("Authorization") authHeader: String): Call<ModerationResponse>

    @POST("images/generations")
    suspend fun generateImages(@Body body: ImageRequest
                               , @Header("Authorization") authHeader: String): ImageGenerationResponse

    @Headers("Accept: application/json")
    @POST("https://api.stability.ai/v1/generation/{engine_id}/text-to-image")
    suspend fun generateImagesWithStability(@Path("engine_id") engineId: String, @Body body: StabilityImageRequest, @Header("Authorization") authHeader: String): StabilityImageGenerationResponse

    @POST("chat/completions")
    suspend fun askAIVision(@Body body: VisionRequest, @Header("Authorization") authHeader: String): AIMessageResponse

    @Headers("Accept: application/json")
    @POST("https://vision.astica.ai/describe")
    suspend fun askAsticaVisionAI( @Body body: AsticaVisionRequest): AsticaVisionResponse

    @GET("https://translate.googleapis.com/translate_a/single?client=gtx&ie=UTF-8&oe=UTF-8&dt=t")
    @Streaming
    suspend fun translateText( @Query("sl") source: String,@Query("tl") target: String,@Query("q") query: String): Response<ResponseBody>
}