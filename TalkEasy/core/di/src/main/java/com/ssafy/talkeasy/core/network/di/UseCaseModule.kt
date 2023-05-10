package com.ssafy.talkeasy.core.network.di

import com.ssafy.talkeasy.core.domain.repository.AuthRepository
import com.ssafy.talkeasy.core.domain.repository.MemberRepository
import com.ssafy.talkeasy.core.domain.usecase.auth.JoinUseCase
import com.ssafy.talkeasy.core.domain.usecase.auth.LoginUseCase
import com.ssafy.talkeasy.core.domain.usecase.member.MemberInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideLogInUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Singleton
    @Provides
    fun provideJoinUseCase(authRepository: AuthRepository): JoinUseCase =
        JoinUseCase(authRepository)

    @Singleton
    @Provides
    fun provideMemberInfoUseCase(memberRepository: MemberRepository): MemberInfoUseCase =
        MemberInfoUseCase(memberRepository)
}