package com.ssafy.talkeasy.core.data.remote.datasource.follow

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.datasource.common.PagingDefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.FollowApiService
import javax.inject.Inject

class FollowRemoteDataSourceImpl @Inject constructor(
    private val followApiService: FollowApiService,
) : FollowRemoteDataSource {

    override suspend fun requestFollowList(): PagingDefaultResponse<List<FollowResponse>> =
        followApiService.requestFollowList()

    override suspend fun requestFollow(
        toUserId: String,
        body: AddFollowRequest,
    ): DefaultResponse<String> = followApiService.requestFollow(toUserId, body)
}