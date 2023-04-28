package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.aac.R.string
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal16
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22

@Composable
fun AACFrame() {
    Column {
        Row { }
    }
}

@Composable
fun ChatPartner(profileImageId: Int, name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = profileImageId),
            contentDescription = stringResource(string.image_profile_default),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = name,
            color = md_theme_light_onBackground,
            style = textStyleNormal22,
            modifier = Modifier.width(203.dp)
        )

        Spacer(modifier = Modifier.width(28.dp))

        Surface(
            shape = MaterialTheme.shapes.extraSmall,
            color = black_squeeze
        ) {
            Text(
                text = "변경",
                color = delta,
                style = textStyleNormal16,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
    }
}

@Composable
@Preview
fun DefaultProfile() {
    ChatPartner(R.drawable.ic_chat_tts, "TTS(음성 출력) 모드")
}

@Composable
@Preview
fun PreviewProfile() {
    ChatPartner(profileImageId = R.drawable.ic_profile_default, name = "일이삼사오육칠팔구십일이삼사오육칠")
}