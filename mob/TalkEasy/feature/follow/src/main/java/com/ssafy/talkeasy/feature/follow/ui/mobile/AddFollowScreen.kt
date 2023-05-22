package com.ssafy.talkeasy.feature.follow.ui.mobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.feature.common.component.FollowDetailScreen
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import com.ssafy.talkeasy.feature.follow.R

@Composable
internal fun AddFollowDetailRoute(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    popBackStack: () -> Unit,
    followViewModel: FollowViewModel = hiltViewModel(navBackStackEntry),
) {
    val addFollowDetailInfo by followViewModel.addFollowInfo.collectAsState()

    FollowDetailScreen(
        modifier = modifier,
        title = stringResource(id = R.string.title_add_follow),
        buttonContent = stringResource(id = R.string.title_add_follow),
        addFollowDetailInfo = addFollowDetailInfo,
        buttonClickListener = { memo: String ->
            followViewModel.requestFollow(
                toUserId = addFollowDetailInfo?.userId ?: "",
                memo = memo
            )
        },
        popBackStack = popBackStack
    )
}

@Composable
fun AddFollowDetailNotification(
    modifier: Modifier = Modifier,
    onClickedAddButton: () -> Unit = {},
    onItemClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.title_notification),
                style = typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = modifier.clickable { onClickedAddButton() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = modifier.size(19.dp),
                    painter = painterResource(
                        id = R.drawable.ic_add_follow_plus
                    ),
                    contentDescription = stringResource(id = R.string.image_notification)
                )

                Spacer(modifier = modifier.width(7.dp))

                Text(
                    text = stringResource(id = R.string.content_add_notification),
                    style = typography.bodyLarge
                )
            }
        }

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            repeat(0) {
                AddFollowDetailNotificationItem(onItemClicked = onItemClicked)
            }
        }
    }
}

@Composable
fun AddFollowDetailNotificationItem(
    modifier: Modifier = Modifier,
    memo: String = "",
    timeDivision: String = "",
    time: String = "",
    onItemClicked: () -> Unit = {},
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked()
            },
        color = green_white,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = modifier.padding(18.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = memo, style = typography.labelMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = timeDivision, style = typography.bodyMedium)

                Spacer(modifier = modifier.width(8.dp))

                Text(text = time, style = typography.titleLarge)
            }
        }
    }
}