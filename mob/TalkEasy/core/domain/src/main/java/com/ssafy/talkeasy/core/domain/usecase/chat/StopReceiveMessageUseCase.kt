package com.ssafy.talkeasy.core.domain.usecase.chat

import com.ssafy.talkeasy.core.domain.repository.RabbitmqRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class StopReceiveMessageUseCase @Inject constructor(
    private val rabbitmqRepository: RabbitmqRepository,
) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        rabbitmqRepository.stopReceiveMessage()
    }
}