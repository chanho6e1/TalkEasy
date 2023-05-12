package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.chat.ChatViewModel
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.chat.ui.tablet.balloon.MyChat
import com.ssafy.talkeasy.feature.chat.ui.tablet.balloon.PartnerChat
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.harp
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal14Vertical
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.common.util.toTimeString

@Composable
@Preview(showBackground = true, widthDp = 1429, heightDp = 857)
fun ChatFrame(
    chatPartner: Follow = Follow(
        userId = "",
        userName = "강은선인데이름이왕왕길어용괜찮아욥",
        followId = "",
        imageUrl = "",
        memo = "",
        mainStatus = true,
        gender = 0,
        age = 31,
        birthDate = "2023-05-04T16:16:38.417705",
        locationStatus = false,
        nickName = "닉네임일이삼사오육칠"
    ),
    chats: List<Chat> = listOf(),
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val chatMode by chatViewModel.chatMode.collectAsState()
    val (isOpened, setIsOpened) = remember {
        mutableStateOf(true)
    }

    ConstraintLayout {
        val (profile, chatRoom, openButton) = createRefs()

        Box(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(profile) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(parent.top, margin = 26.dp)
                }
        ) {
            ChatPartner(chatMode = chatMode, chatPartner = chatPartner)
        }

        Box(
            modifier = Modifier
                .constrainAs(openButton) {
                    start.linkTo(chatRoom.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable { setIsOpened(!isOpened) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .width(32.dp),
                painter = painterResource(id = drawable.bg_trapezoid_secondary_left),
                contentDescription = stringResource(id = R.string.image_open_chat_frame),
            )

            Text(
                text = stringResource(R.string.content_open_chat_room),
                color = md_theme_light_background,
                style = textStyleNormal14Vertical
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(chatRoom) {
                    top.linkTo(profile.bottom, margin = 22.dp)
                    bottom.linkTo(parent.bottom, margin = 18.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(profile.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .background(color = green_white, shape = shapes.extraSmall)
        ) {
            if (chats.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = drawable.bg_talkeasy_logo_verticcal_trans),
                        contentDescription = stringResource(id = R.string.image_app_logo),
                        tint = harp
                    )

                    Text(
                        text = stringResource(id = R.string.content_no_chat),
                        color = harp,
                        style = typography.titleMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(11.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    var startIndex = 0
                    var lastIndex = 0
                    var firstChat = chats[startIndex]

                    while (lastIndex <= chats.lastIndex) {
                        if (firstChat.fromUserId == chats[lastIndex].fromUserId) {
                            if (firstChat.time.toTimeString() == chats[lastIndex].time.toTimeString()) {
                                lastIndex++
                            } else {
                                val slicedChats = chats.subList(startIndex, lastIndex)

                                if (slicedChats[0].fromUserId == chatPartner.userId) {
                                    item {
                                        PartnerChat(
                                            memberName = chatPartner.userName,
                                            nickname = chatPartner.nickName,
                                            messages = slicedChats
                                        )
                                    }
                                } else {
                                    item {
                                        MyChat(messages = slicedChats)
                                    }
                                }

                                startIndex = lastIndex
                                firstChat = chats[startIndex]
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatPartner(chatMode: ChatMode, chatPartner: Follow) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Profile(
            profileUrl = when (chatMode) {
                ChatMode.TTS -> ""
                ChatMode.CHAT -> chatPartner.imageUrl
            },
            size = 48
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            modifier = Modifier.width(203.dp),
            text = chatPartner.userName,
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