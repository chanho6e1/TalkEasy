package com.ssafy.talkeasy.core.data.remote.datasource.member

import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo

interface MemberRemoteDataSource {

    suspend fun requestMemberInfo(): DefaultResponse<MemberInfo>
}