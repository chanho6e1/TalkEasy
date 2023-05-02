package com.ssafy.talkeasy.core.data.remote.interceptor

import com.ssafy.talkeasy.feature.common.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", preferences.accessToken.toString()).build()

        return chain.proceed(request)
    }
}