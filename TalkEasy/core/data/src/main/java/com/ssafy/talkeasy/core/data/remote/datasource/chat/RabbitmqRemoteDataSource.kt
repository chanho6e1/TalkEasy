package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest
import com.ssafy.talkeasy.core.domain.entity.response.Chat

interface RabbitmqRemoteDataSource {

    suspend fun sendChatMessage(message: MessageRequest): Boolean

    suspend fun receiveChatMessage(
        roomId: String,
        fromUserId: String,
        callback: (Chat) -> Unit,
    )

    suspend fun readChatMessage(readMessageRequest: ReadMessageRequest): Boolean

    suspend fun stopReceiveMessage()

    suspend fun disconnect()
}