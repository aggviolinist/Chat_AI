package com.apps.aivision.data.model

import com.google.gson.annotations.SerializedName

data class ImageModel( @SerializedName("url") val url:String,@SerializedName("b64_json") val base64:String)

data class ImageRequest(@SerializedName("prompt") val prompt:String,@SerializedName("n") val n:Int=1,@SerializedName("size") val size:String ="256x256",@SerializedName("response_format")val format:String="b64_json",@SerializedName("model")val model:String="dall-e-3")//b64_json,url
data class ImageGenerationResponse(@SerializedName("data") val data:List<ImageModel>?)


data class StabilityImageModel (@SerializedName("base64")val base64Img:String,@SerializedName("finishReason")val finishReason:String,@SerializedName("seed") val seed:Long)
data class PromptModel (@SerializedName("text") var text:String,@SerializedName("weight") val weight:Int)
data class StabilityImageRequest(@SerializedName("text_prompts") val prompts:List<PromptModel>, @SerializedName("samples") val sample:Int=1, @SerializedName("steps") val steps:Int =50
                                 , @SerializedName("width") val width:Int=1024, @SerializedName("height") val height:Int=1024, @SerializedName("seed") val seed:Int=0, @SerializedName("cfg_scale")val  cfgScale:Int=10, @SerializedName("style_preset") val stylePreset:String?=null)
data class StabilityImageGenerationResponse(@SerializedName("artifacts") val artifacts:List<StabilityImageModel>?)

data class ImageInputRequest(val prompt: String="")


//artifacts