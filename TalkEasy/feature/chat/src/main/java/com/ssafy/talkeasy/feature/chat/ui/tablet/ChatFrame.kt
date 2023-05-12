package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.chat.ChatViewModel
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode

@Composable
fun ChatFrame(
    chatPartner: Follow,
    chats: List<Chat>,
    chatViewModel: ChatViewModel = hiltViewModel()
) {
    val chatMode by chatViewModel.chatMode.collectAsState()

    ConstraintLayout {
        val (profile, chatRoom) = createRefs()

        Box(
            modifier = Modifier.constrainAs(profile) {
                start.linkTo(parent.start, margin = 24.dp)
                top.linkTo(parent.top, margin = 26.dp)
            }
        ) {
            ChatPartner(chatMode = chatMode, chatPartner = chatPartner)
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