package com.ssafy.talkeasy.feature.auth.ui.tablet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.R.raw
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WelcomeFrame(memberName: String) {
    val isLoading = remember {
        mutableStateOf(true)
    }

    Box(modifier = Modifier.wrapContentSize(align = Alignment.Center)) {
        if (isLoading.value) {
            LoadingAnimation(isLoading)
        } else {
            WelcomeAnimation(memberName)
        }
    }
}

/**
 * 아름아, 이거 무한 루프 돌다가 아래 버튼 누르면 로딩 중단 되도록 해놨으니까 그거 이용해서
 * 회원 정보 가져오는거 완료 되면 로딩 중단하고 다음 animation으로 넘어가도록 하면 될 것 같아용!
 * */
@Composable
fun LoadingAnimation(isLoading: MutableState<Boolean>) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw.anim_loading))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f,
            iterations = LottieConstants.IterateForever
        )
    }

    Column {
        LottieAnimation(
            modifier = Modifier.size(120.dp),
            composition = composition,
            progress = { lottieAnimatable.progress }
        )

        TextButton(colors = ButtonDefaults.buttonColors(), onClick = {
            CoroutineScope(Dispatchers.Default).launch {
                // 이 함수 호출하면 바로 멈추니까 이거 써요~~
                lottieAnimatable.snapTo(composition = composition, iteration = 0)
                isLoading.value = false
            }
        }) {
            Text(text = "로딩 중단")
        }
    }
}

@Composable
fun WelcomeAnimation(memberName: String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw.anim_welcome))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(key1 = composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f
        )
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        LottieAnimation(
            modifier = Modifier
                .size(570.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp),
            composition = composition,
            progress = { lottieAnimatable.progress }
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 150.dp),
            textAlign = TextAlign.Center,
            text = String.format(stringResource(id = R.string.title_welcome), memberName),
            style = textStyleBold40
        )
    }
}