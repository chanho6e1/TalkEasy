package com.ssafy.talkeasy.core.domain.repository

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.SosAlarmRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault

interface FollowRepository {

    suspend fun requestFollowList(): Resource<PagingDefault<List<Follow>>>

    suspend fun requestFollow(toUserId: String, memo: String): Resource<String>

    suspend fun requestNotificationList(): Resource<Default<List<MyNotificationItem>>>

    suspend fun requestSaveWardSOS(
        requestSosAlarmDto: SosAlarmRequestBody,
    ): Resource<Default<String>>

    suspend fun modifyFollowMemo(followId: String, memo: String): Resource<Follow>
}