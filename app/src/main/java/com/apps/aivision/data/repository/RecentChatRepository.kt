package com.apps.aivision.data.repository

import com.apps.aivision.data.model.RecentChat
import com.apps.aivision.data.source.local.AIVisionDao
import javax.inject.Inject

interface RecentChatRepository {
    suspend fun addChat(recentChat: RecentChat):Long
    suspend fun getAllChats(): MutableList<RecentChat>
    suspend fun searchChats(query:String): MutableList<RecentChat>
    suspend fun deleteChat(chatId: Long)
    suspend fun deleteAllChats()
    suspend fun updateChat(recentChat: RecentChat):Int
}

class RecentChatRepositoryImpl @Inject constructor(
    private val aiVisionDao: AIVisionDao

) : RecentChatRepository {
    override suspend fun addChat(recentChat: RecentChat) = aiVisionDao.addChat(recentChat)

    override suspend fun getAllChats(): MutableList<RecentChat> = aiVisionDao.getAllChats()
    override suspend fun searchChats(query: String): MutableList<RecentChat> = aiVisionDao.searchChats(query)

    override suspend fun deleteChat(chatId: Long) = aiVisionDao.deleteChat(chatId)

    override suspend fun deleteAllChats() = aiVisionDao.deleteAllChats()

    override suspend fun updateChat(recentChat: RecentChat): Int = aiVisionDao.updateChat(recentChat.id, title = recentChat.title, content = recentChat.content)
}
