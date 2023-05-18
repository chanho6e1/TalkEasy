package com.ssafy.talkeasy.core.domain.usecase.chat

import com.ssafy.talkeasy.core.domain.repository.RabbitmqRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DisConnectRabbitmqUseCase @Inject constructor(
    private val rabbitmqRepository: RabbitmqRepository,
) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        rabbitmqRepository.disconnect()
    }
}