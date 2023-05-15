package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault

interface ChatRepository {

    suspend fun getChatHistory(
        roomId: String,
        offset: Int,
        size: Int,
    ): Resource<PagingDefault<List<Chat>>>

    suspend fun sendChatMessage(message: MessageRequest): Resource<Chat>
}