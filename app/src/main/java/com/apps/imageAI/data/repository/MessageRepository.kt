package com.apps.imageAI.data.repository

import com.apps.imageAI.data.model.ChatMessage
import com.apps.imageAI.data.source.local.imageAIDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MessageRepository {
    fun getMessages(recentChatId: Long): Flow<List<ChatMessage>>
    suspend fun addMessage(message: ChatMessage):Long
    suspend fun deleteMessages(recentChatId: Long)
    suspend fun updateStatus(messageId:Long,status:Int):Int
    suspend fun updateContent(messageId:Long,content:String,url:String):Int
    fun getMessages(recentChatId: Long,limit:Int): List<ChatMessage>
}

class MessageRepositoryImpl @Inject constructor(
    private val ImageAIDao: imageAIDao,
) : MessageRepository {

    override fun getMessages(recentChatId: Long): Flow<List<ChatMessage>> = ImageAIDao.getAllMessagesAsFlow(recentChatId)

    override suspend fun addMessage(message: ChatMessage): Long = ImageAIDao.addMessage(message)

    override suspend fun deleteMessages(recentChatId: Long) = ImageAIDao.deleteAllMessages(recentChatId)

    override suspend fun updateStatus(messageId: Long, status: Int): Int = ImageAIDao.updateMessageStatus(messageId,status)
    override suspend fun updateContent(messageId: Long, content: String,url:String): Int = ImageAIDao.updateMessageContent(messageId,content,url)
    override fun getMessages(recentChatId: Long, limit: Int): List<ChatMessage> = ImageAIDao.getMessages(recentChatId,limit)
}