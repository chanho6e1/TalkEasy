package com.ssafy.talkeasy.core.domain.usecase.follow

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class RequestFollowUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {

    suspend operator fun invoke(toUserId: String, memo: String): Resource<String> =
        withContext(Dispatchers.IO) {
            followRepository.requestFollow(toUserId, memo)
        }
}