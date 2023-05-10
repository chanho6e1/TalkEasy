package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.ssafy.talkeasy.feature.common.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Profile(profileUrl: String = "", size: Int = 56) {
    GlideImage(
        modifier = Modifier.size(size.dp),
        model = profileUrl.ifEmpty { R.drawable.ic_default_profile },
        contentDescription = stringResource(id = R.string.image_follow_list_profile)
    )
}