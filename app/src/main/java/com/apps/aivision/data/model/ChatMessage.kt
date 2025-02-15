package com.apps.aivision.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "tbl_messages")
data class ChatMessage(@PrimaryKey(autoGenerate = true) val id: Long=0,
                       @ColumnInfo(name = "chat_id") val recentChatId: Long,
                       val role: String = "",
                       val content: String = "",
                       val type: String = "",
                       val url: String = "",
                       @ColumnInfo(name = "download_status") val status:Int =0,
                       @ColumnInfo(name = "created_at") val createdAt: String = Calendar.getInstance().time.toString())
