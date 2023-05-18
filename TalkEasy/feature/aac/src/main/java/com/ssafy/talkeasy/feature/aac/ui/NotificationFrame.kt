package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem
import com.ssafy.talkeasy.feature.aac.R
import com.ssafy.talkeasy.feature.common.component.MyNotificationListItem
import com.ssafy.talkeasy.feature.common.component.NoContentLogoMessage
import com.ssafy.talkeasy.feature.common.component.noRippleClickable
import com.ssafy.talkeasy.feature.common.ui.theme.gradientEnd
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun NotificationFrame(
    notifications: List<MyNotificationItem>,
    closeNotificationDialog: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            gradientEnd
                        )
                    )
                )
                .noRippleClickable { closeNotificationDialog() }
        )

        Surface(
            modifier = Modifier
                .padding(top = 85.dp, end = 90.dp)
                .width(425.dp)
                .heightIn(300.dp, 540.dp)
                .align(Alignment.TopEnd),
            color = md_theme_light_background,
            shape = shapes.medium
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    text = stringResource(R.string.title_notification),
                    style = textStyleBold22
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (notifications.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        NoContentLogoMessage(
                            message = stringResource(R.string.content_no_notification),
                            textStyle = typography.titleMedium,
                            width = 156,
                            height = 72,
                            betweenValue = 20
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                    ) {
                        itemsIndexed(items = notifications) { index, notification ->
                            val isLastItem = index == notifications.lastIndex

                            MyNotificationListItem(item = notification, isLastItem = isLastItem)
                        }
                    }
                }
            }
        }
    }
}