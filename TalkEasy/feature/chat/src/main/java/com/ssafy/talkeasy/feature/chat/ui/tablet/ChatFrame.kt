package com.ssafy.talkeasy.feature.chat.ui.tablet

import android.util.Log
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.core.domain.entity.response.MemberInfo
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
import com.ssafy.talkeasy.feature.common.util.OnBottomReached
import com.ssafy.talkeasy.feature.common.util.OnTopReached
import com.ssafy.talkeasy.feature.common.util.toTimeString

@Composable
fun ConstraintLayoutScope.ChatRoomBox(
    chatRoomRef: ConstrainedLayoutReference,
    aacRef: ConstrainedLayoutReference,
    chatPartnerRef: ConstrainedLayoutReference,
    isOpened: Boolean,
    chatMode: ChatMode,
    chatPartner: Follow?,
    memberInfo: MemberInfo?,
    marginLeft: Dp = 20.dp,
    isClickedSendButton: Boolean,
    chatViewModel: ChatViewModel = viewModel(),
) {
    val chats by chatViewModel.chats.collectAsState()
    val newChat by chatViewModel.newChat.collectAsState()
    val chatsTotalPage by chatViewModel.chatsTotalPage.collectAsState()
    var offset by remember { mutableStateOf(1) }

    // chat 초기화
    LaunchedEffect(key1 = chatPartner?.followId, key2 = chatMode) {
        if (chatMode == ChatMode.CHAT && chatPartner != null) {
            chatViewModel.getChatHistory(chatPartner.roomId, offset, 50)
        }
    }

    // 메시지 구독
    DisposableEffect(key1 = chatPartner?.followId, key2 = chatMode) {
        if (chatMode == ChatMode.CHAT && chatPartner != null && memberInfo != null) {
            chatViewModel.receiveChatMessage(chatPartner.roomId, memberInfo.userId)
        }
        Log.d(
            "TAG",
            "ChatRoomBox: receiveChatMessage chatPartner :$chatPartner, memberInfo : $memberInfo"
        )

        // 메시지 구독 취소
        onDispose {
            chatViewModel.stopReceiveMessage()
            Log.d("TAG", "ChatRoomBox: stopReceiveMessage")
        }
    }

    // 메시지 읽음 처리
    LaunchedEffect(newChat) {
        if (newChat != null && chatMode == ChatMode.CHAT &&
            chatPartner != null && memberInfo != null && chats.isNotEmpty()
        ) {
            chatViewModel.readChatMessage(
                readTime = chats.last().time,
                roomId = chatPartner.roomId,
                readUserId = memberInfo.userId
            )
        }
        Log.d(
            "TAG",
            "ChatRoomBox: chatPartner :$chatPartner, memberInfo : $memberInfo, chats : $chats"
        )
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
                ChatContent(
                    chatPartner = chatPartner,
                    chats = chats,
                    isClickedSendButton = isClickedSendButton,
                    offset = offset,
                    OnTopReached = {
                        if (offset < chatsTotalPage) {
                            offset += 1
                            chatViewModel.loadMoreChats(chatPartner.roomId, offset, 50)
                        }
                    }
                )
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
fun ChatContent(
    isClickedSendButton: Boolean,
    chatPartner: Follow,
    chats: List<Chat>,
    offset: Int,
    OnTopReached: () -> Unit,
) {
    val chatList = sublistChat(chats.reversed())
    val scrollState = rememberLazyListState()
    var isBottomMode by remember { mutableStateOf(true) }
    var previousScrollPosition by remember { mutableStateOf(0) }
    var previousScrollOffset by remember { mutableStateOf(0) }

    LaunchedEffect(chats) {
        if (isBottomMode) {
            scrollState.scrollToItem(0)
        }
    }

    LaunchedEffect(isClickedSendButton) {
        scrollState.scrollToItem(0)
    }

    LazyColumn(
        modifier = Modifier.padding(11.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        state = scrollState,
        reverseLayout = true
    ) {
        items(items = chatList) {
            if (it[0].fromUserId == chatPartner.userId) {
                PartnerChat(chatPartner = chatPartner, messages = it.reversed())
            } else {
                MyChat(messages = it.reversed())
            }
        }
    }

    scrollState.OnBottomReached() {
        if (chats.isNotEmpty() && !isBottomMode) {
            previousScrollPosition = scrollState.firstVisibleItemIndex
            previousScrollOffset = scrollState.firstVisibleItemScrollOffset
            OnTopReached()
        }
    }

    scrollState.OnTopReached(
        onIsBottom = { isBottomMode = true },
        onIsNotBottom = { isBottomMode = false }
    )

    if (!isBottomMode) {
        LaunchedEffect(key1 = offset) {
            scrollState.scrollToItem(previousScrollPosition, previousScrollOffset)
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