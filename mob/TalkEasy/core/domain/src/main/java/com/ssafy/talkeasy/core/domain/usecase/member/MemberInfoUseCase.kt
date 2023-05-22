package com.ssafy.talkeasy.core.domain.usecase.member

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo
import com.ssafy.talkeasy.core.domain.repository.MemberRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class MemberInfoUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {

    suspend operator fun invoke(): Resource<Default<MemberInfo>> = withContext(Dispatchers.IO) {
        memberRepository.requestMemberInfo()
    }
}