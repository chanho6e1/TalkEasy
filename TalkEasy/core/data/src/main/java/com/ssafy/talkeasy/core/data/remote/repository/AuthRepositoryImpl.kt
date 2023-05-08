package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Auth
import com.ssafy.talkeasy.core.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun requestLogin(accessToken: String): Resource<Auth> =
        wrapToResource(Dispatchers.IO) {
            authRemoteDataSource.requestLogin(accessToken).toDomainModel()
        }

    override suspend fun requestJoin(member: MemberRequestBody): Resource<Auth> =
        wrapToResource(Dispatchers.IO) {
            authRemoteDataSource.requestJoin(member).toDomainModel()
        }
}