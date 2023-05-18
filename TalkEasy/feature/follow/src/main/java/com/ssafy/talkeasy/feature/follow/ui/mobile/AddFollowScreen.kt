package com.ssafy.talkeasy.feature.follow.ui.mobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.core.domain.entity.AddFollowDetailInfo
import com.ssafy.talkeasy.feature.common.component.CustomTextField
import com.ssafy.talkeasy.feature.common.component.NoLabelTextField
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.component.ProtectorHeader
import com.ssafy.talkeasy.feature.common.component.WideSeedButton
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.cabbage_pont
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.green_white
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_primary
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_secondaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import com.ssafy.talkeasy.feature.follow.R
import java.time.LocalDateTime

@Composable
internal fun AddFollowDetailRoute(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    popBackStack: () -> Unit,
    followViewModel: FollowViewModel = hiltViewModel(navBackStackEntry),
) {
    val addFollowDetailInfo by followViewModel.addFollowInfo.collectAsState()

    AddFollowDetailScreen(
        modifier = modifier,
        addFollowDetailInfo = addFollowDetailInfo,
        requestFollow = followViewModel::requestFollow,
        popBackStack = popBackStack
    )
}

@Composable
internal fun AddFollowDetailScreen(
    modifier: Modifier = Modifier,
    addFollowDetailInfo: AddFollowDetailInfo? = null,
    requestFollow: (String, String) -> Unit,
    popBackStack: () -> Unit,
) {
    val (dialogState: Boolean, setDialogState: (Boolean) -> Unit) = remember {
        mutableStateOf(false)
    }
    val (detailContent: String, setDetailContent: (String) -> Unit) = remember {
        mutableStateOf("")
    }

    AddNotificationDialog(
        modifier = modifier,
        visible = dialogState,
        onDismissRequest = { setDialogState(!dialogState) },
        onClickedAddNotification = {
            setDialogState(!dialogState)
        }
    )
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProtectorHeader(
                modifier = modifier,
                title = stringResource(id = R.string.title_add_follow)
            )

            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 34.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                addFollowDetailInfo?.let {
                    AddFollowDetailContent(
                        modifier = modifier,
                        detailContent = detailContent,
                        setDetailContent = setDetailContent,
                        onClickedAddButton = {
                            setDialogState(!dialogState)
                        },
                        onItemClicked = { setDialogState(!dialogState) },
                        addFollowDetailInfo = addFollowDetailInfo
                    )
                }
            }
            WideSeedButton(
                modifier = modifier.padding(horizontal = 34.dp, vertical = 10.dp),
                onClicked = {
                    addFollowDetailInfo?.let {
                        requestFollow(addFollowDetailInfo.userId, detailContent)
                        popBackStack()
                    }
                },
                text = stringResource(id = R.string.title_add_follow),
                textStyle = typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNotificationDialog(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    onDismissRequest: () -> Unit = {},
    onClickedAddNotification: () -> Unit = {},
) {
    val (notificationMemo: String, setNotificationMemoName: (String) -> Unit) = remember {
        mutableStateOf("")
    }
    val timePickerHour by remember { mutableStateOf(LocalDateTime.now().hour) }
    val timePickerMin by remember { mutableStateOf(LocalDateTime.now().minute) }
    val timePickerColors = TimePickerDefaults.colors(
        periodSelectorBorderColor = delta,
        periodSelectorSelectedContainerColor = md_theme_light_secondaryContainer,
        periodSelectorUnselectedContainerColor = black_squeeze,
        periodSelectorSelectedContentColor = md_theme_light_primary,
        periodSelectorUnselectedContentColor = cabbage_pont,
        timeSelectorSelectedContainerColor = seed,
        timeSelectorUnselectedContainerColor = black_squeeze,
        timeSelectorSelectedContentColor = md_theme_light_primary,
        timeSelectorUnselectedContentColor = md_theme_light_onBackground
    )

    val timePickerState = remember {
        TimePickerState(
            initialHour = timePickerHour,
            initialMinute = timePickerMin,
            is24Hour = false
        )
    }

    if (visible) {
        Dialog(
            onDismissRequest = { onDismissRequest() }
        ) {
            Surface(
                modifier = modifier
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth(),
                color = md_theme_light_background
            ) {
                LazyColumn(
                    modifier = Modifier,
                    contentPadding = PaddingValues(18.dp),
                    verticalArrangement = Arrangement.spacedBy(30.dp)
                ) {
                    item {
                        Text(
                            text = stringResource(id = R.string.title_notification),
                            style = typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    item {
                        TimeInput(
                            state = timePickerState,
                            colors = timePickerColors
                        )
                    }

                    item {
                        CustomTextField(
                            value = notificationMemo,
                            onValueChange = setNotificationMemoName,
                            textStyle = typography.bodyLarge,
                            label = stringResource(id = R.string.content_memo)
                        )
                    }

                    item {
                        WideSeedButton(
                            onClicked = { onClickedAddNotification() },
                            text = stringResource(id = R.string.content_add_notification),
                            textStyle = typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun AddFollowDetailContent(
    modifier: Modifier = Modifier,
    detailContent: String,
    setDetailContent: (String) -> Unit,
    onClickedAddButton: () -> Unit = {},
    onItemClicked: () -> Unit = {},
    addFollowDetailInfo: AddFollowDetailInfo,
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
                    profileUrl = addFollowDetailInfo.imageUrl,
                    size = 116,
                    chatMode = ChatMode.CHAT
                )
            }
        }

        item {
            AddFollowDetailStringContent(
                title = stringResource(id = R.string.title_name),
                content = addFollowDetailInfo.userName
            )
        }

        item {
            AddFollowDetailStringContent(
                title = stringResource(id = R.string.title_gender),
                content = if (addFollowDetailInfo.gender == 0) "남성" else "여성"
            )
        }

        item {
            AddFollowDetailStringContent(
                title = stringResource(id = R.string.title_birth_date),
                content = addFollowDetailInfo.birthDate
            )
        }

        item { AddFollowDetailSignificant(text = detailContent, setText = setDetailContent) }

        item {
            AddFollowDetailNotification(
                modifier = modifier,
                onClickedAddButton = onClickedAddButton,
                onItemClicked = onItemClicked
            )
        }
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