package com.ssafy.talkeasy.core.domain.usecase.fcm

import com.ssafy.talkeasy.core.domain.repository.FCMRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterFCMTokenUseCase @Inject constructor(
    private val fcmRepository: FCMRepository,
) {

    suspend operator fun invoke(appToken: String) = withContext(Dispatchers.IO) {
        fcmRepository.registerFCMToken(appToken)
    }
}