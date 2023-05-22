package com.ssafy.talkeasy.feature.common.util

import com.ssafy.talkeasy.core.domain.entity.response.FCMChat
import kotlinx.coroutines.flow.MutableSharedFlow

object ChatMessageManager {

    val chatMessageFlow = MutableSharedFlow<FCMChat>(extraBufferCapacity = 1)
}