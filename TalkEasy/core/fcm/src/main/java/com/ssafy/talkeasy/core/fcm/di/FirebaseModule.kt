package com.ssafy.talkeasy.core.fcm.di

import com.ssafy.talkeasy.core.fcm.FirebaseMessagingChatService
import com.ssafy.talkeasy.core.fcm.FirebaseTokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    abstract fun bindFirebaseTokenProvider(
        service: FirebaseMessagingChatService,
    ): FirebaseTokenProvider
}