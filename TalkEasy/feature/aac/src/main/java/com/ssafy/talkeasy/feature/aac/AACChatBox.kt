package com.ssafy.talkeasy.feature.aac

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.talkeasy.feature.aac.R.string
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.harp
import com.ssafy.talkeasy.feature.common.ui.theme.shapes

@Composable
fun AACChatBox(words: List<String>) {
    val (sendEnable, setSendEnable) = remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 26.dp, start = 36.dp, end = 36.dp),
        shape = shapes.extraSmall,
        color = black_squeeze
    ) {
        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AACChatCards(words = words)

            Row(modifier = Modifier.padding(start = 16.dp, end = 14.dp)) {
                IconButton(modifier = Modifier.size(62.dp), onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_preview),
                        contentDescription = stringResource(string.image_preview)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    modifier = Modifier.size(62.dp),
                    onClick = { setSendEnable(!sendEnable) }
                ) {
                    Image(
                        painter = if (sendEnable) {
                            painterResource(id = R.drawable.ic_send_enable)
                        } else {
                            painterResource(id = R.drawable.ic_send_disable)
                        },
                        contentDescription = stringResource(string.image_send_chat)
                    )
                }
            }
        }
    }
}

@Composable
fun AACChatCards(words: List<String>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(words) { word ->
            AACCardWrap(word = word, color = harp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAACChatBox() {
    AACChatBox(words = SampleData.string7)
}

@Composable
@Preview(showBackground = true)
fun PreviewAACChatCards() {
    AACChatCards(words = SampleData.string7)
}

@Composable
@Preview
fun PreviewAACChatCard() {
    AACCardWrap(word = "감사합니다", color = harp)
}