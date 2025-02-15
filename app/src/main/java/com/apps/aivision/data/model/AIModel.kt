package com.apps.aivision.data.model

import com.google.gson.annotations.SerializedName

data class AIModel(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

data class AIMessageRequest(
    val model:String = "gpt-3.5-turbo",
    val messages: List<AIModel> = arrayListOf()
)

data class AIChoice(@SerializedName("index") val index:Any,@SerializedName("message") val message: AIModel){
    override fun toString(): String {
        return "AIChoice(index=$index, message=$message)"
    }
}

data class AIMessageResponse(
    @SerializedName("choices") val choices: List<AIChoice>? = arrayListOf()
)

data class VisionRequest(@SerializedName("model") val  model: String,@SerializedName("messages") val messages: List<VisionMessage>,@SerializedName("max_tokens") val maxToken:Int =35)
data class VisionMessage(@SerializedName("role") val role:String,@SerializedName("content") val content:List<VisionContent>)
data class VisionContent(@SerializedName("type") val type:String,@SerializedName("text") val text:String?=null,
                         @SerializedName("image_url") val imageUrl:VisionUrlModel?=null)
data class VisionUrlModel(@SerializedName("url") val url:String)

data class AsticaVisionRequest(@SerializedName("tkn") val key:String,@SerializedName("modelVersion") val modelVersion:String,
                               @SerializedName("input") val input:String,@SerializedName("visionParams") val visionParams:String,@SerializedName("gpt_prompt") val prompt:String?=null,@SerializedName("gpt_length") val length:String?=null)
data class AsticaVisionResponse(@SerializedName("status") val status:String,@SerializedName("caption") val caption:AsticaCaption?=null,@SerializedName("caption_GPTS")val captionGPTS:String?=null,@SerializedName("tags") val tags:List<AsticaObject>?=null,@SerializedName("objects")val objects:List<AsticaObject>?=null,@SerializedName("readResult")val asticaOCR: AsticaOCR?=null)
data class AsticaCaption(@SerializedName("text") val text:String)
data class AsticaObject(@SerializedName("name") val name:String)
data class AsticaOCR(@SerializedName("content") val content:String)