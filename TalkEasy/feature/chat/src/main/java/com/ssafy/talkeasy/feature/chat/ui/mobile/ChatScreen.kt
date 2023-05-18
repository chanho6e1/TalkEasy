package com.ssafy.talkeasy.feature.chat.ui.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.chat.ChatViewModel
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.chat.ui.tablet.MyChat
import com.ssafy.talkeasy.feature.chat.ui.tablet.PartnerChat
import com.ssafy.talkeasy.feature.chat.ui.tablet.sublistChat
import com.ssafy.talkeasy.feature.common.component.NoContentLogoMessage
import com.ssafy.talkeasy.feature.common.component.NoLabelTextField
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.OnBottomReached
import com.ssafy.talkeasy.feature.common.util.OnTopReached
import com.ssafy.talkeasy.feature.follow.FollowViewModel

@Composable
fun ChatRouteProtector(
    onClickedLocationOpenRequest: () -> Unit = {},
    onClickedInfoDetail: () -> Unit = {},
    popBackStack: () -> Unit,
    navBackStackEntry: NavBackStackEntry,
    followListViewModel: FollowViewModel = hiltViewModel(navBackStackEntry),
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val chats by chatViewModel.chats.collectAsState()
    val newChat by chatViewModel.newChat.collectAsState()
    val chatsTotalPage by chatViewModel.chatsTotalPage.collectAsState()
    val selectFollow by followListViewModel.selectFollow.collectAsState()
    val memberInfo by followListViewModel.memberInfo.collectAsState()
    var isClickedSendButton by remember {
        mutableStateOf(false)
    }
    val (text: String, setText: (String) -> Unit) = remember { mutableStateOf("") }
    var offset by remember { mutableStateOf(1) }

    // chats 초기화
    LaunchedEffect(Unit) {
        selectFollow?.let { follow -> chatViewModel.getChatHistory(follow.roomId, offset, 50) }
    }

    // 메시지 구독
    DisposableEffect(Unit) {
        if (selectFollow != null && memberInfo != null) {
            chatViewModel.receiveChatMessage(selectFollow!!.roomId, memberInfo!!.userId)
        }

        // 메시지 구독 취소
        onDispose {
            chatViewModel.stopReceiveMessage()
        }
    }

    // 메시지 읽음 처리
    LaunchedEffect(newChat) {
        if (selectFollow != null && memberInfo != null && chats.isNotEmpty()) {
            chatViewModel.readChatMessage(
                readTime = chats.last().time,
                roomId = selectFollow!!.roomId,
                readUserId = memberInfo!!.userId
            )
        }
    }

    selectFollow?.let { follow ->
        ChatScreen(
            text = text,
            setText = setText,
            chats = chats,
            chatPartner = follow,
            offset = offset,
            onClickedLocationOpenRequest = onClickedLocationOpenRequest,
            onClickedInfoDetail = onClickedInfoDetail,
            onSendButtonClick = {
                chatViewModel.sendChatMessage(
                    toUserId = follow.userId,
                    roomId = follow.roomId,
                    msg = text,
                    fromUserId = memberInfo!!.userId,
                    type = 0
                )
                setText("")
                isClickedSendButton = !isClickedSendButton
            },
            OnTopReached = {
                if (offset < chatsTotalPage) {
                    offset += 1
                    chatViewModel.loadMoreChats(follow.roomId, offset, 50)
                }
            },
            isClickedSendButton = isClickedSendButton,
            popBackStack = popBackStack
        )
    }
}

@Composable
fun ChatScreen(
    text: String,
    setText: (String) -> Unit = {},
    chatPartner: Follow,
    offset: Int,
    isClickedSendButton: Boolean,
    chats: List<Chat>?,
    onClickedLocationOpenRequest: () -> Unit = {},
    onClickedInfoDetail: () -> Unit = {},
    onSendButtonClick: () -> Unit = {},
    OnTopReached: () -> Unit,
    popBackStack: () -> Unit,
) {
    Scaffold(
        topBar = {
            ChatHeader(
                chatPartner = chatPartner,
                onClickedLocationOpenRequest = onClickedLocationOpenRequest,
                onClickedInfoDetail = onClickedInfoDetail,
                popBackStack = popBackStack
            )
        },
        bottomBar = {
            ChatBottom(
                text = text,
                setText = setText,
                onSendButtonClick = onSendButtonClick,
                sendEnable = text.isNotEmpty()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxHeight()
        ) {
            if (chats.isNullOrEmpty()) {
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
                    OnTopReached = OnTopReached
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHeader(
    chatPartner: Follow,
    onClickedLocationOpenRequest: () -> Unit,
    onClickedInfoDetail: () -> Unit,
    popBackStack: () -> Unit,
) {
    var mDisplayMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Profile(profileUrl = chatPartner.imageUrl, size = 40)
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = chatPartner.userName,
                    style = typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = popBackStack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.image_back_icon),
                    tint = md_theme_light_onBackground
                )
            }
        },
        actions = {
            // IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
            //     Icon(
            //         imageVector = Icons.Filled.MoreVert,
            //         contentDescription = stringResource(id = R.string.image_more_info),
            //         tint = md_theme_light_onBackground
            //     )
            // }
            //
            // DropdownMenu(
            //     expanded = mDisplayMenu,
            //     onDismissRequest = { mDisplayMenu = false }
            // ) {
            //     DropdownMenuItem(
            //         onClick = { onClickedInfoDetail() },
            //         text = {
            //             Text(
            //                 text = stringResource(id = R.string.content_info_detail),
            //                 style = typography.bodyLarge
            //             )
            //         }
            //     )
            //     DropdownMenuItem(
            //         onClick = { onClickedLocationOpenRequest() },
            //         text = {
            //             Text(
            //                 text = stringResource(id = R.string.content_location_open_request),
            //                 style = typography.bodyLarge
            //             )
            //         }
            //     )
            // }
        }
    )
}

@Composable
fun ChatContent(
    isClickedSendButton: Boolean,
    chatPartner: Follow,
    chats: List<Chat>,
    offset: Int,
    OnTopReached: () -> Unit,
) {
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
        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        state = scrollState,
        reverseLayout = true
    ) {
        items(items = sublistChat(chats.reversed())) {
            if (it[0].fromUserId == chatPartner.userId) {
                PartnerChat(
                    chatPartner = chatPartner,
                    messages = it.reversed()
                )
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

@Composable
fun ChatBottom(
    text: String,
    sendEnable: Boolean,
    setText: (String) -> Unit,
    onSendButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(md_theme_light_secondaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NoLabelTextField(
                modifier = Modifier.weight(1f),
                text = text,
                setText = setText,
                textStyle = typography.bodyLarge,
                label = stringResource(id = R.string.content_chat_hint),
                innerPaddingHorizontal = 14,
                innerPaddingVertical = 14
            )
            IconButton(
                modifier = Modifier.size(51.dp),
                enabled = sendEnable,
                onClick = onSendButtonClick
            ) {
                Image(
                    painter = if (sendEnable) {
                        painterResource(
                            id = com.ssafy.talkeasy.feature.common.R.drawable.ic_send_enable
                        )
                    } else {
                        painterResource(
                            id = com.ssafy.talkeasy.feature.common.R.drawable.ic_send_disable
                        )
                    },
                    contentDescription = stringResource(R.string.image_send_chat)
                )
            }
        }
    }
}