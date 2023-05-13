package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.chat.ui.tablet.balloon.MyChat
import com.ssafy.talkeasy.feature.chat.ui.tablet.balloon.PartnerChat
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.component.NoContentLogoMessage
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal14Vertical
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.common.util.toTimeString

@Composable
fun ConstraintLayoutScope.ChatRoomBox(
    chatRoomRef: ConstrainedLayoutReference,
    aacRef: ConstrainedLayoutReference,
    chatPartnerRef: ConstrainedLayoutReference,
    isOpened: Boolean,
    chatMode: ChatMode,
    chatPartner: Follow?,
    marginLeft: Dp = 20.dp,
    chatViewModel: ChatViewModel = hiltViewModel(),
) {
    val chats by chatViewModel.chats.collectAsState()
    val (offset, setOffset) = remember {
        mutableStateOf(1)
    }

    if (isOpened) {
        Box(
            modifier = Modifier
                .constrainAs(chatRoomRef) {
                    top.linkTo(aacRef.top)
                    bottom.linkTo(parent.bottom, margin = 18.dp)
                    start.linkTo(parent.start, margin = marginLeft)
                    end.linkTo(chatPartnerRef.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(color = green_white, shape = shapes.extraSmall)
        ) {
            if (chatPartner == null || chatMode == ChatMode.TTS) {
                NoContentLogoMessage(
                    message = stringResource(id = R.string.content_no_content_tts),
                    textStyle = typography.titleMedium,
                    width = 156,
                    height = 72,
                    betweenValue = 20
                )
            } else if (chats.isNullOrEmpty()) {
                NoContentLogoMessage(
                    message = stringResource(id = R.string.content_no_chat),
                    textStyle = typography.titleMedium,
                    width = 156,
                    height = 72,
                    betweenValue = 20
                )
            } else {
                chatViewModel.getChatHistory(chatPartner.roomId, offset, 50)
                chats?.let { ChatContent(chatPartner = chatPartner, chats = it) }
            }
        }
    } else {
        Box(
            modifier = Modifier.constrainAs(chatRoomRef) {
                top.linkTo(chatPartnerRef.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
        )
    }
}

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