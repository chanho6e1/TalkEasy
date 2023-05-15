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
import com.ssafy.talkeasy.feature.follow.FollowViewModel

@Composable
fun ChatRouteProtector(
    onClickedLocationOpenRequest: () -> Unit = {},
    onClickedInfoDetail: () -> Unit = {},
    navBackStackEntry: NavBackStackEntry,
    followListViewModel: FollowViewModel = hiltViewModel(navBackStackEntry),
    chatViewModel: ChatViewModel = hiltViewModel(),
) {
    val chats by chatViewModel.chats.collectAsState()
    val selectFollow by followListViewModel.selectFollow.collectAsState()
    val memberInfo by followListViewModel.memberInfo.collectAsState()
    val (text: String, setText: (String) -> Unit) = remember {
        mutableStateOf("")
    }
    val (offset, setOffset) = remember {
        mutableStateOf(1)
    }

    LaunchedEffect(key1 = selectFollow) {
        selectFollow?.let { chatViewModel.getChatHistory(it.roomId, offset, 50) }
    }

    selectFollow?.let { follow ->
        ChatScreen(
            text = text,
            setText = setText,
            chats = chats,
            chatPartner = follow,
            onClickedLocationOpenRequest = onClickedLocationOpenRequest,
            onClickedInfoDetail = onClickedInfoDetail,
            onSendButtonClick = {
                chatViewModel.sendMessage(
                    toUserId = selectFollow!!.userId,
                    roomId = selectFollow!!.roomId,
                    msg = text,
                    fromUserId = memberInfo!!.userId,
                    type = 0
                )
                setText("")
                chatViewModel.getChatHistory(follow.roomId, offset, 50)
            }
        )
    }
}

@Composable
fun ChatScreen(
    text: String,
    setText: (String) -> Unit = {},
    chatPartner: Follow,
    chats: List<Chat>?,
    onClickedLocationOpenRequest: () -> Unit = {},
    onClickedInfoDetail: () -> Unit = {},
    onSendButtonClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            ChatHeader(
                chatPartner = chatPartner,
                onClickedLocationOpenRequest = onClickedLocationOpenRequest,
                onClickedInfoDetail = onClickedInfoDetail
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
                ChatContent(chatPartner = chatPartner, chats = chats)
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
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.image_back_icon),
                    tint = md_theme_light_onBackground
                )
            }
        },
        actions = {
            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(id = R.string.image_more_info),
                    tint = md_theme_light_onBackground
                )
            }

            DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false }
            ) {
                DropdownMenuItem(
                    onClick = { onClickedInfoDetail() },
                    text = {
                        Text(
                            text = stringResource(id = R.string.content_info_detail),
                            style = typography.bodyLarge
                        )
                    }
                )
                DropdownMenuItem(
                    onClick = { onClickedLocationOpenRequest() },
                    text = {
                        Text(
                            text = stringResource(id = R.string.content_location_open_request),
                            style = typography.bodyLarge
                        )
                    }
                )
            }
        }
    )
}

@Composable
fun ChatContent(chatPartner: Follow, chats: List<Chat>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(items = sublistChat(chats)) {
            if (it[0].fromUserId == chatPartner.userId) {
                PartnerChat(
                    chatPartner = chatPartner,
                    messages = it
                )
            } else {
                MyChat(messages = it)
            }
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