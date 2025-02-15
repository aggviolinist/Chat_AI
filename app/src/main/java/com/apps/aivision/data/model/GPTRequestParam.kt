package com.apps.aivision.data.model
import com.google.gson.annotations.SerializedName

data class GPTRequestParam(
    @SerializedName("temperature")
    val temperature: Double = 0.9,
    @SerializedName("stream")
    var stream: Boolean = true,
    /* @SerializedName("max_tokens")
     val maxTokens: Int = 4000,*/
    @SerializedName("model")
    val model: String = GPTModel.gpt4.model,
    @SerializedName("messages")
    val messages: List<GPTMessage> = emptyList(),
)

