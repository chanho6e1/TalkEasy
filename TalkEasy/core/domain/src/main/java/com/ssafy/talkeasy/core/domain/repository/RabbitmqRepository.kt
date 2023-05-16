package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest
import com.ssafy.talkeasy.core.domain.entity.response.Chat

interface RabbitmqRepository {

    suspend fun sendChatMessage(message: MessageRequest): Resource<Boolean>

    suspend fun receiveMessage(
        roomId: String,
        fromUserId: String,
        callback: (Chat) -> Unit,
    )

    suspend fun readChatMessage(readMessageRequest: ReadMessageRequest): Resource<Boolean>

    suspend fun stopReceiveMessage()

    suspend fun disconnect()
}