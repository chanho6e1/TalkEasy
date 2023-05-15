package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.common.component.ChatBalloon
import com.ssafy.talkeasy.feature.common.component.MyChatItemHead
import com.ssafy.talkeasy.feature.common.component.PartnerChatItemHead
import com.ssafy.talkeasy.feature.common.util.ChatDirection

@Composable
fun PartnerChat(chatPartner: Follow, messages: List<Chat>) {
    if (messages.isNotEmpty()) {
        Box {
            PartnerChatItemHead(chatPartner = chatPartner, type = messages[0].type)

            Column(
                modifier = Modifier.padding(start = 47.dp, top = 19.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(messages.size) { index ->
                    ChatBalloon(
                        chatDirection = ChatDirection.PARTNER,
                        chat = messages[index],
                        isLastMessage = index == messages.lastIndex
                    )
                }
            }
        }
    }
}

@Composable
fun MyChat(messages: List<Chat>) {
    if (messages.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            MyChatItemHead(messages[0].type)

            Column(
                modifier = Modifier.padding(end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                repeat(messages.size) { index ->
                    ChatBalloon(
                        chatDirection = ChatDirection.ME,
                        chat = messages[index],
                        isLastMessage = index == messages.lastIndex
                    )
                }
            }
        }
    }
}