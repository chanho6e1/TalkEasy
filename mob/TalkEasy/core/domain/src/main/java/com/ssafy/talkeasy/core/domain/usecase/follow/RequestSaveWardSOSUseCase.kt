package com.ssafy.talkeasy.core.domain.usecase.follow

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.SosAlarmRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
data class RequestSaveWardSOSUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {

    suspend operator fun invoke(
        requestSosAlarmDto: SosAlarmRequestBody,
    ): Resource<Default<String>> =
        withContext(Dispatchers.IO) {
            followRepository.requestSaveWardSOS(requestSosAlarmDto)
        }
}