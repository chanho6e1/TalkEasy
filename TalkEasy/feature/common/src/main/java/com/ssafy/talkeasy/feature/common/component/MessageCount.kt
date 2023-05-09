package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.sunset_orange
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Preview(showBackground = true)
@Composable
fun MessageCount(modifier: Modifier = Modifier, contentText: Int = 55) {
    val isMax = remember { contentText >= 99 }
    if (isMax) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(sunset_orange)
            )

            Text(
                modifier = modifier,
                color = md_theme_light_background,
                text = contentText.toString(),
                style = typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    } else {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(sunset_orange),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier
                    .padding(horizontal = 4.dp),
                color = md_theme_light_background,
                text = "+99",
                style = typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}