package com.ssafy.talkeasy.feature.follow.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.core.domain.entity.response.Follow
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onPrimaryContainer
import com.ssafy.talkeasy.feature.common.ui.theme.seed
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.common.util.ChatMode
import com.ssafy.talkeasy.feature.follow.FollowViewModel
import com.ssafy.talkeasy.feature.follow.R

@Composable
fun FollowFrame(
    onDismiss: () -> Unit,
    setChatMode: (ChatMode) -> Unit,
    setChatPartner: (Follow) -> Unit,
    followViewModel: FollowViewModel = viewModel(),
) {
    val followList by followViewModel.followList.collectAsState()
    val (isShowManageFollowDialog, setIsShowManageFollowDialog) = remember {
        mutableStateOf(false)
    }

    if (isShowManageFollowDialog) {
        ManageFollowFrame {
            followViewModel.requestFollowList()
            setIsShowManageFollowDialog(false)
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(500.dp)
                .heightIn(min = 300.dp, max = 640.dp)
                .wrapContentHeight(),
            shape = shapes.medium,
            colors = CardDefaults.cardColors(containerColor = md_theme_light_background)
        ) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar(onCloseButtonClickListener = onDismiss)

                Spacer(modifier = Modifier.height(20.dp))

                TTSModeFollow(
                    setChatMode = { chatMode -> setChatMode(chatMode) },
                    closeFollowDialog = onDismiss
                )

                Spacer(modifier = Modifier.height(20.dp))

                ChatModeFollow(
                    followList = followList,
                    setChatMode = { chatMode -> setChatMode(chatMode) },
                    setChatPartner = { chatPartner -> setChatPartner(chatPartner) },
                    showManageFollowDialog = { setIsShowManageFollowDialog(true) },
                    closeFollowDialog = onDismiss
                )
            }
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onCloseButtonClickListener: () -> Unit,
) {
    val (isShowAddFollowDialog, setIsShowAddFollowDialog) = remember {
        mutableStateOf(false)
    }

    if (isShowAddFollowDialog) {
        AddFollowFrame(onDismissListener = { setIsShowAddFollowDialog(false) })
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(20.dp),
            onClick = { onCloseButtonClickListener() }
        ) {
            Icon(
                painter = painterResource(id = drawable.ic_close),
                contentDescription = stringResource(R.string.image_close_follow_dialog)
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.title_chat_partner),
            color = md_theme_light_onBackground,
            textAlign = TextAlign.Center,
            style = textStyleBold22
        )

        TextButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            shape = shapes.extraSmall,
            colors = ButtonDefaults.buttonColors(
                contentColor = md_theme_light_onPrimaryContainer,
                containerColor = seed
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            onClick = { setIsShowAddFollowDialog(true) }
        ) {
            Text(
                text = stringResource(R.string.content_add_follow),
                style = typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}