package com.ssafy.talkeasy.core.data.di

import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.service.AuthApiService
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
}