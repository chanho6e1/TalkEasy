package com.ssafy.talkeasy.core.domain.usecase.auth

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import com.ssafy.talkeasy.core.domain.entity.response.Auth
import com.ssafy.talkeasy.core.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JoinUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(member: MemberRequestBody): Resource<Auth> =
        withContext(Dispatchers.IO) {
            authRepository.requestJoin(member)
        }
}