package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ssafy.talkeasy.feature.aac.R.string
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.component.Profile
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.delta
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_error
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_errorContainer
import com.ssafy.talkeasy.feature.common.ui.theme.md_theme_light_onBackground
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleBold22
import com.ssafy.talkeasy.feature.common.ui.theme.textStyleNormal22
import com.ssafy.talkeasy.feature.common.ui.theme.typography

@Composable
fun AACTopBar(onRight: Boolean) {
    val chatMode by remember {
        mutableStateOf(false)
    }

    if (onRight) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 36.dp)
        ) {
            val (profile, snackBar, buttons) = createRefs()

            Box(
                modifier = Modifier.constrainAs(profile) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 10.dp)
                }
            ) {
                if (chatMode) {
                    ChatPartner(name = "강은선인데이름이왕왕길어욥")
                } else {
                    DefaultProfile()
                }
            }

            Box(
                modifier = Modifier.constrainAs(snackBar) {
                    start.linkTo(profile.end)
                    end.linkTo(buttons.start)
                    width = Dimension.fillToConstraints
                }
            ) {
                BrowseLocation("이름이왕왕긴강은선")
            }

            Row(
                modifier = Modifier.constrainAs(buttons) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = 8.dp)
                }
            ) {
                ButtonSOS()

                Spacer(modifier = Modifier.width(20.dp))

                ButtonAlarmAndSetting()
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 21.dp, start = 36.dp, end = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonSOS()

            Row(horizontalArrangement = Arrangement.End) {
                ButtonAlarmAndSetting()

                Spacer(modifier = Modifier.width(20.dp))

                if (chatMode) {
                    ChatPartner(name = "강은선인데이름이왕왕길어욥")
                } else {
                    DefaultProfile()
                }
            }
        }
    }
}

@Composable
fun ChatPartner(profileUrl: String = "", name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Profile(profileUrl, 48)

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            modifier = Modifier.width(203.dp),
            text = name,
            color = md_theme_light_onBackground,
            style = textStyleNormal22
        )

        Spacer(modifier = Modifier.width(28.dp))

        Surface(
            shape = shapes.extraSmall,
            color = black_squeeze
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                text = "변경",
                color = delta,
                style = typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview
fun ButtonSOS() {
    Surface(shape = shapes.extraSmall, color = md_theme_light_errorContainer) {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
            text = "SOS",
            color = md_theme_light_error,
            style = textStyleBold22
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
            Image(
                modifier = Modifier.size(40.dp),
                painter = if (newAlarm) {
                    painterResource(id = R.drawable.ic_alarm_new)
                } else {
                    painterResource(id = R.drawable.ic_alarm_default)
                },
                contentDescription = stringResource(string.image_alarm_default)
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        IconButton(onClick = {
            // 세팅 화면으로 전환
            setNewAlarm(!newAlarm)
        }) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = stringResource(string.image_setting)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DefaultProfile() {
    ChatPartner(name = stringResource(string.content_chat_mode_tts))
}