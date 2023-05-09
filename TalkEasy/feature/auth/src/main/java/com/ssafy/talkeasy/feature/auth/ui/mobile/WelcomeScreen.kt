package com.ssafy.talkeasy.feature.auth.ui.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.R.drawable as Common
import com.ssafy.talkeasy.feature.common.component.LoadingAnimation
import com.ssafy.talkeasy.feature.common.component.WelcomeAnimation
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
internal fun WelcomeRouteProtector(
    modifier: Modifier = Modifier,
    onFinishedLoading: () -> Unit = {},
) {
    Box() {
        WelcomeScreen(
            modifier = Modifier,
            memberName = "일이삼사오육칠팔",
            onFinishedLoading = onFinishedLoading
        )
    }
}

@Composable
internal fun WelcomeScreen(
    modifier: Modifier = Modifier,
    memberName: String,
    onFinishedLoading: () -> Unit = {},
) {
    val isLoading = remember {
        mutableStateOf(false)
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isLoading.value) {
                LoadingAnimation(isLoading = isLoading, size = 320)
            } else {
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
}