package com.ssafy.talkeasy.core.data.di

import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.datasource.chat.ChatRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.chat.ChatRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.datasource.follow.FollowRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.follow.FollowRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.datasource.member.MemberRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.member.MemberRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.service.AACApiService
import com.ssafy.talkeasy.core.data.remote.service.AuthApiService
import com.ssafy.talkeasy.core.data.remote.service.ChatApiService
import com.ssafy.talkeasy.core.data.remote.service.FollowApiService
import com.ssafy.talkeasy.core.data.remote.service.MemberApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideAuthDataSource(
        authApiService: AuthApiService,
    ): AuthRemoteDataSource = AuthRemoteDataSourceImpl(authApiService)

    @Provides
    @Singleton
    fun provideMemberDataSource(
        memberApiService: MemberApiService,
    ): MemberRemoteDataSource = MemberRemoteDataSourceImpl(memberApiService)

    @Provides
    @Singleton
    fun provideFollowDataSource(
        followApiService: FollowApiService,
    ): FollowRemoteDataSource = FollowRemoteDataSourceImpl(followApiService)

    @Provides
    @Singleton
    fun provideChatRemoteDataSource(
        chatApiService: ChatApiService,
    ): ChatRemoteDataSource = ChatRemoteDataSourceImpl(chatApiService)

    @Provides
    @Singleton
    fun provideAACRemoteDataSource(
        aacApiService: AACApiService,
    ): AACRemoteDataSource = AACRemoteDataSourceImpl(aacApiService)
}