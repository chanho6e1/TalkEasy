package com.ssafy.talkeasy.core.domain.usecase.chat

import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.repository.RabbitmqRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class ReceiveChatMessageUseCase @Inject constructor(
    private val rabbitmqRepository: RabbitmqRepository,
) {

    suspend operator fun invoke(
        roomId: String,
        fromUserId: String,
        callback: (Chat) -> Unit,
    ) = withContext(Dispatchers.IO) {
        rabbitmqRepository.receiveMessage(roomId, fromUserId, callback)
    }
}