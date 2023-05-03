package com.ssafy.talkeasy.feature.auth.ui.tablet

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.talkeasy.feature.common.R.raw

@Composable
fun WelcomeFrame(username: String) {
    val doHandler by lazy { Handler(Looper.getMainLooper()) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(raw.anim_loading))
    val progress by animateLottieCompositionAsState(composition)

    Box(modifier = Modifier.wrapContentSize(align = Alignment.Center)) {
        // doHandler.postDelayed(
        // LottieAnimation(
        //     composition = composition,
        //     progress = progress,
        // )
        // )

        // Text(
        //     text = String.format(stringResource(id = R.string.title_welcome), username),
        //     style = welcomeBold40,
        //     textAlign = TextAlign.Center
        // )
    }
}