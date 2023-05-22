package com.ssafy.talkeasy.feature.follow.ui.tablet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ssafy.talkeasy.feature.common.ui.theme.harp
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun ManageFollowFrame(onDismissListener: () -> Unit) {
    Dialog(onDismissRequest = { onDismissListener() }) {
        val options = listOf("주보호자 설정", "별명 설정", "위치 정보 제공 해제", "친구 삭제", "위치 정보 제공 설정")

        Card(
            modifier = Modifier
                .width(230.dp)
                .wrapContentHeight(),
            shape = shapes.medium,
            colors = CardDefaults.cardColors(containerColor = md_theme_light_background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(horizontal = 28.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count = 7) { index ->
                    if (index % 2 == 0) {
                        val color: Color =
                            if (index == 6) md_theme_light_error else md_theme_light_onBackground
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { },
                            text = options[index / 2],
                            style = typography.titleSmall,
                            color = color,
                            textAlign = TextAlign.Start
                        )
                    } else {
                        Divider(thickness = 1.dp, color = harp)
                    }
                }
            }
        }
    }
}