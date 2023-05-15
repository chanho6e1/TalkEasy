package com.ssafy.talkeasy.core.data.remote.datasource.chat

import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest

interface ChatRemoteDataSource {

    suspend fun getChatHistory(
        roomId: String,
        offset: Int,
        size: Int,
    ): PagingDefaultResponse<List<ChatResponse>>

    suspend fun sendMessage(message: MessageRequest): RabbitMqResponse
}