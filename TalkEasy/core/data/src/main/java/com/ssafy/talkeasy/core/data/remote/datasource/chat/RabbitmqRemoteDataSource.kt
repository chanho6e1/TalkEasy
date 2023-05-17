package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest

interface RabbitmqRemoteDataSource {

    suspend fun sendChatMessage(message: MessageRequest): Boolean

    suspend fun receiveChatMessage(
        roomId: String,
        fromUserId: String,
        callback: (Any) -> Unit,
    )

    suspend fun readChatMessage(readMessageRequest: ReadMessageRequest): Boolean

    suspend fun stopReceiveMessage()

    suspend fun disconnect()
}