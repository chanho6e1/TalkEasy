package com.ssafy.talkeasy.feature.auth.ui.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(color = md_theme_light_background) {
            Image(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter,
                imageVector = ImageVector.vectorResource(id = drawable.bg_log_in_wave_for_tablet),
                contentDescription = stringResource(R.string.bg_main_wave_text),
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = drawable.bg_log_in_right_leaf_for_tablet),
                contentDescription = stringResource(id = R.string.bg_main_leaf_big_text),
                modifier = Modifier
                    .size(450.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 9.dp, start = 155.dp),
            )

            Image(
                painter = painterResource(id = drawable.bg_log_in_left_leaf_for_tablet),
                contentDescription = stringResource(id = R.string.bg_main_leaf_big_text),
                modifier = Modifier
                    .size(450.dp)
                    .align(Alignment.BottomStart)
                    .padding(end = 128.dp, top = 95.dp),
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        painter = painterResource(id = drawable.bg_talkeasy_logo_verticcal_trans),
                        contentDescription = stringResource(id = R.string.image_logo),
                        modifier = Modifier
                            .padding(bottom = 101.dp)
                            .fillMaxSize()
                            .size(200.dp)
                    )
                }
                item {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxSize()
                            .size(60.dp)
                    ) {
                        Image(
                            painter = painterResource(id = drawable.ic_kakao_login),
                            contentDescription = stringResource(id = R.string.kakao_login_text),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}