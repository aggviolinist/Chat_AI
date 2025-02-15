package com.apps.aivision.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apps.aivision.data.model.ChatMessage
import com.apps.aivision.data.model.RecentChat

@Database(
    entities = [RecentChat::class,ChatMessage::class],
    version = 1,
    exportSchema = false
)
abstract class AIVisionDatabase : RoomDatabase() {
    abstract fun aiVisionDao(): AIVisionDao
}