package com.apps.aivision.data.model

sealed class ImageGenerationStatus {
    data class Generated(val path:String):ImageGenerationStatus()
    data object Downloading:ImageGenerationStatus()
    data object Completed:ImageGenerationStatus()
    data class GenerationError(val error:String):ImageGenerationStatus()
    data class DownloadError(val url:String):ImageGenerationStatus()
}