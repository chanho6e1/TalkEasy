package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.core.domain.entity.AddFollowDetailInfo
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode

@Composable
fun FollowDetailScreen(
    modifier: Modifier = Modifier,
    title: String,
    buttonContent: String,
    addFollowDetailInfo: AddFollowDetailInfo? = null,
    selectFollowInfo: Follow? = null,
    buttonClickListener: (String) -> Unit,
    popBackStack: () -> Unit,
) {
    val (detailContent: String, setDetailContent: (String) -> Unit) = remember {
        mutableStateOf(selectFollowInfo?.memo ?: "")
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProtectorHeader(
                modifier = modifier,
                title = title,
                onClickedBackButton = popBackStack
            )

            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 34.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddFollowDetailContent(
                    modifier = modifier,
                    detailContent = detailContent,
                    setDetailContent = setDetailContent,
                    addFollowDetailInfo = addFollowDetailInfo,
                    selectFollowInfo = selectFollowInfo
                )
            }
            WideSeedButton(
                modifier = modifier.padding(horizontal = 34.dp, vertical = 10.dp),
                onClicked = {
                    addFollowDetailInfo?.let {
                        buttonClickListener(detailContent)
                        popBackStack()
                    }
                },
                text = buttonContent,
                textStyle = typography.bodyLarge
            )
        }
    }
}

@Composable
internal fun AddFollowDetailContent(
    modifier: Modifier = Modifier,
    detailContent: String,
    setDetailContent: (String) -> Unit,
    addFollowDetailInfo: AddFollowDetailInfo?,
    selectFollowInfo: Follow?,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        userScrollEnabled = true
    ) {
        item {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Profile(
                    profileUrl = addFollowDetailInfo?.imageUrl ?: selectFollowInfo?.imageUrl ?: "",
                    size = 116,
                    chatMode = ChatMode.CHAT
                )
            }
        }

        item {
            AddFollowDetailStringContent(
                title = stringResource(id = R.string.title_name),
                content = addFollowDetailInfo?.userName ?: selectFollowInfo?.userName ?: ""
            )
        }

        item {
            AddFollowDetailStringContent(
                title = stringResource(id = R.string.title_gender),
                content = if ((addFollowDetailInfo?.gender ?: selectFollowInfo?.gender) == 0) {
                    "남성"
                } else {
                    "여성"
                }
            )
        }

        item {
            AddFollowDetailStringContent(
                title = stringResource(id = R.string.title_birth_date),
                content = addFollowDetailInfo?.birthDate ?: selectFollowInfo?.birthDate ?: ""
            )
        }

        item { AddFollowDetailSignificant(text = detailContent, setText = setDetailContent) }

        /*
                item {
                    AddFollowDetailNotification(
                        modifier = modifier,
                        onClickedAddButton = onClickedAddButton,
                        onItemClicked = onItemClicked
                    )
                }
        */
    }
}

@Composable
fun AddFollowDetailStringContent(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = title, style = typography.titleSmall, fontWeight = FontWeight.Bold)

        Text(
            text = content,
            style = typography.bodyLarge,
            modifier = modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddFollowDetailSignificant(
    modifier: Modifier = Modifier,
    text: String = "",
    setText: (String) -> Unit = {},
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(id = R.string.title_significant),
            style = typography.titleSmall,
            fontWeight = FontWeight.Bold
        )

        NoLabelTextField(
            text = text,
            setText = setText,
            textStyle = typography.bodyLarge,
            label = stringResource(
                id = R.string.content_significant_hint
            ),
            innerPaddingHorizontal = 18,
            innerPaddingVertical = 14
        )
    }
}

/*
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
}*/