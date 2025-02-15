package com.apps.aivision.data.model

enum class GPTModel(val model: String, val maxTokens: Int) {
    gpt4("gpt-4", 8000),
    gpt35Turbo("gpt-4o-mini", 4000),
    gpt4Turbo("gpt-4o", 16000),
    gpt4Vision("gpt-4o", 8000)
}