package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.chat.ui.tablet.balloon.MyChat
import com.ssafy.talkeasy.feature.chat.ui.tablet.balloon.PartnerChat
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal14Vertical
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.common.util.toTimeString

@Composable
fun ChatContent(chatPartner: Follow, chats: List<Chat>) {
    LazyColumn(
        modifier = Modifier.padding(11.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(items = sublistChat(chats)) {
            if (it[0].fromUserId == chatPartner.userId) {
                PartnerChat(
                    memberName = chatPartner.userName,
                    nickname = chatPartner.nickName,
                    messages = it
                )
            } else {
                MyChat(messages = it)
            }
        }
    }
}

@Composable
fun ChatPartner(chatMode: ChatMode, chatPartner: Follow?) {
    val profileUrl: String
    val memberName: String

    if (chatPartner == null || chatMode == ChatMode.TTS) {
        profileUrl = ""
        memberName = stringResource(id = R.string.content_chat_mode_tts)
    } else {
        profileUrl = chatPartner.imageUrl
        memberName = chatPartner.userName
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Profile(profileUrl = profileUrl, size = 48)

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            modifier = Modifier.width(203.dp),
            text = memberName,
            color = md_theme_light_onBackground,
            style = textStyleNormal22
        )

        Spacer(modifier = Modifier.width(28.dp))

        Surface(shape = shapes.extraSmall, color = black_squeeze) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                text = "변경",
                color = delta,
                style = typography.bodyLarge
            )
        }
    }
}

@Composable
fun OpenChatRoomButton() {
    Image(
        modifier = Modifier.width(32.dp),
        painter = painterResource(id = drawable.bg_trapezoid_secondary_left),
        contentDescription = stringResource(id = R.string.image_open_chat_frame)
    )

    Text(
        text = stringResource(R.string.content_open_chat_room),
        color = md_theme_light_background,
        style = textStyleNormal14Vertical
    )
}

fun sublistChat(chats: List<Chat>): List<List<Chat>> {
    var startIndex = 0
    var lastIndex = 0
    var firstChat = chats[startIndex]
    val result = mutableListOf<List<Chat>>()

    while (lastIndex <= chats.lastIndex) {
        if (firstChat.fromUserId == chats[lastIndex].fromUserId) {
            if (firstChat.time.toTimeString() == chats[lastIndex].time.toTimeString()) {
                lastIndex++
                continue
            }
        }

        result.add(chats.subList(startIndex, lastIndex))

        startIndex = lastIndex
        firstChat = chats[startIndex]
    }

    result.add(chats.subList(startIndex, lastIndex + 1))

    return result
}