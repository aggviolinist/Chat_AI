package com.apps.aivision.data.repository

import com.apps.aivision.data.model.ChatMessage
import com.apps.aivision.data.source.local.AIVisionDao
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
    private val aiVisionDao: AIVisionDao,
) : MessageRepository {

    override fun getMessages(recentChatId: Long): Flow<List<ChatMessage>> = aiVisionDao.getAllMessagesAsFlow(recentChatId)

    override suspend fun addMessage(message: ChatMessage): Long = aiVisionDao.addMessage(message)

    override suspend fun deleteMessages(recentChatId: Long) = aiVisionDao.deleteAllMessages(recentChatId)

    override suspend fun updateStatus(messageId: Long, status: Int): Int = aiVisionDao.updateMessageStatus(messageId,status)
    override suspend fun updateContent(messageId: Long, content: String,url:String): Int = aiVisionDao.updateMessageContent(messageId,content,url)
    override fun getMessages(recentChatId: Long, limit: Int): List<ChatMessage> = aiVisionDao.getMessages(recentChatId,limit)
}