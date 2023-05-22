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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.talkeasy.feature.aac.AACViewModel
import com.ssafy.talkeasy.feature.aac.R.string
import com.ssafy.talkeasy.feature.common.R
import com.ssafy.talkeasy.feature.common.component.LoadingAnimationIterate
import com.ssafy.talkeasy.feature.common.ui.theme.black_squeeze
import com.ssafy.talkeasy.feature.common.ui.theme.harp
import com.ssafy.talkeasy.feature.common.ui.theme.shapes
import com.ssafy.talkeasy.feature.common.util.SendMode

@Composable
fun AACChatBox(
    words: List<String> = listOf(),
    cardClickEnable: Boolean,
    sendMode: MutableState<SendMode>,
    setCardClickEnable: (Boolean) -> Unit,
    aacViewModel: AACViewModel = viewModel(),
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
                modifier = Modifier.constrainAs(chatCards) {
                    start.linkTo(parent.start)
                    end.linkTo(buttons.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
                cardClickEnable = cardClickEnable
            )

            SendButtonRow(
                modifier = Modifier.constrainAs(buttons) { end.linkTo(parent.end) },
                sendEnable = words.isNotEmpty(),
                sendMode = sendMode,
                setCardClickEnable = { flag: Boolean -> setCardClickEnable(flag) },
                generateSentence = { aacViewModel.generateSentence(words = words) }
            )
        }
    }
}

@Composable
fun AACChatCards(
    words: List<String>,
    modifier: Modifier,
    cardClickEnable: Boolean,
    aacViewModel: AACViewModel = viewModel(),
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = modifier) {
        itemsIndexed(words) { index, word ->
            AACCardWrap(
                word = word,
                color = harp,
                cardClickEnable = cardClickEnable,
                onCardSelectedListener = { aacViewModel.deleteCard(index) }
            )
        }
    }
}

@Composable
fun SendButtonRow(
    modifier: Modifier,
    sendEnable: Boolean,
    sendMode: MutableState<SendMode>,
    setCardClickEnable: (Boolean) -> Unit,
    generateSentence: () -> Unit,
    aacViewModel: AACViewModel = viewModel(),
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val previewCardList: MutableState<List<String>> = remember {
            mutableStateOf(listOf())
        }
        val selectedCardList by aacViewModel.selectedCard.collectAsState()

        LaunchedEffect(key1 = sendMode.value) {
            if (sendMode.value == SendMode.NONE) {
                setCardClickEnable(true)
            }
        }

        if (sendMode.value == SendMode.PREVIEW) {
            LoadingAnimationIterate(loadingAnimationId = R.raw.anim_loading_grey, size = 62.dp)
        } else {
            IconButton(
                modifier = Modifier.size(62.dp),
                onClick = {
                    previewCardList.value = selectedCardList
                    setCardClickEnable(false)
                    generateSentence()
                    sendMode.value = SendMode.PREVIEW
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_preview),
                    contentDescription = stringResource(string.image_preview)
                )
            }
        }

        if (sendMode.value == SendMode.SEND) {
            LoadingAnimationIterate(loadingAnimationId = R.raw.anim_loading_green, size = 62.dp)
        } else {
            IconButton(
                modifier = Modifier.size(62.dp),
                enabled = sendEnable,
                onClick = {
                    setCardClickEnable(false)
                    if (selectedCardList != previewCardList.value) {
                        generateSentence()
                    }
                    previewCardList.value = listOf()
                    sendMode.value = SendMode.SEND
                }
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