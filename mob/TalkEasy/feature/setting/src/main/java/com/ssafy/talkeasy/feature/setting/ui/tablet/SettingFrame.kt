package com.ssafy.talkeasy.feature.setting.ui.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold32
import com.ssafy.talkeasy.feature.setting.R

@Composable
fun SettingFrame() {
}

@Composable
fun SettingTopBar() {
    Row(modifier = Modifier.padding(top = 18.dp, bottom = 22.dp, start = 24.dp)) {
        IconButton(modifier = Modifier.size(36.dp), onClick = { }) {
            Image(
                painter = painterResource(id = drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.image_arrow_back)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = "설정", color = md_theme_light_onBackground, style = textStyleBold32)
    }
}