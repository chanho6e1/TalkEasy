package com.ssafy.talkeasy.feature.follow.ui.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem
import com.ssafy.talkeasy.feature.common.component.NoContentLogoMessage
import com.ssafy.talkeasy.feature.common.component.ProtectorHeader
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.toTimeString
import com.ssafy.talkeasy.feature.follow.R

@Composable
fun MyNotificationListRoute(
    modifier: Modifier = Modifier,
) {
    val myNotificationList = remember {
        arrayListOf<MyNotificationItem>()
    }
    MyNotificationListScreen(modifier = modifier, notificationList = myNotificationList)
}

@Preview(showBackground = true)
@Composable
fun MyNotificationListScreen(
    modifier: Modifier = Modifier,
    notificationList: List<MyNotificationItem> = arrayListOf(),
) {
    Column(modifier = modifier.fillMaxSize()) {
        ProtectorHeader(title = stringResource(id = R.string.title_my_notification_list))
        MyNotificationListContent()
    }
}

@Composable
fun MyNotificationListContent(
    modifier: Modifier = Modifier,
    notificationList: List<MyNotificationItem> = arrayListOf(),
) {
    if (notificationList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .padding(start = 18.dp, end = 18.dp, top = 6.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(items = notificationList) { index, item ->
                MyNotificationListItem(item = item)
            }
        }
    } else {
        NoContentLogoMessage(
            message = stringResource(id = R.string.content_no_notification),
            textStyle = typography.titleSmall,
            width = 156,
            height = 72,
            betweenValue = 20
        )
    }
}

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
                painter = painterResource(id = R.drawable.ic_siren_circle),
                contentDescription = stringResource(id = R.string.image_siren_circle)
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
                    text = item.message,
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.Bold
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