package com.apps.aivision.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "messages")
data class MessageModel(
    @PrimaryKey(autoGenerate = false)
    var id: String = Date().time.toString(),
    var conversationId: String = "",
    var question: String = "",
    var answer: String = "",
    var type: String = "",
    var url: String = "",
    var status:Int =0,
    var createdAt: String = Calendar.getInstance().time.toString(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageModel

        if (id != other.id) return false
        if (conversationId != other.conversationId) return false
        if (question != other.question) return false
        if (answer != other.answer) return false
        if (type != other.type) return false
        if (url != other.url) return false
        if (status != other.status) return false
        if (createdAt != other.createdAt) return false


        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + conversationId.hashCode()
        result = 31 * result + question.hashCode()
        result = 31 * result + answer.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }

    override fun toString(): String {
        return "MessageModel(id='$id', conversationId='$conversationId', question='$question', answer='$answer', type='$type', url='$url', status=$status, createdAt='$createdAt')"
    }


}