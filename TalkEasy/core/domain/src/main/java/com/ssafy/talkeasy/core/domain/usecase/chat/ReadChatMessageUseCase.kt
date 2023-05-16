package com.ssafy.talkeasy.core.domain.usecase.chat

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.ReadMessageRequest
import com.ssafy.talkeasy.core.domain.repository.RabbitmqRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class ReadChatMessageUseCase @Inject constructor(
    private val rabbitmqRepository: RabbitmqRepository,
) {

    suspend operator fun invoke(
        readMessageRequest: ReadMessageRequest,
    ): Resource<Boolean> = withContext(Dispatchers.IO) {
        rabbitmqRepository.readChatMessage(readMessageRequest)
    }
}