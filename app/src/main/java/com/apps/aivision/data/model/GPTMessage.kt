package com.apps.aivision.data.model

import com.google.gson.annotations.SerializedName

data class GPTMessage(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("role")
    val role: String = GPTRole.USER.value,
)

