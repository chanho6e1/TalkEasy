package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.talkeasy.feature.common.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier, isLoading: MutableState<Boolean>, size: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading))
    val lottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(composition) {
        lottieAnimatable.animate(
            composition = composition,
            clipSpec = LottieClipSpec.Frame(0, 1200),
            initialProgress = 0f,
            iterations = LottieConstants.IterateForever
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LottieAnimation(
            modifier = modifier.size(size.dp),
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