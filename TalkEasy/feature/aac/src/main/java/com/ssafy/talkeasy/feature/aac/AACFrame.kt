package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_errorContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal16
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22

@Composable
fun AACFrame() {
    val onRight = remember {
        mutableStateOf(true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 21.dp, start = 24.dp, end = 36.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onRight.value) {
                // ChatPartner(profileImageId = profileImageId, name = name)
                DefaultProfile()

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    ButtonSOS()

                    Spacer(modifier = Modifier.width(20.dp))

                    ButtonAlarmAndSetting()
                }
            }
        }
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
fun ButtonSOS() {
    Surface(shape = MaterialTheme.shapes.extraSmall, color = md_theme_light_errorContainer) {
        Text(
            text = "SOS",
            color = md_theme_light_error,
            style = textStyleBold22,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ButtonAlarmAndSetting() {
    val (newAlarm, setNewAlarm) = remember {
        mutableStateOf(false)
    }

    Row {
        IconButton(onClick = { setNewAlarm(!newAlarm) }) {
            Icon(
                painter = if (newAlarm) {
                    painterResource(id = R.drawable.ic_alarm_new)
                } else {
                    painterResource(id = R.drawable.ic_alarm_default)
                },
                contentDescription = stringResource(string.image_alarm_default),
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        IconButton(onClick = {
            // 세팅 화면으로 전환
            setNewAlarm(!newAlarm)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = stringResource(string.image_setting),
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultProfile() {
    ChatPartner(R.drawable.ic_chat_tts, stringResource(string.content_chat_mode_tts))
}

@Composable
@Preview(showBackground = true)
fun PreviewProfile() {
    ChatPartner(profileImageId = R.drawable.ic_profile_default, name = "일이삼사오육칠팔구십일이삼사오육칠")
}