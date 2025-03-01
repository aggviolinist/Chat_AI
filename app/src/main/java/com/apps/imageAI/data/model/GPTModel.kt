package com.apps.imageAI.data.model

enum class GPTModel(val modelName: String, val maxTokens: Int, val description: String) {
    GPT_4("gpt-4", 8000, "The most capable model, suitable for complex tasks."),
    GPT_3_5_TURBO("gpt-4o-mini", 4000, "A fast and cost-effective model for general use."),
    GPT_4_TURBO("gpt-4o", 16000, "The latest model with extended context length."),
    GPT_4_VISION("gpt-4o", 8000, "Optimized for image and text understanding.");

    companion object {
        fun fromModelName(modelName: String): GPTModel? {
            return entries.find { it.modelName == modelName }
        }
    }
}