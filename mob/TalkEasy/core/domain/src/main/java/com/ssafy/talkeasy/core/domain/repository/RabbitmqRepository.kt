package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest

interface RabbitmqRepository {

    suspend fun sendChatMessage(message: MessageRequest): Resource<Boolean>

    suspend fun receiveMessage(
        roomId: String,
        fromUserId: String,
        callback: (Any) -> Unit,
    )

    suspend fun readChatMessage(readMessageRequest: ReadMessageRequest): Resource<Boolean>

    suspend fun stopReceiveMessage()

    suspend fun disconnect()
}