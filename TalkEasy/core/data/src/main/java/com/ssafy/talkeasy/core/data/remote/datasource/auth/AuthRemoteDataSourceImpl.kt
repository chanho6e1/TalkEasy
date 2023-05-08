package com.ssafy.talkeasy.core.data.remote.datasource.auth

import com.google.gson.Gson
import com.ssafy.talkeasy.core.data.remote.datasource.common.DefaultResponse
import com.ssafy.talkeasy.core.data.remote.service.AuthApiService
import com.ssafy.talkeasy.core.domain.entity.request.MemberRequestBody
import java.io.File
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRemoteDataSource {

    override suspend fun requestLogin(accessToken: String, role: Int): DefaultResponse<String> =
        authApiService.requestLogin(accessToken, role)

    override suspend fun requestJoin(
        member: MemberRequestBody,
        image: File?,
    ): DefaultResponse<String> {
        val memberJson = Gson().toJson(member)
        val memberRequestBody = memberJson.toRequestBody("application/json".toMediaTypeOrNull())

        image?.let {
            val requestFile = image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val multipart =
                MultipartBody.Part.createFormData("multipartFile", image.name, requestFile)
            return authApiService.requestJoin(memberRequestBody, multipart)
        }
        return authApiService.requestJoin(memberRequestBody, null)
    }
}