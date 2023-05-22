package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Preview(showBackground = true)
@Composable
fun LocationOpenAnimation(
    modifier: Modifier = Modifier,
    isInfoLoadingFinished: Boolean = false,
    isLocationOpenAccepted: Boolean = false,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.anim_location_loading)
    )
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(key1 = composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f,
            iterations = LottieConstants.IterateForever
        )
    }

    LaunchedEffect(key1 = isLocationOpenAccepted, key2 = isInfoLoadingFinished) {
        if (isInfoLoadingFinished && isLocationOpenAccepted) {
            lottieAnimatable.snapTo(composition = composition, iteration = 0)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = md_theme_light_background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .wrapContentHeight()
                        .size(width = 400.dp, height = 199.dp),
                    composition = composition,
                    progress = { lottieAnimatable.progress }
                )

                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.content_location_open_animation),
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}