package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.follow.FollowRemoteDataSource
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.core.domain.entity.response.PagingDefault
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class FollowRepositoryImpl @Inject constructor(
    private val followRemoteDataSource: FollowRemoteDataSource,
) : FollowRepository {

    override suspend fun requestFollowList(): Resource<PagingDefault<List<Follow>>> =
        wrapToResource(Dispatchers.IO) {
            followRemoteDataSource.requestFollowList().toDomainModel()
        }
}