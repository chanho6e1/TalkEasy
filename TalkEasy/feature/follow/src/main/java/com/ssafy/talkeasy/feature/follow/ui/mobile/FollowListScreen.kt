package com.ssafy.talkeasy.feature.follow.ui.mobile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.ssafy.talkeasy.feature.common.component.MessageCount
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.cabbage_pont
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import com.ssafy.talkeasy.feature.follow.R

@Composable
internal fun FollowListRoute(
    modifier: Modifier = Modifier,
    viewModel: FollowViewModel = hiltViewModel(),
    onClickedAddFollow: () -> Unit = {},
    onClickedNotification: () -> Unit = {},
    onClickedSettings: () -> Unit = {},
) {
    FollowLisScreen(
        modifier = modifier,
        onClickedAddFollow = onClickedAddFollow
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
internal fun FollowLisScreen(
    modifier: Modifier = Modifier,
    onClickedAddFollow: () -> Unit = {},
    onClickedNotification: () -> Unit = {},
    onClickedSettings: () -> Unit = {},
) {
    Column() {
        FollowListHeader(
            modifier = modifier,
            onClickedAddFollow = onClickedAddFollow,
            onClickedNotification = onClickedNotification,
            onClickedSettings = onClickedSettings
        )
        FollowListContent(modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun FollowListHeader(
    modifier: Modifier = Modifier,
    onClickedAddFollow: () -> Unit = {},
    onClickedNotification: () -> Unit = {},
    onClickedSettings: () -> Unit = {},
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Text(
                modifier = modifier.padding(18.dp),
                text = stringResource(id = R.string.title_follow_list),
                style = typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Row(modifier = modifier.padding(end = 18.dp)) {
                IconButton(modifier = modifier, onClick = onClickedAddFollow) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_friend),
                        contentDescription = stringResource(
                            R.string.ic_add_user_text
                        ),
                        modifier = modifier.size(24.dp)
                    )
                }
                IconButton(modifier = modifier, onClick = onClickedNotification) {
                    Icon(
                        painter = painterResource(R.drawable.ic_notification_off),
                        contentDescription = stringResource(
                            R.string.ic_notification_text
                        ),
                        modifier = modifier.size(24.dp)
                    )
                }
                IconButton(modifier = modifier, onClick = onClickedSettings) {
                    Icon(
                        painter = painterResource(R.drawable.ic_settings),
                        contentDescription = stringResource(
                            R.string.ic_settings_text
                        ),
                        modifier = modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FollowListContent(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(count = 5) {
            FollowListItem()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun FollowListItem(
    modifier: Modifier = Modifier,
    profileUrl: String = "",
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Profile(profileUrl, 56)

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "일이삼사오육칠팔구",
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
                Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
                    Text(text = "오전", style = typography.bodySmall, color = delta)
                    Text(text = "11", style = typography.bodySmall, color = delta)
                    Text(text = ":", style = typography.bodySmall, color = delta)
                    Text(text = "56", style = typography.bodySmall, color = delta)
                }
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row() {
                    Text(text = "여성", style = typography.bodyLarge, color = cabbage_pont)
                    Text(text = " | ", style = typography.bodyLarge, color = cabbage_pont)
                    Text(text = "23", style = typography.bodyLarge, color = cabbage_pont)
                    Text(text = "세", style = typography.bodyLarge, color = cabbage_pont)
                }
                MessageCount()
            }
        }
    }
}