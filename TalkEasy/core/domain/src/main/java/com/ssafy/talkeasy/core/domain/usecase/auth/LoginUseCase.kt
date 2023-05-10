package com.ssafy.talkeasy.core.domain.usecase.auth

import com.ssafy.talkeasy.core.domain.Resource
import com.ssafy.talkeasy.core.domain.entity.response.Auth
import com.ssafy.talkeasy.core.domain.entity.response.Default
import com.ssafy.talkeasy.core.domain.repository.AuthRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {

    suspend operator fun invoke(accessToken: String, role: Int): Resource<Default<Auth>> =
        withContext(Dispatchers.IO) {
            authRepository.requestLogin(accessToken, role)
        }
}