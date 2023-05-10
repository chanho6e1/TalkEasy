package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
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
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun ChatPartnerChatItemHead(memberName: String, nickname: String = "엄마", message: String = "") {
    Box {
        ChatPartnerProfile(memberName, nickname)

        ChatPartnerChatBalloonHead(start = 37, top = 19, message = message)
    }
}

@Composable
fun ChatPartnerProfile(memberName: String, nickname: String) {
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
fun ChatPartnerChatBalloonHead(start: Int = 0, top: Int = 0, message: String) {
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
                    .widthIn(max = 242.dp),
                style = typography.bodyMedium,
                text = message
            )
        }
    }
}

@Composable
fun ChatPartnerChatBalloonTail(message: String = "") {
    Card(
        modifier = Modifier.wrapContentSize(),
        shape = shapes.extraSmall,
        colors = CardDefaults.cardColors(md_theme_light_secondaryContainer)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 10.dp)
                .widthIn(max = 242.dp),
            style = typography.bodyMedium,
            text = message
        )
    }
}