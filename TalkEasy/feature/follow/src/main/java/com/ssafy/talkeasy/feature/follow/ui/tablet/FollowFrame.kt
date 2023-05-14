package com.ssafy.talkeasy.feature.follow.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.R.string
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onPrimaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_outline
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.follow.R

@Composable
fun FollowFrame(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .width(500.dp)
                .heightIn(min = 300.dp, max = 640.dp)
                .wrapContentHeight(),
            shape = shapes.medium,
            colors = CardDefaults.cardColors(containerColor = md_theme_light_background)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar(onCloseButtonClickListener = onDismiss) {}

                Spacer(modifier = Modifier.height(20.dp))

                TTSMode()

            }
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onCloseButtonClickListener: () -> Unit,
    onAddFollowButtonClickListener: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(20.dp),
            onClick = { onCloseButtonClickListener() }
        ) {
            Icon(
                painter = painterResource(id = drawable.ic_close),
                contentDescription = stringResource(R.string.image_close_follow_dialog)
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.title_chat_partner),
            color = md_theme_light_onBackground,
            textAlign = TextAlign.Center,
            style = textStyleBold22
        )

        TextButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            shape = shapes.extraSmall,
            colors = ButtonDefaults.buttonColors(
                contentColor = md_theme_light_onPrimaryContainer,
                containerColor = seed
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            onClick = { onAddFollowButtonClickListener() }
        ) {
            Text(
                text = stringResource(R.string.content_add_follow),
                style = typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TTSMode() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.alignByBaseline(),
                text = "음성 모드",
                color = md_theme_light_onBackground,
                style = typography.bodyLarge
            )

            Text(
                modifier = Modifier.alignByBaseline(),
                text = "(채팅 미 전송)",
                color = delta,
                style = typography.bodySmall
            )
        }

        FollowItem(chatMode = ChatMode.TTS)
    }
}

@Composable
fun FollowItem(chatMode: ChatMode, follow: Follow? = null) {
    val profileUrl: String
    val memberName: String

    if (follow == null || chatMode == ChatMode.TTS) {
        profileUrl = ""
        memberName = stringResource(id = R.string.content_chat_mode_tts)
    } else {
        profileUrl = follow.imageUrl
        memberName = if (follow.nickName == "") {
            follow.userName
        } else {
            String.format(
                stringResource(string.content_name_and_nickname),
                follow.userName,
                follow.nickName
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (follow != null && follow.mainStatus) {
            Text(
                text = stringResource(R.string.title_main_follow),
                color = md_theme_light_outline,
                style = typography.bodyMedium
            )
        }

        Row(
            modifier = Modifier.padding(start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Profile(profileUrl = profileUrl, chatMode = chatMode)

            Text(
                text = memberName,
                style = typography.bodyLarge,
                color = md_theme_light_onBackground
            )
        }
    }
}