package com.ssafy.talkeasy.feature.chat.ui.tablet.balloon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

enum class Direction {
    PARTNER, ME
}

@Composable
fun PartnerChatItemHead(memberName: String, nickname: String) {
    val chatName = if (nickname == "") {
        memberName
    } else {
        String.format(stringResource(R.string.content_name_and_nickname), memberName, nickname)
    }

    Box {
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

        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 37.dp, top = 24.dp),
            painter = painterResource(id = drawable.bg_chat_balloon_left_head),
            contentDescription = stringResource(R.string.image_the_person_chat_balloon_head),
            tint = md_theme_light_secondaryContainer
        )
    }
}

@Composable
fun MyChatItemHead() {
    Box {
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 37.dp, top = 24.dp),
            painter = painterResource(id = drawable.bg_chat_balloon_right_head),
            contentDescription = stringResource(R.string.image_the_person_chat_balloon_head),
            tint = seed
        )
    }
}

@Composable
fun BaseChatBalloon(direction: Direction, chat: Chat, isLastMessage: Boolean) {
    val color: Color = when (direction) {
        Direction.PARTNER -> {
            md_theme_light_secondaryContainer
        }

        Direction.ME -> {
            seed
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        if (direction == Direction.ME && isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time
            )
        }

        Card(shape = shapes.extraSmall, colors = CardDefaults.cardColors(color)) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .widthIn(max = 214.dp),
                style = typography.bodyMedium,
                text = chat.message
            )
        }

        if (direction == Direction.PARTNER && isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time
            )
        }
    }
}