package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.aac.R.string
import com.ssafy.talkeasy.feature.common.R.drawable

@Composable
fun AACFrame() {
    Column() {
        Row() { }
    }
}

@Composable
fun ChatPartner() {
    Row() {
        Image(
            painter = painterResource(id = drawable.ic_profile_default),
            contentDescription = stringResource(string.image_profile_default),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
@Preview
fun DefaultProfile() {
    ChatPartner()
}