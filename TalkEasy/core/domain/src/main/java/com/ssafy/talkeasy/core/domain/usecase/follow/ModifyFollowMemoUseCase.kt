package com.ssafy.talkeasy.core.domain.usecase.follow

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class ModifyFollowMemoUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {

    suspend operator fun invoke(followId: String, memo: String): Resource<String> =
        withContext(Dispatchers.IO) {
            followRepository.modifyFollowMemo(followId, memo)
        }
}