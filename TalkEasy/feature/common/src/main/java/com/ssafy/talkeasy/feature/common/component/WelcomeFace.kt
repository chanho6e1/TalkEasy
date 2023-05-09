package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.R.raw

/**
 * 아름아, 이거 무한 루프 돌다가 아래 버튼 누르면 로딩 중단 되도록 해놨으니까 그거 이용해서
 * 회원 정보 가져오는거 완료 되면 로딩 중단하고 다음 animation으로 넘어가도록 하면 될 것 같아용!
 * */

@Composable
fun WelcomeAnimation(
    modifier: Modifier = Modifier,
    memberName: String,
    size: Int,
    textStyle: TextStyle,
    bottomPadding: Int,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw.anim_welcome))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(key1 = composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f
        )
    }

    Box(modifier = modifier.fillMaxWidth()) {
        LottieAnimation(
            modifier = modifier
                .size(size.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding.dp),
            composition = composition,
            progress = { lottieAnimatable.progress }
        )

        Text(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPadding.dp),
            textAlign = TextAlign.Center,
            text = String.format(stringResource(id = R.string.title_welcome), memberName),
            style = textStyle,
            fontWeight = FontWeight.Bold
        )
    }
}