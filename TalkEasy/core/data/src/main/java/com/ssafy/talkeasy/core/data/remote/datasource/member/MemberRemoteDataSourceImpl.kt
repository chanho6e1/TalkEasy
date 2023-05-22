package com.ssafy.talkeasy.core.data.remote.datasource.member

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.MemberApiService
import javax.inject.Inject

class MemberRemoteDataSourceImpl @Inject constructor(
    private val memberApiService: MemberApiService,
) : MemberRemoteDataSource {

    override suspend fun requestMemberInfo(): DefaultResponse<MemberInfoResponse> =
        memberApiService.requestMemberInfo()
}