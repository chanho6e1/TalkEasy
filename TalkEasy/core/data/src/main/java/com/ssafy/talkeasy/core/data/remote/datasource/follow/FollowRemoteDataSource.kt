package com.ssafy.talkeasy.core.data.remote.datasource.follow

import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.domain.entity.response.Follow

interface FollowRemoteDataSource {

    suspend fun requestFollowList(): PagingDefaultResponse<List<Follow>>
}