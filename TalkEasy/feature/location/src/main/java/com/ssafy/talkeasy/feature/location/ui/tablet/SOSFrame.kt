package com.ssafy.talkeasy.feature.location.ui.tablet

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_errorContainer
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold36
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold90
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal28
import com.ssafy.talkeasy.feature.common.ui.theme.typography
import com.ssafy.talkeasy.feature.location.R

@Composable
fun SOSRequestFrame(closeSOSRequestDialog: () -> Unit, showSOSDialog: () -> Unit) {
    var count by remember { mutableStateOf(10) }

    LaunchedEffect(true) {
        val animatable = Animatable(10f)
        animatable.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 10000, easing = LinearEasing)
        ) {
            count = value.toInt()
        }

        if (count == 0) {
            closeSOSRequestDialog()
            showSOSDialog()
        }
    }

    Dialog(onDismissRequest = closeSOSRequestDialog) {
        Box(
            modifier = Modifier
                .size(width = 685.dp, height = 660.dp)
                .background(shape = shapes.medium, color = md_theme_light_background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar()

                Box(
                    modifier = Modifier
                        .size(187.dp)
                        .clip(CircleShape)
                        .background(color = md_theme_light_error)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = count.toString(),
                        style = textStyleBold90,
                        color = md_theme_light_background
                    )
                }

                Text(
                    text = stringResource(R.string.content_request_sos),
                    style = typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(40.dp))

                Column(
                    modifier = Modifier.clickable { closeSOSRequestDialog() },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(color = delta)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp)
                                .align(Alignment.Center),
                            painter = painterResource(id = drawable.ic_close),
                            contentDescription = stringResource(R.string.image_cancel_sos_request),
                            tint = md_theme_light_background
                        )
                    }

                    Text(text = "종료", style = typography.titleSmall, color = delta)
                }
            }
        }
    }
}

@Composable
fun SOSFrame(quitSOSListener: () -> Unit) {
    Dialog(onDismissRequest = quitSOSListener) {
        Box(
            modifier = Modifier
                .size(width = 780.dp, height = 600.dp)
                .background(color = md_theme_light_background, shape = shapes.medium)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBar()

                Box(
                    modifier = Modifier
                        .size(187.dp)
                        .clip(CircleShape)
                        .background(color = md_theme_light_errorContainer)
                ) {
                    Image(
                        modifier = Modifier
                            .size(124.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = drawable.ic_sos),
                        contentDescription = stringResource(R.string.image_sos)
                    )
                }

                Text(
                    text = stringResource(R.string.content_sos),
                    style = typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.width(1.dp))

                TextButton(
                    shape = RoundedCornerShape(150.dp),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = md_theme_light_background,
                        containerColor = md_theme_light_error
                    ),
                    contentPadding = PaddingValues(horizontal = 50.dp, vertical = 14.dp),
                    onClick = quitSOSListener
                ) {
                    Text(
                        text = stringResource(R.string.content_quit_sos),
                        style = textStyleNormal28,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar() {
    Text(text = stringResource(R.string.title_request_sos), style = textStyleBold36)
}