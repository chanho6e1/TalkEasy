package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.chat.ChatRemoteDataSource
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault
import com.ssafy.talkeasy.core.domain.repository.ChatRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class ChatRepositoryImpl @Inject constructor(
    private val chatRemoteDataSource: ChatRemoteDataSource,
) : ChatRepository {

    override suspend fun getChatHistory(
        roomId: String,
        offset: Int,
        size: Int,
    ): Resource<PagingDefault<List<Chat>>> = wrapToResource(Dispatchers.IO) {
        val response = chatRemoteDataSource.getChatHistory(roomId, offset, size)
        PagingDefault(
            status = response.status,
            data = response.data.map { it.toDomainModel() },
            totalPages = response.totalPages
        )
    }

    override suspend fun sendChatMessage(message: MessageRequest): Resource<Chat> =
        wrapToResource(Dispatchers.IO) {
            chatRemoteDataSource.sendMessage(message = message).toDomainModel()
        }
}