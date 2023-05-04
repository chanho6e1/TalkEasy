package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.ui.theme.Typography
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_surfaceVariant
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal30

@Composable
fun AACCardWrap(word: String, color: Color) {
    Surface(shape = shapes.extraSmall, color = color) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            text = word,
            color = md_theme_light_onBackground,
            style = Typography.titleMedium
        )
    }
}

@Composable
fun AACCardSmall(word: String) {
    Surface(
        modifier = Modifier.width(202.dp),
        shape = shapes.extraSmall,
        color = md_theme_light_surfaceVariant
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = word,
            textAlign = TextAlign.Center,
            color = md_theme_light_onBackground,
            style = Typography.titleMedium
        )
    }
}

@Composable
fun AACCardLarge(word: String) {
    Surface(
        modifier = Modifier.width(352.dp),
        shape = shapes.extraSmall,
        color = md_theme_light_surfaceVariant
    ) {
        Text(
            modifier = Modifier.padding(vertical = 18.dp),
            text = word,
            textAlign = TextAlign.Center,
            color = md_theme_light_onBackground,
            style = textStyleNormal30
        )
    }
}

@Composable
@Preview
fun PreviewAACCardSmall() {
    AACCardSmall(word = "119에 전화해주세요")
}

@Composable
@Preview
fun PreviewAACCardLarge() {
    AACCardLarge(word = "119에 전화해주세요")
}