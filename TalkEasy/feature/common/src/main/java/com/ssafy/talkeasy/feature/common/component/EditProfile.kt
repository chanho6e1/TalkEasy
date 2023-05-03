package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.dimens
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EditProfile(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    size: Int = 110,
    textStyle: TextStyle = typography.titleLarge,
    onClick: () -> Unit,
) {
    Box(modifier = modifier.noRippleClickable { onClick() }, contentAlignment = Alignment.Center) {
        GlideImage(
            model = imageUrl.ifEmpty {
                R.drawable.ic_default_profile
            },
            contentDescription = stringResource(id = R.string.ic_default_profile_text),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .size(size.dp)
                .clip(CircleShape)
                .background(dimens)
        )
        Text(
            text = stringResource(id = R.string.content_select),
            color = md_theme_light_background,
            style = textStyle,
            fontWeight = FontWeight.Bold
        )
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}