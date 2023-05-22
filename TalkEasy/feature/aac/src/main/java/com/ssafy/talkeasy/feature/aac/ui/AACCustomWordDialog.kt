package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.aac.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondary
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun AACCustomWordDialog() {
    Surface(color = md_theme_light_background, shape = shapes.medium) {
        Column(
            modifier = Modifier
                .width(500.dp)
                .padding(vertical = 30.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "추가한 사용자 정의 단어",
                style = textStyleBold22
            )

            // AACCardSmall(word = AACWord(0, "안녕하세요", 0))
            // AACCardSmall(word = AACWord(0, "안녕하세요", 0))
            // AACCardSmall(word = AACWord(0, "안녕하세요", 0))
        }
    }
}

@Composable
fun AACCustomWordDialogButton(showCustomWordDialog: () -> Unit) {
    Row(
        modifier = Modifier.clickable { showCustomWordDialog() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = drawable.ic_plus),
            contentDescription = stringResource(R.string.image_show_custom_word_dialog_button),
            tint = md_theme_light_secondary
        )

        Text(
            text = stringResource(R.string.content_show_custom_word_dialog_button),
            style = typography.titleSmall
        )
    }
}