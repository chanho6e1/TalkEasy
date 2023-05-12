package com.ssafy.talkeasy.feature.follow.ui.mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.response.MyNotificationItem
import com.ssafy.talkeasy.feature.common.component.MyNotificationListItem
import com.ssafy.talkeasy.feature.common.component.NoContentLogoMessage
import com.ssafy.talkeasy.feature.common.component.ProtectorHeader
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.follow.R

@Composable
fun MyNotificationListRoute(modifier: Modifier = Modifier) {
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