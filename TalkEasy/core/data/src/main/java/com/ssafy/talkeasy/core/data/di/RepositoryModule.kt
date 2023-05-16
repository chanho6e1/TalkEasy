package com.ssafy.talkeasy.core.data.di

import com.ssafy.talkeasy.core.data.remote.datasource.aac.AACRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.datasource.chat.ChatRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.follow.FollowRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.datasource.member.MemberRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.repository.AACRepositoryImpl
import com.ssafy.talkeasy.core.data.remote.repository.AuthRepositoryImpl
import com.ssafy.talkeasy.core.data.remote.repository.ChatRepositoryImpl
import com.ssafy.talkeasy.core.data.remote.repository.FollowRepositoryImpl
import com.ssafy.talkeasy.core.data.remote.repository.MemberRepositoryImpl
import com.ssafy.talkeasy.core.domain.repository.AACRepository
import com.ssafy.talkeasy.core.domain.repository.AuthRepository
import com.ssafy.talkeasy.core.domain.repository.ChatRepository
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import com.ssafy.talkeasy.core.domain.repository.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl,
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideMemberRepository(
        memberRemoteDataSource: MemberRemoteDataSourceImpl,
    ): MemberRepository = MemberRepositoryImpl(memberRemoteDataSource)

    @Provides
    @Singleton
    fun provideFollowRepository(
        followRemoteDataSource: FollowRemoteDataSourceImpl,
    ): FollowRepository = FollowRepositoryImpl(followRemoteDataSource)

    @Provides
    @Singleton
    fun provideChatRepository(
        chatRemoteDataSource: ChatRemoteDataSource,
    ): ChatRepository = ChatRepositoryImpl(chatRemoteDataSource)

    @Provides
    @Singleton
    fun provideAACRepository(
        aacRemoteDataSource: AACRemoteDataSource,
    ): AACRepository = AACRepositoryImpl(aacRemoteDataSource)
}