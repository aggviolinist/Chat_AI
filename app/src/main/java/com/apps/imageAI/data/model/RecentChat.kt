package com.apps.imageAI.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "tbl_recent_chat")
data class RecentChat(
    @PrimaryKey(autoGenerate = true) val id: Long=0,
    val title: String = "",
    val type: String = "",
    val content:String="",
    @ColumnInfo(name = "created_at") val createdAt: String = Calendar.getInstance().time.toString())