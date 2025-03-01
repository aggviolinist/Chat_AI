package com.apps.imageAI.data.repository

import com.apps.imageAI.data.model.RecentChat
import com.apps.imageAI.data.source.local.imageAIDao
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
    private val ImageAIDao: imageAIDao

) : RecentChatRepository {
    override suspend fun addChat(recentChat: RecentChat) = ImageAIDao.addChat(recentChat)

    override suspend fun getAllChats(): MutableList<RecentChat> = ImageAIDao.getAllChats()
    override suspend fun searchChats(query: String): MutableList<RecentChat> = ImageAIDao.searchChats(query)

    override suspend fun deleteChat(chatId: Long) = ImageAIDao.deleteChat(chatId)

    override suspend fun deleteAllChats() = ImageAIDao.deleteAllChats()

    override suspend fun updateChat(recentChat: RecentChat): Int = ImageAIDao.updateChat(recentChat.id, title = recentChat.title, content = recentChat.content)
}
