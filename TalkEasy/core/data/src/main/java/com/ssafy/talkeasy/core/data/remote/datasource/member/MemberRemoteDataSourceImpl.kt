package com.ssafy.talkeasy.core.data.remote.datasource.member

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.MemberApiService
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberApiService: MemberApiService,
) : MemberRemoteDataSource {

    override suspend fun requestMemberInfo(): DefaultResponse<MemberInfo> =
        memberApiService.requestMemberInfo()
}