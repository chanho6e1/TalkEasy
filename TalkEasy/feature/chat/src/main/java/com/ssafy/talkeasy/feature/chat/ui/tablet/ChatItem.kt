package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.Chat
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.cabbage_pont
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun PartnerChat(memberName: String, nickname: String, messages: List<Chat>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        when (messages.size) {
            0 -> {}
            1 -> {
                item {
                    PartnerChatItemHead(
                        memberName = memberName,
                        nickname = nickname,
                        chat = messages[0],
                        isLastMessage = true
                    )
                }
            }

            else -> {
                item {
                    PartnerChatItemHead(
                        memberName = memberName,
                        nickname = nickname,
                        chat = messages[0],
                        isLastMessage = false
                    )
                }

                items(messages.size - 2) { index ->
                    Box(modifier = Modifier.padding(start = 47.dp)) {
                        PartnerChatBalloonTail(chat = messages[index + 1], isLastMessage = false)
                    }
                }

                item {
                    Box(modifier = Modifier.padding(start = 47.dp)) {
                        PartnerChatBalloonTail(chat = messages.last(), isLastMessage = true)
                    }
                }
            }
        }
    }
}

@Composable
fun MyChat(messages: List<Chat>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.End
    ) {
        when (messages.size) {
            0 -> {}
            1 -> {
                item {
                    MyChatBalloonHead(chat = messages[0], isLastMessage = true)
                }
            }

            else -> {
                item {
                    MyChatBalloonHead(chat = messages[0], isLastMessage = false)
                }

                items(messages.size - 2) { index ->
                    Box(modifier = Modifier.padding(end = 10.dp)) {
                        MyChatBalloonTail(chat = messages[index + 1], isLastMessage = false)
                    }
                }

                item {
                    Box(modifier = Modifier.padding(end = 10.dp)) {
                        MyChatBalloonTail(chat = messages.last(), isLastMessage = true)
                    }
                }
            }
        }
    }
}

@Composable
fun PartnerChatItemHead(memberName: String, nickname: String, chat: Chat, isLastMessage: Boolean) {
    Box {
        PartnerProfile(memberName, nickname)

        PartnerChatBalloonHead(start = 37, top = 19, chat = chat, isLastMessage = isLastMessage)
    }
}

@Composable
fun PartnerProfile(memberName: String, nickname: String) {
    val chatName = if (nickname == "") {
        memberName
    } else {
        String.format(stringResource(R.string.content_name_and_nickname), memberName, nickname)
    }

    Row(verticalAlignment = Alignment.Top) {
        Profile(size = 37)

        Text(
            modifier = Modifier
                .widthIn(max = 310.dp)
                .padding(start = 12.dp),
            style = typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = chatName
        )
    }
}

@Composable
fun PartnerChatBalloonHead(start: Int = 0, top: Int = 0, chat: Chat, isLastMessage: Boolean) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(modifier = Modifier.padding(start = start.dp, top = top.dp)) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 5.dp),
                painter = painterResource(id = drawable.bg_chat_balloon_left_head),
                contentDescription = stringResource(R.string.image_the_person_chat_balloon_head),
                tint = md_theme_light_secondaryContainer
            )

            Card(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 10.dp),
                shape = shapes.extraSmall,
                colors = CardDefaults.cardColors(md_theme_light_secondaryContainer)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                        .widthIn(max = 214.dp),
                    style = typography.bodyMedium,
                    text = chat.message
                )
            }
        }

        if (isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time
            )
        }
    }
}

@Composable
fun PartnerChatBalloonTail(chat: Chat, isLastMessage: Boolean) {
    Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Card(
            modifier = Modifier.wrapContentSize(),
            shape = shapes.extraSmall,
            colors = CardDefaults.cardColors(md_theme_light_secondaryContainer)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .widthIn(max = 214.dp),
                style = typography.bodyMedium,
                text = chat.message
            )
        }

        if (isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time
            )
        }
    }
}

@Composable
fun MyChatBalloonHead(end: Int = 0, top: Int = 0, chat: Chat, isLastMessage: Boolean) {
    Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        if (isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time
            )
        }

        Box(modifier = Modifier.padding(end = end.dp, top = top.dp)) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 5.dp),
                painter = painterResource(id = drawable.bg_chat_balloon_right_head),
                contentDescription = stringResource(R.string.image_the_person_chat_balloon_head),
                tint = seed
            )

            Card(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 10.dp),
                shape = shapes.extraSmall,
                colors = CardDefaults.cardColors(seed)
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 18.dp, vertical = 10.dp)
                        .widthIn(max = 214.dp),
                    style = typography.bodyMedium,
                    text = chat.message
                )
            }
        }
    }
}

@Composable
fun MyChatBalloonTail(chat: Chat, isLastMessage: Boolean) {
    Row(modifier = Modifier.wrapContentSize(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        if (isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time
            )
        }

        Card(
            modifier = Modifier.wrapContentSize(),
            shape = shapes.extraSmall,
            colors = CardDefaults.cardColors(seed)
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .widthIn(max = 214.dp),
                style = typography.bodyMedium,
                text = chat.message
            )
        }
    }
}