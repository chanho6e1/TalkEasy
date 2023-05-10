package com.ssafy.talkeasy.core.data.remote.datasource.follow

import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.FollowApiService
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import javax.inject.Inject

class FollowRemoteDataSourceImpl @Inject constructor(
    private val followApiService: FollowApiService,
) : FollowRemoteDataSource {

    override suspend fun requestFollowList(): PagingDefaultResponse<List<Follow>> =
        followApiService.requestFollowList()
}