package com.apps.imageAI.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apps.imageAI.data.model.ChatMessage
import com.apps.imageAI.data.model.RecentChat

@Database(
    entities = [RecentChat::class,ChatMessage::class],
    version = 1,
    exportSchema = false
)
abstract class imageAIDatabase : RoomDatabase() {
    abstract fun ImageAIDao(): imageAIDao
}
