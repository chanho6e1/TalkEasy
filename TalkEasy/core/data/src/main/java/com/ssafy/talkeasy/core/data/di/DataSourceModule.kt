package com.ssafy.talkeasy.core.data.di

import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.datasource.member.MemberRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.member.MemberRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.service.AuthApiService
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
}