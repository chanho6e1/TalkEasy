package com.ssafy.talkeasy.core.data.di

import com.ssafy.talkeasy.core.data.common.util.AuthInterceptorClient
import com.ssafy.talkeasy.core.data.common.util.NoAuthInterceptorClient
import com.ssafy.talkeasy.core.data.remote.service.AACApiService
import com.ssafy.talkeasy.core.data.remote.service.AuthApiService
import com.ssafy.talkeasy.core.data.remote.service.ChatApiService
import com.ssafy.talkeasy.core.data.remote.service.FCMApiService
import com.ssafy.talkeasy.core.data.remote.service.FollowApiService
import com.ssafy.talkeasy.core.data.remote.service.MemberApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthApiService(
        @NoAuthInterceptorClient
        retrofit: Retrofit,
    ): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideMemberApiService(
        @AuthInterceptorClient
        retrofit: Retrofit,
    ): MemberApiService = retrofit.create(MemberApiService::class.java)

    @Provides
    @Singleton
    fun provideFollowApiService(
        @AuthInterceptorClient
        retrofit: Retrofit,
    ): FollowApiService = retrofit.create(FollowApiService::class.java)

    @Provides
    @Singleton
    fun provideChatApiService(
        @AuthInterceptorClient
        retrofit: Retrofit,
    ): ChatApiService = retrofit.create(ChatApiService::class.java)

    @Provides
    @Singleton
    fun provideAACApiService(
        @AuthInterceptorClient
        retrofit: Retrofit,
    ): AACApiService = retrofit.create(AACApiService::class.java)

    @Provides
    @Singleton
    fun provideFCMApiService(
        @AuthInterceptorClient
        retrofit: Retrofit,
    ): FCMApiService = retrofit.create(FCMApiService::class.java)
}