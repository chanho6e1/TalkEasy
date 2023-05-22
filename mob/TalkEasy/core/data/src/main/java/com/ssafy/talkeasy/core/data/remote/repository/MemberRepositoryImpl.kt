package com.ssafy.talkeasy.core.data.remote.repository

import com.ssafy.talkeasy.core.data.common.util.wrapToResource
import com.ssafy.talkeasy.core.data.remote.datasource.member.MemberRemoteDataSource
import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo
import com.ssafy.talkeasy.core.domain.repository.MemberRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers

class MemberRepositoryImpl @Inject constructor(
    private val memberRemoteDataSource: MemberRemoteDataSource,
) : MemberRepository {

    override suspend fun requestMemberInfo(): Resource<Default<MemberInfo>> =
        wrapToResource(Dispatchers.IO) {
            val defaultResponse = memberRemoteDataSource.requestMemberInfo()
            val memberInfo = defaultResponse.data.toDomainModel()
            Default(status = defaultResponse.status, data = memberInfo)
        }
}