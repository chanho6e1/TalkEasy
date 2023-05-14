package com.ssafy.talkeasy.feature.auth.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ssafy.talkeasy.feature.auth.AuthViewModel
import com.ssafy.talkeasy.feature.common.component.WelcomeAnimation
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold40
import com.ssafy.talkeasy.feature.follow.FollowViewModel

@Composable
fun WelcomeRouteWard(
    modifier: Modifier = Modifier,
    onFinishedLoading: () -> Unit = {},
    navBackStackEntry: NavBackStackEntry,
    authViewModel: AuthViewModel = hiltViewModel(navBackStackEntry),
    followViewModel: FollowViewModel = hiltViewModel(),
) {
    val memberInfo by followViewModel.memberInfo.collectAsState()
    val followList by followViewModel.followList.collectAsState()
    val memberName by authViewModel.name.collectAsState()
    var isInfoLoadingFinished by remember {
        mutableStateOf(false)
    }

    SideEffect {
        if (memberInfo == null) {
            followViewModel.requestMemberInfo()
        }
        if (followList == null) {
            followViewModel.requestFollowList()
        }
    }

    LaunchedEffect(memberInfo) {
        if (memberInfo != null) {
            isInfoLoadingFinished = true
        }
    }

    WelcomeFrame(
        modifier = modifier,
        memberName = memberName,
        onFinishedLoading = onFinishedLoading,
        isInfoLoadingFinished = isInfoLoadingFinished
    )
}

@Composable
internal fun WelcomeFrame(
    modifier: Modifier = Modifier,
    memberName: String,
    isInfoLoadingFinished: Boolean,
    onFinishedLoading: () -> Unit = {},
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.wrapContentSize(align = Alignment.Center)) {
            WelcomeAnimation(
                memberName = memberName,
                size = 570,
                textStyle = textStyleBold40,
                bottomPadding = 80,
                isInfoLoadingFinished = isInfoLoadingFinished,
                onFinishedLoading = onFinishedLoading
            )
        }
    }
}