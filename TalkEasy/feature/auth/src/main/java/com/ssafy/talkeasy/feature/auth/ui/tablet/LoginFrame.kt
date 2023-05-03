package com.ssafy.talkeasy.feature.auth.ui.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.auth.R
import com.ssafy.talkeasy.feature.common.R.drawable
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_background

@Composable
@Preview(widthDp = 1429, heightDp = 857)
fun LoginFrame() {
    Surface(color = md_theme_light_background) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.BottomCenter,
            imageVector = ImageVector.vectorResource(id = drawable.bg_log_in_wave_for_tablet),
            contentDescription = stringResource(R.string.image_bg_log_in_wave),
            contentScale = ContentScale.FillWidth
        )

        Surface(
            modifier = Modifier
                .height(500.dp)
                .padding(top = 9.dp, start = 836.dp),
            color = Color.Transparent
        ) {
            Image(
                alignment = Alignment.TopStart,
                imageVector = ImageVector.vectorResource(
                    id = drawable.bg_log_in_right_leaf_for_tablet
                ),
                contentDescription = stringResource(R.string.image_bg_right_leaf),
                contentScale = ContentScale.FillHeight
            )
        }

        Image(
            modifier = Modifier.padding(top = 417.dp, end = 810.dp),
            alignment = Alignment.TopStart,
            imageVector = ImageVector.vectorResource(id = drawable.bg_log_in_left_leaf_for_tablet),
            contentDescription = stringResource(R.string.image_bg_right_leaf),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier.wrapContentSize(align = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(width = 478.dp, height = 218.82.dp),
                color = Color.Transparent
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        id = drawable.bg_talkeasy_logo_verticcal_trans
                    ),
                    contentDescription = stringResource(R.string.image_logo),
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(modifier = Modifier.height(100.dp))

            IconButton(
                modifier = Modifier.size(width = 600.dp, height = 90.dp),
                onClick = { }
            ) {
                Image(
                    alignment = Alignment.TopCenter,
                    painter = painterResource(drawable.ic_kakao_login),
                    contentDescription = stringResource(R.string.image_log_in),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}