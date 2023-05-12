package com.ssafy.talkeasy.feature.chat.ui.tablet.balloon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.cabbage_pont
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_errorContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onError
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatDirection
import com.ssafy.talkeasy.feature.common.util.LocationStatus
import com.ssafy.talkeasy.feature.common.util.ChatType
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
fun ChatBalloon(chatDirection: ChatDirection, chat: Chat, isLastMessage: Boolean) {
    val color: Color = if (chat.type == 2) {
        md_theme_light_errorContainer
    } else {
        when (chatDirection) {
            ChatDirection.PARTNER -> {
                md_theme_light_secondaryContainer
            }

            ChatDirection.ME -> {
                seed
            }
        }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        if (chatDirection == ChatDirection.ME) {
            Column(
                modifier = Modifier.align(Alignment.Bottom),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    color = seed,
                    style = typography.labelMedium,
                    text = if (chat.readCount == 0) "" else chat.readCount.toString()
                )

                if (isLastMessage) {
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        color = cabbage_pont,
                        style = typography.labelMedium,
                        text = chat.time.toTimeString()
                    )
                }
            }
        }

        Card(shape = shapes.extraSmall, colors = CardDefaults.cardColors(color)) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .widthIn(max = 214.dp),
                contentAlignment = Alignment.Center
            ) {
                when (chat.type) {
                    ChatType.MSG.ordinal -> Message(message = chat.message)
                    ChatType.LOCATION.ordinal -> Location(
                        message = chat.message,
                        status = chat.status!!
                    )

                    ChatType.SOS.ordinal -> SOS(chatDirection = chatDirection, message = chat.message)
                }
            }
        }

        if (chatDirection == ChatDirection.PARTNER) {
            Column(
                modifier = Modifier.align(Alignment.Bottom),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    color = seed,
                    style = typography.labelMedium,
                    text = if (chat.readCount == 0) "" else chat.readCount.toString()
                )
                if (isLastMessage) {
                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        color = cabbage_pont,
                        style = typography.labelMedium,
                        text = chat.time.toTimeString()
                    )
                }
            }
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
        LocationStatus.REQUEST.ordinal -> {
            imageId = drawable.ic_location_request
            contentDescription = stringResource(R.string.image_request_location)
        }

        LocationStatus.RESULT.ordinal -> {
            imageId = drawable.ic_location_request
            contentDescription = stringResource(R.string.image_request_location)
        }

        LocationStatus.REJECT.ordinal -> {
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

@Composable
fun SOS(chatDirection: ChatDirection, message: String) {
    Column(
        modifier = Modifier.padding(horizontal = 2.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(42.dp),
            painter = painterResource(id = drawable.ic_sos),
            contentDescription = stringResource(R.string.image_sos)
        )

        Text(style = typography.bodyMedium, text = message)

        when (chatDirection) {
            ChatDirection.PARTNER -> {
                TextButton(
                    shape = shapes.extraSmall,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = md_theme_light_error,
                        contentColor = md_theme_light_onError
                    ),
                    contentPadding = PaddingValues(horizontal = 62.dp, vertical = 8.dp),
                    onClick = { }
                ) {
                    Text(
                        style = typography.labelMedium,
                        text = stringResource(R.string.content_button_browse_location)
                    )
                }
            }

            ChatDirection.ME -> {
                TextButton(
                    modifier = Modifier.height(0.dp),
                    shape = shapes.extraSmall,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = md_theme_light_error,
                        contentColor = md_theme_light_onError
                    ),
                    contentPadding = PaddingValues(horizontal = 62.dp, vertical = 8.dp),
                    onClick = { }
                ) {
                    Text(
                        style = typography.labelMedium,
                        text = stringResource(R.string.content_button_browse_location)
                    )
                }
            }
        }
    }
}