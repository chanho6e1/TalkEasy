package com.ssafy.talkeasy.core.domain.usecase.follow

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FollowListUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {

    suspend operator fun invoke(): Resource<PagingDefault<List<Follow>>> =
        withContext(Dispatchers.IO) {
            followRepository.requestFollowList()
        }
}