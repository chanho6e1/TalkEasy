package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.chat.RabbitmqRemoteDataSource
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MessageRequest
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.repository.RabbitmqRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class RabbitmqRepositoryImpl @Inject constructor(
    private val rabbitmqRemoteDataSource: RabbitmqRemoteDataSource,
) : RabbitmqRepository {

    override suspend fun sendChatMessage(message: MessageRequest): Resource<Boolean> =
        wrapToResource(Dispatchers.IO) {
            rabbitmqRemoteDataSource.sendChatMessage(message)
        }

    override suspend fun receiveMessage(
        roomId: String,
        fromUserId: String,
        callback: (Chat) -> Unit,
    ) = rabbitmqRemoteDataSource.receiveChatMessage(roomId, fromUserId, callback)

    override suspend fun readChatMessage(
        readMessageRequest: ReadMessageRequest,
    ): Resource<Boolean> = wrapToResource(Dispatchers.IO) {
        rabbitmqRemoteDataSource.readChatMessage(readMessageRequest)
    }

    override suspend fun stopReceiveMessage() {
        rabbitmqRemoteDataSource.stopReceiveMessage()
    }

    override suspend fun disconnect() {
        rabbitmqRemoteDataSource.disconnect()
    }
}