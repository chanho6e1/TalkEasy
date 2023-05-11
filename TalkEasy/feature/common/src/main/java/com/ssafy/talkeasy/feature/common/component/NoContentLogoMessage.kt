package com.ssafy.talkeasy.feature.common.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.harp

@Composable
fun NoContentLogoMessage(
    modifier: Modifier = Modifier,
    message: String,
    textStyle: TextStyle,
    color: Color = harp,
    width: Int,
    height: Int,
    betweenValue: Int,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(betweenValue.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.size(width = width.dp, height = height.dp),
                painter = painterResource(id = R.drawable.bg_talkeasy_logo_verticcal_trans),
                contentDescription = stringResource(
                    id = R.string.image_logo
                ),
                colorFilter = ColorFilter.tint(color)
            )
            Text(
                text = message,
                style = textStyle,
                color = color,
                textAlign = TextAlign.Center
            )
        }
    }
}