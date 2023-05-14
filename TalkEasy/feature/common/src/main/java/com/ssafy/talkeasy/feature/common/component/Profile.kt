package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.util.ChatMode

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Profile(profileUrl: String = "", size: Int = 56, chatMode: ChatMode) {
    val model = when (chatMode) {
        ChatMode.TTS -> R.drawable.ic_chat_tts
        ChatMode.CHAT -> profileUrl.ifEmpty { R.drawable.ic_default_profile }
    }

    GlideImage(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        model = model,
        contentDescription = stringResource(id = R.string.image_follow_list_profile)
    )
}