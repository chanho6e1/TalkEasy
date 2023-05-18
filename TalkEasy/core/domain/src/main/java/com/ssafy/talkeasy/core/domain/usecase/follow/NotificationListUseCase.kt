package com.ssafy.talkeasy.core.domain.usecase.follow

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class NotificationListUseCase @Inject constructor(
    private val followRepository: FollowRepository,
) {

    suspend operator fun invoke(): Resource<Default<List<MyNotificationItem>>> =
        withContext(Dispatchers.IO) {
            followRepository.requestNotificationList()
        }
}