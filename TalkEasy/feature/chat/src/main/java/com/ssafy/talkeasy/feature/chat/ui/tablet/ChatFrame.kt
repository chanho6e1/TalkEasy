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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.ssafy.talkeasy.feature.chat.ChatViewModel
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.R.string
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

    LaunchedEffect(key1 = chatPartner?.followId, key2 = chatMode) {
        if (chatMode == ChatMode.CHAT && chatPartner != null) {
            chatViewModel.getChatHistory(chatPartner.roomId, offset, 50)
        }
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
            } else if (chats.isEmpty()) {
                NoContentLogoMessage(
                    message = stringResource(id = R.string.content_no_chat),
                    textStyle = typography.titleMedium,
                    width = 156,
                    height = 72,
                    betweenValue = 20
                )
            } else {
                ChatContent(chatPartner = chatPartner, chats = chats)
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
    val chatList = sublistChat(chats)
    val scrollState = rememberLazyListState(initialFirstVisibleItemIndex = chatList.lastIndex)

    LazyColumn(
        modifier = Modifier.padding(11.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        state = scrollState
    ) {
        items(items = chatList) {
            if (it[0].fromUserId == chatPartner.userId) {
                PartnerChat(chatPartner = chatPartner, messages = it)
            } else {
                MyChat(messages = it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatPartner(chatMode: ChatMode, chatPartner: Follow?, onChangeButtonClickListener: () -> Unit) {
    val profileUrl: String
    val memberName: String

    if (chatPartner == null || chatMode == ChatMode.TTS) {
        profileUrl = ""
        memberName = stringResource(id = R.string.content_chat_mode_tts)
    } else {
        profileUrl = chatPartner.imageUrl
        memberName = if (chatPartner.nickName == "") {
            chatPartner.userName
        } else {
            String.format(
                stringResource(string.content_name_and_nickname),
                chatPartner.userName,
                chatPartner.nickName
            )
        }
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Profile(profileUrl = profileUrl, size = 48, chatMode = chatMode)

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            modifier = Modifier.width(203.dp),
            text = memberName,
            color = md_theme_light_onBackground,
            style = textStyleNormal22
        )

        Spacer(modifier = Modifier.width(28.dp))

        Surface(
            shape = shapes.extraSmall,
            color = black_squeeze,
            onClick = { onChangeButtonClickListener() }
        ) {
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

    result.add(chats.subList(startIndex, lastIndex))

    return result
}