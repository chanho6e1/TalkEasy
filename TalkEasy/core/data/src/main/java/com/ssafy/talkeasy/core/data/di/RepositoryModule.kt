package com.ssafy.talkeasy.core.data.di

import com.ssafy.talkeasy.core.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.talkeasy.core.data.remote.repository.AuthRepositoryImpl
import com.ssafy.talkeasy.core.domain.repository.AuthRepository
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
}