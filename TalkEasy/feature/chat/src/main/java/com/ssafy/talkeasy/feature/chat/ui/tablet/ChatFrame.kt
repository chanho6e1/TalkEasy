package com.ssafy.talkeasy.feature.chat.ui.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ssafy.talkeasy.core.domain.entity.response.Chat
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.harp

@Composable
fun ChatFrame(chats: List<Chat>) {
    if (chats.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier.fillMaxWidth(0.5f),
                painter = painterResource(id = drawable.bg_talkeasy_logo_verticcal_trans),
                contentDescription = stringResource(R.string.image_no_chat),
                tint = harp
            )
        }
    }

    var lastIndex = 0

    while (lastIndex <= chats.lastIndex) {

    }
}