package com.apps.aivision.data.model

import com.apps.aivision.components.ConversationType

data class AiPromptsCategoryModel(
    var categoryTitle: String = "",
    var prompts: List<AiPromptModel>,
)


data class AiPromptModel(
    var image: Int,
    var title: String = "",
    var summery: String = "",
    var type: String = ConversationType.TEXT.name,
    val examplesList: List<String>
)
