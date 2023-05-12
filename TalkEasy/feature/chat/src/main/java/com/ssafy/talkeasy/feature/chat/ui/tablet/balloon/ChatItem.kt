package com.ssafy.talkeasy.feature.chat.ui.tablet.balloon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.feature.common.util.ChatDirection

@Composable
fun PartnerChat(memberName: String, nickname: String, messages: List<Chat>) {
    if (messages.isNotEmpty()) {
        Box {
            PartnerChatItemHead(
                memberName = memberName,
                nickname = nickname,
                type = messages[0].type
            )

            LazyColumn(
                modifier = Modifier.padding(start = 47.dp, top = 19.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages.size) { index ->
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
        Box(contentAlignment = Alignment.TopEnd) {
            MyChatItemHead(messages[0].type)

            LazyColumn(
                modifier = Modifier.padding(end = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.End
            ) {
                items(messages.size) { index ->
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