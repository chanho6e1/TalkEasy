package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem
import com.ssafy.talkeasy.core.domain.entity.response.NotificationType
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.toTimeString

@Composable
fun MyNotificationListItem(
    modifier: Modifier = Modifier,
    item: MyNotificationItem,
    onItemClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onItemClicked() }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier.size(60.dp),
                contentDescription = stringResource(
                    id = when (item.type) {
                        NotificationType.LOCATION -> R.string.image_siren_circle
                        else -> R.string.image_location_circle
                    }
                ),
                painter = painterResource(
                    id = when (item.type) {
                        NotificationType.LOCATION -> R.drawable.ic_location_circle
                        else -> R.drawable.ic_siren_circle
                    }
                )
            )

            Column(
                modifier = Modifier
                    .padding(
                        start = 15.dp,
                        end = 18.dp,
                        top = 18.dp,
                        bottom = 18.dp
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = String.format(
                        stringResource(
                            id = when (item.type) {
                                NotificationType.LOCATION ->
                                    R.string.content_notification_request_location
                                NotificationType.SOS_REQUEST ->
                                    R.string.content_notification_request_sos
                                NotificationType.SOS_RESPONSE ->
                                    R.string.content_notification_response_location
                            }
                        ),
                        item.data
                    ),
                    style = typography.bodyLarge
                )

                Text(
                    modifier = modifier,
                    color = delta,
                    text = item.time.toTimeString(),
                    style = typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(black_squeeze)
        )
    }
}