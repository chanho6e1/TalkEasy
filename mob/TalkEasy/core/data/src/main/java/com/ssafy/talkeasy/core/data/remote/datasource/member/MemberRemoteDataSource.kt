package com.ssafy.talkeasy.core.data.remote.datasource.member

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse

interface MemberRemoteDataSource {

    suspend fun requestMemberInfo(): DefaultResponse<MemberInfoResponse>
}