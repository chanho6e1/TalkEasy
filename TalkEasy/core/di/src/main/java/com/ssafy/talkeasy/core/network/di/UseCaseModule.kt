package com.ssafy.talkeasy.core.network.di

import com.ssafy.talkeasy.core.domain.repository.AACRepository
import com.ssafy.talkeasy.core.domain.repository.AuthRepository
import com.ssafy.talkeasy.core.domain.repository.ChatRepository
import com.ssafy.talkeasy.core.domain.repository.FollowRepository
import com.ssafy.talkeasy.core.domain.repository.MemberRepository
import com.ssafy.talkeasy.core.domain.repository.RabbitmqRepository
import com.ssafy.talkeasy.core.domain.usecase.aac.GenerateSentenceUseCase
import com.ssafy.talkeasy.core.domain.usecase.aac.GetTTSMp3UrlUseCase
import com.ssafy.talkeasy.core.domain.usecase.aac.GetWordListUseCase
import com.ssafy.talkeasy.core.domain.usecase.auth.JoinUseCase
import com.ssafy.talkeasy.core.domain.usecase.auth.LoginUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.DisConnectRabbitmqUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.GetChatHistoryUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.ReadChatMessageUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.ReceiveChatMessageUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.SendChatMessageUseCase
import com.ssafy.talkeasy.core.domain.usecase.chat.StopReceiveMessageUseCase
import com.ssafy.talkeasy.core.domain.usecase.follow.FollowListUseCase
import com.ssafy.talkeasy.core.domain.usecase.follow.RequestFollowUseCase
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

    @Singleton
    @Provides
    fun provideFollowListUseCase(followRepository: FollowRepository): FollowListUseCase =
        FollowListUseCase(followRepository)

    @Singleton
    @Provides
    fun provideGetChatHistoryUseCase(chatRepository: ChatRepository): GetChatHistoryUseCase =
        GetChatHistoryUseCase(chatRepository)

    @Singleton
    @Provides
    fun provideGenerateSentenceUseCase(aacRepository: AACRepository): GenerateSentenceUseCase =
        GenerateSentenceUseCase(aacRepository)

    @Singleton
    @Provides
    fun provideGetWordListUseCase(aacRepository: AACRepository): GetWordListUseCase =
        GetWordListUseCase(aacRepository)

    @Singleton
    @Provides
    fun provideGetTTSMp3UrlUseCase(aacRepository: AACRepository): GetTTSMp3UrlUseCase =
        GetTTSMp3UrlUseCase(aacRepository)

    @Singleton
    @Provides
    fun provideRequestFollowUseCase(followRepository: FollowRepository): RequestFollowUseCase =
        RequestFollowUseCase(followRepository)

    @Singleton
    @Provides
    fun provideReceiveChatMessageUseCase(
        rabbitmqRepository: RabbitmqRepository,
    ): ReceiveChatMessageUseCase =
        ReceiveChatMessageUseCase(rabbitmqRepository)

    @Singleton
    @Provides
    fun provideSendChatMessageUseCase(
        rabbitmqRepository: RabbitmqRepository,
    ): SendChatMessageUseCase =
        SendChatMessageUseCase(rabbitmqRepository)

    @Singleton
    @Provides
    fun provideReadChatMessageUseCase(
        rabbitmqRepository: RabbitmqRepository,
    ): ReadChatMessageUseCase =
        ReadChatMessageUseCase(rabbitmqRepository)

    @Singleton
    @Provides
    fun provideStopChatMessageUseCase(
        rabbitmqRepository: RabbitmqRepository,
    ): StopReceiveMessageUseCase =
        StopReceiveMessageUseCase(rabbitmqRepository)

    @Singleton
    @Provides
    fun provideDisConnectRabbitmqUseCase(
        rabbitmqRepository: RabbitmqRepository,
    ): DisConnectRabbitmqUseCase =
        DisConnectRabbitmqUseCase(rabbitmqRepository)
}