package com.ssafy.talkeasy.feature.auth.ui.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.feature.auth.AuthViewModel
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.R.drawable as Common
import com.ssafy.talkeasy.feature.common.component.WelcomeAnimation
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.follow.FollowViewModel

@Composable
internal fun WelcomeRouteProtector(
    modifier: Modifier = Modifier,
    onFinishedLoading: () -> Unit = {},
    navBackStackEntry: NavBackStackEntry,
    authViewModel: AuthViewModel = hiltViewModel(navBackStackEntry),
    followViewModel: FollowViewModel = hiltViewModel(),
) {
    val memberInfo by followViewModel.memberInfo.collectAsState()
    val memberName by authViewModel.name.collectAsState()

    SideEffect {
        if (memberInfo == null) {
            followViewModel.requestMemberInfo()
        }
    }

    LaunchedEffect(memberInfo) {
        if (memberInfo != null) {
            onFinishedLoading()
        }
    }

    Box() {
        WelcomeScreen(
            modifier = modifier,
            memberName = memberName
        )
    }
}

@Composable
internal fun WelcomeScreen(
    modifier: Modifier = Modifier,
    memberName: String,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            WelcomeAnimation(
                memberName = memberName,
                size = 320,
                textStyle = typography.titleMedium,
                bottomPadding = 50
            )

            Image(
                painter = painterResource(id = Common.bg_log_in_wave_for_mobile),
                contentDescription = stringResource(id = R.string.bg_main_wave_text),
                modifier = modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter
            )
        }
    }
}