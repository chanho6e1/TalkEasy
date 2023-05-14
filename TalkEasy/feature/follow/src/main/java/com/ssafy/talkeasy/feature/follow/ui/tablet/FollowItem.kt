package com.ssafy.talkeasy.feature.follow.ui.tablet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.common.R.string
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_outline
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.follow.R

@Composable
fun TTSModeFollow() {
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
                text = stringResource(R.string.title_chat_mode_tts),
                color = md_theme_light_onBackground,
                style = typography.bodyLarge
            )

            Text(
                modifier = Modifier.alignByBaseline(),
                text = stringResource(R.string.content_message_not_send),
                color = delta,
                style = typography.bodySmall
            )
        }

        Box(modifier = Modifier.padding(horizontal = 8.dp)) {
            FollowItem(chatMode = ChatMode.TTS)
        }
    }
}

@Composable
fun ChatModeFollow(followList: List<Follow>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.title_chat_mode_chat),
            color = md_theme_light_onBackground,
            style = typography.bodyLarge
        )

        FollowList(followList = followList)
    }
}

@Composable
fun FollowList(followList: List<Follow>) {
    var isFirstNormalFollow = false

    LazyColumn(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = followList) { follow ->
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (follow.mainStatus) {
                    isFirstNormalFollow = true
                } else if (isFirstNormalFollow) {
                    isFirstNormalFollow = false
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.5.dp),
                        color = black_squeeze
                    ) {}
                } else {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = black_squeeze
                    ) {}
                }

                FollowItem(chatMode = ChatMode.CHAT, follow = follow)
            }
        }
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