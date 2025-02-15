package com.apps.aivision.data.model

import com.google.gson.annotations.SerializedName

data class ModerationRequest(@SerializedName("input")val input:String)

data class ModerationResponse(@SerializedName("id")val id :String,@SerializedName("model")val model :String,@SerializedName("results")val results:List<ResponseModel>?)
data class ResponseModel(@SerializedName("flagged") val flagged:Boolean)