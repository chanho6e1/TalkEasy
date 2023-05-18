package com.ssafy.talkeasy.feature.chat.ui.mobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.feature.chat.R
import com.ssafy.talkeasy.feature.common.component.FollowDetailScreen
import com.ssafy.talkeasy.feature.follow.FollowViewModel

@Composable
internal fun ChatPartnerInfoScreen(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    navBackStackEntry: NavBackStackEntry,
    followViewModel: FollowViewModel = hiltViewModel(navBackStackEntry),
) {
    val selectFollow by followViewModel.selectFollow.collectAsState()

    FollowDetailScreen(
        modifier = modifier,
        title = stringResource(R.string.title_show_chat_partner_detail),
        buttonContent = stringResource(R.string.content_save),
        selectFollowInfo = selectFollow,
        buttonClickListener = {},
        popBackStack = popBackStack
    )
}