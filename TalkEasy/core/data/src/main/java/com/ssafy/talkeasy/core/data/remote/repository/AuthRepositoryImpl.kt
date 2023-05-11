package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Auth
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.repository.AuthRepository
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun requestLogin(accessToken: String, role: Int): Resource<Default<Auth>> =
        wrapToResource(Dispatchers.IO) {
            val defaultResponse = authRemoteDataSource.requestLogin(accessToken, role)
            val auth = defaultResponse.data.toDomainModel()
            Default(status = defaultResponse.status, data = auth)
        }

    override suspend fun requestJoin(
        member: MemberRequestBody,
        image: File?,
    ): Resource<Default<String>> =
        wrapToResource(Dispatchers.IO) {
            authRemoteDataSource.requestJoin(member, image).toDomainModel()
        }
}