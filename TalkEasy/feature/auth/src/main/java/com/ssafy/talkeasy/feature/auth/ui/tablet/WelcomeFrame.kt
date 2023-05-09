package com.ssafy.talkeasy.feature.auth.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.talkeasy.feature.common.component.LoadingAnimation
import com.ssafy.talkeasy.feature.common.component.WelcomeAnimation
import com.ssafy.talkeasy.feature.common.ui.theme.welcomeBold40

@Preview(showBackground = true, widthDp = 1429, heightDp = 857)
@Composable
fun WelcomeRouteWard(modifier: Modifier = Modifier) {
    WelcomeFrame(modifier = modifier, memberName = "일이삼사오육칠팔구")
}

@Composable
internal fun WelcomeFrame(modifier: Modifier = Modifier, memberName: String) {
    val isLoading = remember {
        mutableStateOf(false)
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.wrapContentSize(align = Alignment.Center)) {
            if (isLoading.value) {
                LoadingAnimation(isLoading = isLoading, size = 120)
            } else {
                WelcomeAnimation(
                    memberName = memberName,
                    size = 570,
                    textStyle = welcomeBold40,
                    bottomPadding = 80
                )
            }
        }
    }
}