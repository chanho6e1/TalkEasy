package com.ssafy.talkeasy.feature.chat.ui.tablet.balloon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_errorContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.Direction
import com.ssafy.talkeasy.feature.common.util.Status
import com.ssafy.talkeasy.feature.common.util.Type
import com.ssafy.talkeasy.feature.common.util.toTimeString

@Composable
fun PartnerChatItemHead(memberName: String, nickname: String, type: Int) {
    val chatName = if (nickname == "") {
        memberName
    } else {
        String.format(stringResource(R.string.content_name_and_nickname), memberName, nickname)
    }
    val color = if (type == 2) md_theme_light_errorContainer else md_theme_light_secondaryContainer

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
            tint = color
        )
    }
}

@Composable
fun MyChatItemHead(type: Int) {
    val color = if (type == 2) md_theme_light_errorContainer else seed

    Box {
        Icon(
            modifier = Modifier.align(Alignment.TopStart),
            painter = painterResource(id = drawable.bg_chat_balloon_right_head),
            contentDescription = stringResource(R.string.image_the_person_chat_balloon_head),
            tint = color
        )
    }
}

@Composable
fun ChatBalloon(direction: Direction, chat: Chat, isLastMessage: Boolean) {
    val color: Color = if (chat.type == 2) {
        md_theme_light_errorContainer
    } else {
        when (direction) {
            Direction.PARTNER -> {
                md_theme_light_secondaryContainer
            }

            Direction.ME -> {
                seed
            }
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        if (direction == Direction.ME && isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time.toTimeString()
            )
        }

        Card(shape = shapes.extraSmall, colors = CardDefaults.cardColors(color)) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .widthIn(max = 214.dp),
                contentAlignment = Alignment.Center
            ) {
                when (chat.type) {
                    Type.MSG.ordinal -> Message(message = chat.message)
                    Type.LOCATION.ordinal -> Location(
                        message = chat.message,
                        status = chat.status!!
                    )

                    Type.SOS.ordinal -> {}
                }
            }
        }

        if (direction == Direction.PARTNER && isLastMessage) {
            Text(
                modifier = Modifier.align(Alignment.Bottom),
                color = cabbage_pont,
                style = typography.labelMedium,
                text = chat.time.toTimeString()
            )
        }
    }
}

@Composable
fun Message(message: String) {
    Text(style = typography.bodyMedium, text = message)
}

@Composable
fun Location(message: String, status: Int) {
    var imageId = 0
    var contentDescription = ""

    when (status) {
        Status.REQUEST.ordinal -> {
            imageId = drawable.ic_location_request
            contentDescription = stringResource(R.string.image_request_location)
        }

        Status.RESULT.ordinal -> {
            imageId = drawable.ic_location_request
            contentDescription = stringResource(R.string.image_request_location)
        }

        Status.REJECT.ordinal -> {
            imageId = drawable.ic_location_reject
            contentDescription = stringResource(R.string.image_reject_location)
        }
    }

    Row(
        modifier = Modifier.width(130.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = imageId),
            contentDescription = contentDescription
        )

        Text(style = typography.bodyMedium, text = message)
    }
}