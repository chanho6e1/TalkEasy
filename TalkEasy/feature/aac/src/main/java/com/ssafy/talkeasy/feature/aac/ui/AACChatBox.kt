package com.ssafy.talkeasy.feature.aac.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.aac.R.string
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.harp
import com.ssafy.talkeasy.feature.common.ui.theme.shapes

@Composable
@Preview(showBackground = true)
fun AACChatBox(
    words: List<String> = listOf(),
    aacViewModel: AACViewModel = viewModel(),
    setIsClickedSendButton: (Boolean) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        shape = shapes.extraSmall,
        color = black_squeeze
    ) {
        ConstraintLayout(
            Modifier.padding(
                top = 11.dp,
                bottom = 11.dp,
                start = 20.dp,
                end = 14.dp
            )
        ) {
            val (chatCards, buttons) = createRefs()

            AACChatCards(
                words = words,
                modifier = Modifier
                    .constrainAs(chatCards) {
                        start.linkTo(parent.start)
                        end.linkTo(buttons.start, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            SendButtonRow(
                modifier = Modifier.constrainAs(buttons) {
                    end.linkTo(parent.end)
                },
                sendEnable = words.isNotEmpty(),
                onSendButtonClick = {
                    aacViewModel.generateSentence(words = words)
                    setIsClickedSendButton(true)
                }
            )
        }
    }
}

@Composable
fun AACChatCards(
    words: List<String>,
    modifier: Modifier,
    aacViewModel: AACViewModel = viewModel(),
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier) {
        itemsIndexed(words) { index, word ->
            AACCardWrap(
                word = word,
                color = harp,
                onCardSelectedListener = { aacViewModel.deleteCard(index) }
            )
        }
    }
}

@Composable
fun SendButtonRow(modifier: Modifier, sendEnable: Boolean, onSendButtonClick: () -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(modifier = Modifier.size(62.dp), onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_preview),
                contentDescription = stringResource(string.image_preview)
            )
        }

        IconButton(
            modifier = Modifier.size(62.dp),
            enabled = sendEnable,
            onClick = onSendButtonClick
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